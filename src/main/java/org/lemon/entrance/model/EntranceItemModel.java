package org.lemon.entrance.model;


import lombok.Data;

@Data
public class EntranceItemModel  {
    Long id;
    /**
     * 卡号
     */
    String cid;
    /**
     * 类型：0：车，1：人
     */
    Integer type;
    /**
     * 身份证ID、车牌号
     */
    String eid;
    /**
     * 姓名
     */
    String name;


}
