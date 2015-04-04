package com.wbh.mymvc.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.bean.BeanBody;
import com.wbh.mymvc.context.ContextLoaderListener;
import com.wbh.mymvc.context.DefaultWebContext;
import com.wbh.mymvc.interceptor.BaseInterceptor;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.RequestResult;
import com.wbh.mymvc.util.Assert;
import com.wbh.mymvc.util.ComparatorObstructUtil;

/**
 * 请求调度核心类
 * 3.0后拦截器和控制器从IOC容器中取出
 * @author wbh
 * @version 1.1
 */
public class MyDispatcherServlet extends MyBaseServlet {

	private static final long serialVersionUID = 5021042324168770425L;

	public static final String PATH_MYVIEW = "path.myview";
	public static final String WEBCONTEXT = "webContext";

	private Properties p;
	private List<BeanBody> controllers = new ArrayList<BeanBody>();
	private String viewPath = "";
	private Map<String, Handler> hs = new HashMap<String, Handler>();
	private List<Obstruct> obstructs = new ArrayList<Obstruct>();
	private List<Obstruct> matchObstruct = new ArrayList<Obstruct>();
	private DefaultWebContext webContext = null;

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

	/*
	 * 初始化部分参数
	 */
	private void initParameter() {
		webContext = (DefaultWebContext) this.getServletContext().getAttribute(
				WEBCONTEXT);
		viewPath = p.getProperty(PATH_MYVIEW);
		logger.info("映射view目录:" + viewPath);
	}

	/*
	 * 加载配置文件
	 */
	private void loadConfigFile() {
		
		p = (Properties) this.getServletContext().getAttribute(ContextLoaderListener.BASE_CONFIG_PROPERTIES);
		
		Assert.isNull(p, "配置文件未配置！");
		
	}

	/*
	 * 加载控制器
	 */
	private void loadController() {
		controllers = webContext.getBeanBodysByAnnotation(MyController.class);

	}

	/*
	 * 映射控制器方法
	 */
	private void mapMethod() {

		Method[] ms = null;
		String rm = null;
		for (BeanBody bb : controllers) {
			ms = bb.getBeanClass().getMethods();

			String mappingUrl = "";
			for (Method m : ms) {
				if (m.isAnnotationPresent(MyRequestMapping.class)) {
					mappingUrl = this.getServletContext().getContextPath()
							+ m.getAnnotation(MyRequestMapping.class).value()
									.trim();
					rm = m.getAnnotation(MyRequestMapping.class).method()
							.trim().toUpperCase();
					logger.info("映射url:" + mappingUrl);

					hs.put(mappingUrl + rm, new Handler(bb.getBean(), m, rm));// 先直接拼接字符串当key，以后再优化
				}
			}
		}
	}

	/*
	 * 加载拦截器
	 */
	private void loadInterceptor() {

		List<Object> os = webContext.getBeansByAnnotation(MyInterceptor.class);
		String[] mappingPath = {};
		String interceptorMethod = "";
		int index = 0;
		for (Object o : os) {
			mappingPath = o.getClass().getAnnotation(MyInterceptor.class)
					.mappingPath();
			interceptorMethod = o.getClass().getAnnotation(MyInterceptor.class)
					.interceptionMethod();
			index = o.getClass().getAnnotation(MyInterceptor.class).index();

			obstructs.add(new Obstruct((BaseInterceptor) o, mappingPath,
					interceptorMethod, index));
			logger.info("加载interceptor:" + o.getClass().getName());

		}

		Collections.sort(obstructs, new ComparatorObstructUtil());
	}

	/**
	 * springMVC的拦截器栈虽然形式上是一个栈的类型，在底层实现却没有使用stack
	 * 使用单个stack在afterHandler和afterViewLoad同时需要逆向读出的要求下实
	 * 现起来繁琐，springMVC底层维护了一个HandlerExecutionChain存放handler和 interceptor
	 * 对象的集合，服务器在启动时所有配置在spring配置文件中的拦截器
	 * 便已经实例化（通过IOC），mymvc3.0后加入了IOC已经可以从webcontext中取出拦截器和控制器
	 * 
	 * 和springMVC一样使用反射来调用匹配的控制器方法
	 * 
	 * @param req
	 * @param resp
	 */
	private void doService(HttpServletRequest req, HttpServletResponse resp) {

		Handler h = getHandler(req, resp);
		RequestResult rr = new RequestResult();

		if (null == h) { // url已被mapping才进行拦截否则直接返回
			return;
		}

		initMatchObstructList(req);

		/*
		 * 这里的处理和springMVC有所不同，这里在beforHandler返回false时直接返回，而
		 * springMVC会调用afterCompletion方法，此时的afterCompletion和返回ture的
		 * afterCompletion方法不同，这里以后再做细致分析，先这样好了。
		 */
		if (!doBeforeHandler(req, resp, h)) {
			return;
		}

		rr = invokeMappedMethod(h, req, resp);

		doAfterHandler(req, resp, rr);

		if ((null == rr) || (("").equals(rr.getView()))) {
			return;
		}

		loadView(rr, req, resp);

		doAfterViewLoad(req, resp);

	}

	private void initMatchObstructList(HttpServletRequest req) {

		matchObstruct.clear();// 清空匹配的拦截器集合

		for (int i = 0; i < obstructs.size(); i++) {
			Obstruct o = obstructs.get(i);
			if (o.pathMatch(req)) {
				matchObstruct.add(o);// 可以认为matchObstruct依旧是按照index赠序排列
			}
		}

	}

	private void doAfterViewLoad(HttpServletRequest req,
			HttpServletResponse resp) {

		for (int i = matchObstruct.size() - 1; i >= 0; i--) {
			Obstruct o = matchObstruct.get(i);
			try {

				o.getInterceptor().afterViewLoad(req, resp);

			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}

	private void doAfterHandler(HttpServletRequest req,
			HttpServletResponse resp, RequestResult rr) {

		// 降序
		for (int i = matchObstruct.size() - 1; i >= 0; i--) {
			Obstruct o = matchObstruct.get(i);
			try {

				o.getInterceptor().afterHandler(req, resp, rr);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	private boolean doBeforeHandler(HttpServletRequest req,
			HttpServletResponse resp, Handler h) {

		for (Obstruct o : matchObstruct) {

			try {

				if (!o.getInterceptor().beforeHandler(req, resp, h)) {
					return false;
				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return true;

	}

	/**
	 * 获得映射到的控制器
	 * @param req
	 * @param resp
	 * @return
	 */
	private Handler getHandler(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURI();
		return hs.get(url + req.getMethod().trim().toUpperCase());
	}

	/**
	 * 执行映射方法返回RequestResult对象
	 * @param h
	 * @param req
	 * @param resp
	 * @return
	 */
	private RequestResult invokeMappedMethod(Handler h,
			HttpServletRequest req, HttpServletResponse resp) {
		Method m = h.getMappingMethod();
		Object[] os = h.getParameterValues();
		try {
			
			return (RequestResult) m.invoke(
					h.getController(), 
					os);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			return null;
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			return null;
		} catch (InvocationTargetException e1) {
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
	private void loadView(RequestResult rr, HttpServletRequest req,
			HttpServletResponse resp) {
		Model m = rr.getModel();
		for (String s : rr.getModel().keySet()) {
			req.setAttribute(s, m.get(s));
		}
		String viewName = rr.getView().trim();
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
