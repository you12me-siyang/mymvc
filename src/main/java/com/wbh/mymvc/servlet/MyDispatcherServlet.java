package com.wbh.mymvc.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.MyModelAndView;
import com.wbh.mymvc.util.MvcUtil;

public class MyDispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 5021042324168770425L;

	private String mvcConfigLocation = "";
	private Properties p;
	private List<Class<?>> ca = new ArrayList<Class<?>>();
	private String viewPath = "";

	/**
	 * 该servlet在服务器启动时调用init()初始化，这里主要加载mvc配置文件，根据用户配置的控制器路径将被
	 * @MyController 
	 * 注解的类加载，存放在ca中，在之后的版本中这个初始化方法还将加载用户的拦截器列表
	 */
	@Override
	public void init() throws ServletException {

		System.out.println("=====================MyDispatcherServlet init=====================");

		this.mvcConfigLocation = getInitParameter("mvcConfigLocation");
		System.out.println(getMvcConfigLocation());
		InputStream inputStream = this.getServletContext().getResourceAsStream(
				mvcConfigLocation);

		p = new Properties();

		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

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
					System.out.println("===========加载controller:"+c.getName()+"================");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		viewPath = p.getProperty("myview.path");
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

    /**
     * 和springMVC一样使用反射来调用匹配的控制器方法
     * @param req
     * @param resp
     */
	private void doService(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURI();
		String appName = this.getServletContext().getContextPath();
		Method[] ms = null;
		MyModelAndView mv = new MyModelAndView();
		Model model = new Model();
		for(Class<?> c:ca){
			ms = c.getMethods();
			for(Method m:ms){
				if(m.isAnnotationPresent(MyRequestMapping.class)&&(appName+m.getAnnotation(MyRequestMapping.class).value()).equals(url)){
					try {
						mv = (MyModelAndView) m.invoke(c.newInstance(),new Object[]{model,req,resp});
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
		
		req.setAttribute("model", mv.getModel());
		String viewFileName = viewPath+mv.getView()+".jsp";
		try {
			this.getServletContext().getRequestDispatcher(viewFileName).forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMvcConfigLocation() {
		return mvcConfigLocation;
	}

	public void setMvcConfigLocation(String mvcConfigLocation) {
		this.mvcConfigLocation = mvcConfigLocation;
	}
}
