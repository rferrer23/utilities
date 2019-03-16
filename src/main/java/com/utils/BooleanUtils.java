package com.utils;


public class BooleanUtils extends org.apache.commons.lang3.BooleanUtils{
	
	  public static int compare(boolean a, boolean b) {
		    return (a == b) ? 0 : (a ? 1 : -1);
		  }
}
