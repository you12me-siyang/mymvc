package com.wbh.mymvc.ui;

public class RequestResult {
	
	private String view;
	private Model model = new Model();
	private Object resultEntity;
	private int resultCode;
	private String message;
	
	
	public void addObject(String key,Object value){
		this.model.put(key, value);
	}
	
	public Object getObject(String key){
		return this.model.get(key);
	}
	
	//-------------------getter setter---------------
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
	public Object getResultEntity() {
		return resultEntity;
	}
	public void setResultEntity(Object resultEntity) {
		this.resultEntity = resultEntity;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
