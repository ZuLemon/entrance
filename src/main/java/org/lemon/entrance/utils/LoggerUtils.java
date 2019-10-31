package org.lemon.entrance.utils;


import org.lemon.entrance.model.LogLevelModel;

/**
 *  @author: Lemon
 *  @Date: 2018/10/25 12:15
 *  @Description: 日志助手
 */

public class LoggerUtils {

	private final static LoggerProxy log = LoggerProxyFactory.getProXy(LoggerUtils.class);

	public static void info(String msg) {
		log.info(LogLevelModel.INFO, msg);
	}
	public static void debug(String msg) {
		log.info(LogLevelModel.DEBUG, msg);
	}
	public static void warn(String msg) {
		log.info(LogLevelModel.WARN, msg);
	}
	public static void error(String msg) {
		log.info(LogLevelModel.ERROR, msg);
	}
}