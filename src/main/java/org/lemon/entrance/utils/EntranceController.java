package org.lemon.entrance.utils;



public class EntranceController {
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getDevsn() {
        return devsn;
    }

    public void setDevsn(String devsn) {
        this.devsn = devsn;
    }

    public String getChncommand() {

            chncommand="dc_cmd_"+devsn;

        return chncommand;
    }

    public void setChncommand(String chncommand) {
        this.chncommand = chncommand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
