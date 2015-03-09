package com.wbh.mymvc.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.MyModelAndView;
import com.wbh.mymvc.util.ComparatorObstructUtil;
import com.wbh.mymvc.util.MvcUtil;

/**
 * 请求调度核心类
 * 
 * @author wbh
 * @version 1.1
 */
public class MyDispatcherServlet extends MyBaseServlet {

	private static final long serialVersionUID = 5021042324168770425L;

	private Properties p;
	private List<Class<?>> cs = new ArrayList<Class<?>>();
	private String viewPath = "";
	private Map<String, Handler> hs = new HashMap<String, Handler>();
	private List<Obstruct> os = new ArrayList<Obstruct>();
	private Stack<Obstruct> obstructStack = new Stack<Obstruct>();
	private String appName = "";

	/**
	 * 该servlet在服务器启动时调用init()初始化，这里主要加载mvc配置文件，根据用户配置的控制器路径将被
	 * 
	 * @MyController 注解的类加载，存放在ca中，在之后的版本中这个初始化方法还将加载用户的拦截器列表
	 */
	@Override
	public void init() throws ServletException {

		logger.info("=====================MyDispatcherServlet init=====================");

		logger.info("============================加载配置文件:===========================");
		loadConfigFile();

		logger.info("=============================初始化参数============================");
		initParameter();

		logger.info("=============================加载控制器============================");
		loadController();

		logger.info("========================预映射URL和requestMap======================");
		mapMethod();

		logger.info("=============================加载拦截器============================");
		loadInterceptor();

		logger.info("=============================初始化完毕============================");

	}

	private void initParameter() {
		appName = this.getServletContext().getContextPath();
		logger.info("上下文目录:" + appName);
		viewPath = p.getProperty("myview.path");
		logger.info("映射view目录:" + viewPath);
	}

