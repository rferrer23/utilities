package com.stream.utils.impl;

import java.util.function.Function;

import com.stream.utils.UnChecked;

public class GeneralUnCheckedImpl implements UnChecked{
	
	
	private Function<Throwable, RuntimeException> fun;

	public GeneralUnCheckedImpl(Function<Throwable,RuntimeException> fun) {
		this.fun=fun;
	}

	public RuntimeException propagate(Throwable throwable) {
		    if (fun !=null) {
		    	return fun.apply(throwable);
		    }
		    return new UnsupportedOperationException();
	    }
}
