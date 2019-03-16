package com.rits.compare;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.rits.cloning.ICloningStrategy;
import com.rits.compare.report.ReportBase;


public class Compare {
	private final Set<Class<?>> ignored = new HashSet<Class<?>>();
	private final Map<Class<?>, ICompareObjects> compareFunctions = new HashMap<Class<?>, ICompareObjects>();
	
	private final Map<Object, Boolean> ignoredInstances = new IdentityHashMap<Object, Boolean>();
	private final ConcurrentHashMap<Object, ReportBase> fieldsCache = new ConcurrentHashMap<Object,ReportBase>();
	private final List<ICloningStrategy> cloningStrategies = new LinkedList<ICloningStrategy>();
	
	public <T> ReportBase compare(T elem1,T elem2) {
		List<ReportBase> inform = new ArrayList<ReportBase>();
		if(elem1==elem2) {
			return new ReportBase<Boolean>(true,Boolean.class);
		}
		if(elem1==null) {
			return new ReportBase<NullElement>(new NullElement(0),NullElement.class);
		}else if(elem2==null) {
			return new ReportBase<NullElement>(new NullElement(1),NullElement.class);
		}
		Map<String,String> map = new HashMap<String, String>();
		if(elem2.getClass()!=elem1.getClass()) {
			map.put("className:0",elem2.getClass().getName());
			map.put("className:1",elem1.getClass().getName());
			return new ReportBase<Map>(map, Map.class);
		}
		
		if(fieldsCache.contains(elem1)) {
			internalCompare(elem1, elem2, inform, map);
			ReportBase report = compareReport(inform, map);
			fieldsCache.put(elem1, report);
			return report;
		}else {
			return fieldsCache.get(elem1);
		}

	}

	private ReportBase compareReport(List<ReportBase> inform, Map<String, String> map) {
		if(map.isEmpty() && inform.isEmpty()) {
			return new ReportBase<Boolean>(true,Boolean.class);
		}
		
		if(!map.isEmpty() && inform.isEmpty()) {
			return new ReportBase<Map>(map, Map.class);
		}
		if(!map.isEmpty() && !inform.isEmpty()) {
			inform.add(new ReportBase<Map>(map, Map.class));
		}
		
		return new ReportBase<List>(inform, List.class);
	}

	private <T> void internalCompare(T elem1, T elem2, List<ReportBase> inform, Map<String, String> map) {
		Field[] fields = elem2.getClass().getDeclaredFields();
		if(fields!=null && fields.length>0) {
			for(Field field:fields) {
				Boolean isIgnore = ignoredInstances.get(field.getClass());
				if(isTrue(isIgnore)) {
					continue;
				}
				field.setAccessible(true);
				ICompareObjects fun = compareFunctions.get(field.getClass());
				if(fun!=null) {
					existCompareFun(fun, elem1, elem2, field, map);
				}else {
					deepCompare(elem1, elem2, inform, field);
				}
			}
		}
	}

	private <T> void deepCompare(T elem1, T elem2, List<ReportBase> inform, Field field) {
		try {
			ReportBase result =compare(field.get(elem1), field.get(elem2));
			if(result!=null) {
				if(result.getClass().equals(Boolean.class) || !isTrue((Boolean)result.getValue())) {
					inform.add(new ReportBase<FieldResultEntry>(new FieldResultEntry(field.getName(), result),FieldResultEntry.class));
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private <T> void existCompareFun(ICompareObjects fun,T elem1,T elem2,Field field,Map<String,String> map) {
		try {
			Boolean result = fun.areEquals(field.get(elem1), field.get(elem2));
			if(isTrue(result)) {
				return;
			}else {
				map.put(field.getName()+":0", field.get(elem1).toString());
				map.put(field.getName()+":1", field.get(elem2).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private boolean isTrue(Boolean isIgnore) {
		return isIgnore!=null && isIgnore;
	}
	
	public static class NullElement{

		private int argPos;

		public NullElement(int i) {
			this.argPos = i;
		}

		public int getArgPos() {
			return argPos;
		}
	}
	
	
	public static class FieldResultEntry{

		private String name;
		private ReportBase base;

		public FieldResultEntry(String name,ReportBase base) {
			this.name = name;
			this.base=base;
		}

		public String getName() {
			return name;
		}

		public ReportBase getBase() {
			return base;
		}
		
	}
}