	private void loadConfigFile() {
		String mvcConfigLocation = getInitParameter("mvcConfigLocation");
		logger.info(mvcConfigLocation);
		InputStream inputStream = this.getServletContext().getResourceAsStream(
				mvcConfigLocation);

		p = new Properties();

		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void loadController() {

		String controllerPath = p.getProperty("controller.annotated.path");
		String filePath = "";
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();

		filePath = classPath + controllerPath;
		List<String> allClassName = new ArrayList<String>();

		MvcUtil.getAllClassName(classPath, filePath, allClassName);

		for (String s : allClassName) {
			try {

				Class<?> c = Class.forName(s);
				if (c.isAnnotationPresent(MyController.class)) {
					cs.add(c);
					logger.info("加载controller:" + c.getName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void mapMethod() {

		Method[] ms = null;
		String rm = null;
		for (Class<?> c : cs) {
			ms = c.getMethods();
			String mappingUrl = "";
			for (Method m : ms) {
				if (m.isAnnotationPresent(MyRequestMapping.class)) {
					mappingUrl = appName
							+ m.getAnnotation(MyRequestMapping.class).value()
									.trim();
					rm = m.getAnnotation(MyRequestMapping.class).method()
							.trim().toUpperCase();
					logger.info("映射url:" + mappingUrl);
					hs.put(mappingUrl + rm, new Handler(c, m, rm));// 先直接拼接字符串当key，以后再优化
				}
			}
		}
	}

	private void loadInterceptor() {
		String controllerPath = p.getProperty("interception.path").trim();
		String filePath = "";
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();

		filePath = classPath + controllerPath;
		List<String> allClassName = new ArrayList<String>();

		MvcUtil.getAllClassName(classPath, filePath, allClassName);

		String[] mappingPath = {};
		String interceptorMethod = "";
		int index = 0;
		for (String s : allClassName) {
			try {
				Class<?> c = Class.forName(s);
				if (c.isAnnotationPresent(MyInterceptor.class)) {
					mappingPath = c.getAnnotation(MyInterceptor.class)
							.mappingPath();
					interceptorMethod = c.getAnnotation(MyInterceptor.class)
							.interceptionMethod();
					index = c.getAnnotation(MyInterceptor.class).index();

					os.add(new Obstruct(c, mappingPath, interceptorMethod,
							index));
					logger.info("加载interceptor:" + c.getName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(os, new ComparatorObstructUtil());
	}

	/**
	 * springMVC的拦截器栈虽然形式上是一个栈的类型，在底层实现却没有使用stack
	 * 使用单个stack在afterHandler和afterViewLoad同时需要逆向读出的要求下实
	 * 现起来繁琐，springMVC底层维护了一个HandlerExecutionChain存放handler和
	 * interceptor 对象的集合，服务器在启动时所有配置在spring配置文件中的拦截器
	 * 便已经实例化，springMVC在匹配到拦截器后直接调用对象的方法，而我的做法是，
	 * 匹配到后再实例化，这必将引起一些性能上的损失，这将在后续版本中有所改进，现在
	 * 以完成功能为主。
	 */
	/**
	 * 和springMVC一样使用反射来调用匹配的控制器方法
	 * 
	 * @param req
	 * @param resp
	 */
	private void doService(HttpServletRequest req, HttpServletResponse resp) {

		obstructStack.clear();

		Handler h = getHandler(req, resp);
		MyModelAndView mv = new MyModelAndView();

		// 未匹配可以自定义异常，先放这里
		if (null == h) { // url已被mapping才进行拦截否则直接返回
			return;
		}
		/*
		 * 这里的处理和springMVC有所不同，这里在beforHandler返回false时直接返回，而
		 * springMVC会调用afterCompletion方法，此时的afterCompletion和返回ture的
		 * afterCompletion方法不同，这里以后再做细致分析，先这样好了。
		 */
		if (!doBeforeHandler(req, resp)) {
			return;
		}

		mv = invokeMappedMethod(h, req, resp);

		
		doAfterHandler(req, resp, mv);

		if ((null == mv) || (("").equals(mv.getView()))) {
			return;
		}

		loadView(mv, req, resp);

		doAfterViewLoad(req, resp, h);

	}

	private void doAfterViewLoad(HttpServletRequest req,
			HttpServletResponse resp, Handler h) {

	}

	private void doAfterHandler(HttpServletRequest req,
			HttpServletResponse resp, MyModelAndView mv) {
		
		Class<?> c = null;
		
		for (int i=os.size()-1;i>=0;i--) {
			Obstruct o =os.get(i);
			if (o.pathMatch(req)) {
				try {
					c = o.getInterceptorClass();
					Method m = c.getMethod("afterHandler", new Class<?>[] {
							javax.servlet.http.HttpServletRequest.class,
							javax.servlet.http.HttpServletResponse.class,
							MyModelAndView.class });
					 m.invoke(c.newInstance(), new Object[] { req,resp,mv });
					obstructStack.push(o);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private boolean doBeforeHandler(HttpServletRequest req,
			HttpServletResponse resp) {

		Class<?> c = null;
		boolean b = true;
		for (Obstruct o : os) {
			if (o.pathMatch(req)) {
				try {
					c = o.getInterceptorClass();
					Method m = c.getMethod("beforeHandler", new Class<?>[] {
							javax.servlet.http.HttpServletRequest.class,
							javax.servlet.http.HttpServletResponse.class});
					b = (boolean) m.invoke(c.newInstance(), new Object[] { req,
							resp });
					obstructStack.push(o);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}

	/**
	 * 获得映射到的控制器
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	private Handler getHandler(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURI();
		return hs.get(url + req.getMethod().trim().toUpperCase());
	}

	/**
	 * 执行映射方法返回MyModelAndView对象
	 * 
	 * @param h
	 * @param req
	 * @param resp
	 * @return
	 */
	private MyModelAndView invokeMappedMethod(Handler h,
			HttpServletRequest req, HttpServletResponse resp) {
		Model model = new Model();
		Method m = h.getMappingMethod();
		Class<?> c = h.getControllerClass();
		try {
			return (MyModelAndView) m.invoke(c.newInstance(), new Object[] {
					model, req, resp });
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			return null;
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			return null;
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
			return null;
		} catch (InstantiationException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	/**
	 * 加载页面
	 * 
	 * @param mv
	 * @param req
	 * @param resp
	 */
	private void loadView(MyModelAndView mv, HttpServletRequest req,
			HttpServletResponse resp) {
		Model m = mv.getModel();
		for(String s:mv.getModel().keySet()){
			req.setAttribute(s, m.get(s));
		}
		String viewName = mv.getView().trim();
		String viewFileName = viewPath + viewName + ".jsp";
		try {
			this.getServletContext().getRequestDispatcher(viewFileName)
					.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doService(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doService(req, resp);
	}

}
