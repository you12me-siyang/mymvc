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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.entity.Handler;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.MyModelAndView;
import com.wbh.mymvc.util.MvcUtil;

/**
 * 请求调度核心类
 * @author wbh
 * @version 1.1
 */
public class MyDispatcherServlet extends MyBaseServlet {

	private static final long serialVersionUID = 5021042324168770425L;

	private Properties p;
	private List<Class<?>> ca = new ArrayList<Class<?>>();
	private String viewPath = "";
	private Map<String, Handler> hs = new HashMap<String, Handler>();
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

		logger.info("=========================预映射URL和requestMap=====================");
		mapMethod();
		
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
					ca.add(c);
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
		for (Class<?> c : ca) {
			ms = c.getMethods();
			String mappingUrl = "";
			for (Method m : ms) {
				if (m.isAnnotationPresent(MyRequestMapping.class)) {
					mappingUrl = appName+ m.getAnnotation(MyRequestMapping.class).value().trim();
					rm = m.getAnnotation(MyRequestMapping.class).method().trim().toUpperCase();
					logger.info("映射url:" + mappingUrl);
					hs.put(mappingUrl+rm, new Handler(c, m, rm));//直接拼接字符串当key，以后再优化
				}
			}
		}
	}

	/**
	 * 和springMVC一样使用反射来调用匹配的控制器方法
	 * 
	 * @param req
	 * @param resp
	 */
	private void doService(HttpServletRequest req, HttpServletResponse resp) {
	
		Handler h = getHandler(req, resp);
		MyModelAndView mv = null;
		
		//未匹配可以自定义异常，先放这里
		if(null != h){
			mv = invokeMappedMethod(h, req, resp);
			
		}
		
		if((null != mv)&&(!("").equals(mv.getView()))){
			loadView(mv, req, resp);
		}
	}
	
	/**
	 * 获得映射到的控制器
	 * @param req
	 * @param resp
	 * @return
	 */
	private Handler getHandler(HttpServletRequest req, HttpServletResponse resp){
		String url = req.getRequestURI();
		return hs.get(url+req.getMethod().trim().toUpperCase());
	}
	
	/**
	 * 执行映射方法返回MyModelAndView对象
	 * @param h
	 * @param req
	 * @param resp
	 * @return
	 */
	private MyModelAndView invokeMappedMethod(Handler h,HttpServletRequest req, HttpServletResponse resp){
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
	 * @param mv
	 * @param req
	 * @param resp
	 */
	private void loadView(MyModelAndView mv,HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("model", mv.getModel());
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
