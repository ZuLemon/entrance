package org.lemon.entrance.model;



import lombok.Data;

@Data
public class EntranceLogModel {
    Long id;
    /**
     * 控制器IP地址
     */
    String ip;
    /**
     * 设备序列号
     */
    String devsn;
    /**
     * 记录序列号
     */
    Long index;
    /**
    //0=无记录
    //1=刷卡记录
    //2=门磁,按钮, 设备启动, 远程开门记录
    //3=报警记录	1
    */
    Integer type;
    /**
     * 卡号
     */
    String eid;
    /**
     * 门号
     */
    Integer door;
    /***
     * 通过有效性
     * 0：禁止
     * 1：通过
     */
    Integer pass;
    /**
     * 进出类型
     * 0:出
     * 1：进
     */
    Integer entrance;

    String stationid;
    EntranceItemModel eitem;
}
