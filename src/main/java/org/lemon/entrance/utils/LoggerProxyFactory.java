package org.lemon.entrance.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


import org.slf4j.log4j12.Log4jLoggerAdapter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


/**
 *  @author: Lemon
 *  @Date: 2018/10/25 12:15
 *  @Description: 日志代理工厂
 */
public final class LoggerProxyFactory {
	public final static LoggerProxy getProXy(Class<?> cls) {
		try {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(LoggerProxy.class);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					Field nameField = Log4jLoggerAdapter.class.getDeclaredField("FQCN");
					Field modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
					nameField.setAccessible(true);
					nameField.set(null, cls.getName());
					Object result = proxy.invokeSuper(obj, args);
					nameField.set(null, Log4jLoggerAdapter.class.getName());
					return result;
				}
			});
			return (LoggerProxy) enhancer.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LoggerProxy();
	}
}

