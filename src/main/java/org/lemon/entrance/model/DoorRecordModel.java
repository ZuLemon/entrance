package org.lemon.entrance.model;

import lombok.Data;

/**
 * @Author : Lemon
 * @Desc : 控制器返回记录
 * @Date : 2019/11/11 19:19
 **/
@Data
public class DoorRecordModel {
    /**SN**/
    private Integer devsn;
    /**控制器IP**/
    private String controllerIp;
    /**记录索引**/
    private Integer recordIndex;
    /**
     * 记录类型*
     *0=无记录
     *1=刷卡记录
     *2=门磁,按钮, 设备启动, 远程开门记录
     *3=报警记录
     * */
    private Integer recordType;
    /**
     * 有效性
     * 0 不通过
     * 1 通过
     */
    private Integer recordValid;
    /**卡号**/
    private String recordCardNo;
    /**门号**/
    private Integer recordDoorNO;
    /**
     * 进门/出门
     * 1表示进门
     * 2表示出门)*
     * */
    private String recordInOrOut;
    /**记录时间**/
    private String recordTime;
    /**描述**/
    private String recordDesc;
}
