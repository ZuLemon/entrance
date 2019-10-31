package org.lemon.entrance.utils;
/**
 * 代理日志定义
 **/
import org.lemon.entrance.model.LogLevelModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  @author: Lemon
 *  @Date: 2018/10/25 12:17
 *  @Description: 日志代理
 */
public class LoggerProxy {
	private static final Logger log = LoggerFactory.getLogger(LoggerProxy.class);
	public void info(int level, String msg) {
		switch (level){
			case LogLevelModel.DEBUG:
				log.debug(msg);
				break;
			case LogLevelModel.ERROR:
				log.error(msg);
				break;
			case LogLevelModel.WARN:
				log.warn(msg);
				break;
			default:
				log.info(msg);
		}
	}
}