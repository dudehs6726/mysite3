package com.douzone.mysite.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class GlobalExceptionHandler {

	@AfterThrowing(value="execution(* *..*.*.*(..))", throwing="ex")
	public void afterAroundAdvice(Throwable ex) {
		
		System.out.println("call [afterThrowing advice] : " + ex);
	}
}
