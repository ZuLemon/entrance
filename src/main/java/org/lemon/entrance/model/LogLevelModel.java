package org.lemon.entrance.model;

import lombok.Data;

/**
 *  @author: Lemon
 *  @Date: 2018/10/25 12:34
 *  @Description: 日志级别
 **/
@Data
public final class LogLevelModel {
    public final static int ERROR=3;
    public final static int WARN=4;
    public final static int INFO=6;
    public final static int DEBUG=7;
}
