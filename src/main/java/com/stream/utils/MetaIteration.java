package com.stream.utils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public  class MetaIteration {
	
	public static <T>  Predicate<T> getPredicateAllTrue(Class<T> type){
		return n -> true;
	}
	
	public static <T> void transformByCondition(List<T> list, Predicate<T> p, Consumer<T> c) {
		int i = 0;
		if(isEmpty(list)) {
			return;
		}
		while(i<list.size()) {
			if(p.test(list.get(i))) {
				c.accept(list.get(i));
			}
			i++;
		}
	}
	public static <T> boolean contains(List<T> list, Predicate<T> p) {
		int i = 0;
		if(isEmpty(list)) {
			return false;
		}
		while(i<list.size()) {
			if(p.test(list.get(i))) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static <T> T search(List<T> list, Predicate<T> p) {
		int i = 0;
		if(isEmpty(list)) {
			return null;
		}
		while(i<list.size()) {
			if(p.test(list.get(i))) {
				return list.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static <T> void merge(List<T> list, Predicate<T> p, BiConsumer<T,T> biFun,Consumer<T> fun ) {
		int i = 0;
		if(isEmpty(list)) {
			return;
		}
		if(list.size()==1) {
			fun.accept(list.get(i));
			return;
		}
		T prevPair = null;
		while(i<list.size()) {
			if(p.test(list.get(i))) {
				if(prevPair==null) {
					prevPair=list.get(i);
				}else {
					biFun.accept(prevPair, list.get(i));
				}
			}
			i++;
		}
	}
	
	public static <T> boolean isEmpty(List<T> list) {
		return list==null || list.isEmpty();
	}


}
