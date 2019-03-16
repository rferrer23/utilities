package com.rits.compare.report;

import java.util.Collection;

public class ReportBase<T> {
	
	private T value;
	private Class<T> type;
	
	public ReportBase(T value, Class<T> type) {
		this.value=value;
		this.type=type;
	}
	
	public boolean isAReportBaseInstance() {
		return value instanceof ReportBase;
	}
	
	public boolean isACollection() {
		return value instanceof Collection;
	}
	
	public Class<T>  getType(){
		return type;
	}
	
	public T getValue() {
		return value;
	}

}
