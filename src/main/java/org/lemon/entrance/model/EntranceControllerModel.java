package org.lemon.entrance.model;


import lombok.Data;

@Data
public class EntranceControllerModel {
    String id;
    String ip;
    Integer port;
    String stationid;
    String devsn;
    String title;
    /**
     * 用于交互的命令通道topic
     */
    String chncommand;


}
