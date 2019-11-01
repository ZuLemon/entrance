package org.lemon.entrance.model;

/**
 * Created by Lemon on 2017/1/10.
 */

import lombok.Data;

/**
 * 服务器返回对象
 */
@Data
public class ReturnModel {
    private Boolean success = true;
    private Object object = null;
    private String exception = "";

}