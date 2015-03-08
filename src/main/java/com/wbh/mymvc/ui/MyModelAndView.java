package com.wbh.mymvc.ui;


public class MyModelAndView {
	
	
	private String view;
	
	private Model model = new Model();
	
	public MyModelAndView() {
	}

	public MyModelAndView(String view,Model model) {
		this.model = model;
		this.view = view;
	}
	
	public void addObject(String key,Object value){
		this.model.put(key, value);
	}
	
	public void removeObjecy(String key){
		this.model.remove(key);
	}
	
	public Object getObject(String key){
		return this.model.get(key);
	}
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
