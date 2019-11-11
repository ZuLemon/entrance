package org.lemon.entrance.utils;


import java.net.DatagramPacket;

/**
 * 门禁控制器执行返回状态
 */
public class DoorControlResponse {
    /**
     * 兼容Response定义
     */
    int code;
    String msg;
    boolean success=false;
    byte recv[];
    /**
     * 最后一条记录信息
     */
    int[] doorStatus = new int[4];
    int[] pbStatus = new int[4];
    int[] relayStatus=new int[4];
    /**
     //36	故障号
     //等于0 无故障
     //不等于0, 有故障(先重设时间, 如果还有问题, 则要返厂家维护)	1
     */
    int errCode;
    /**
     //40-43	流水号	4
     */
    long sequenceId = 0;
    /**
     //49	继电器状态	1	 [0表示门上锁, 1表示门开锁. 正常门上锁时, 值为0000]
     */

    /**
     //50	门磁状态的8-15bit位[火警/强制锁门]
     //Bit0  强制锁门
     //Bit1  火警
     */
    int otherInputStatus;
    /**
     * 控制器时间
     */
    String controllerTime = "2000-01-01 00:00:00";

    public DoorControlResponse(DatagramPacket packet) {
        code=0;
        msg="操作成功";
        recv=packet.getData();
//        DoorControlClient.parseRcordInfo(recv);
        //	其他信息
        //28	1号门门磁(0表示关上, 1表示打开)	1	0x00
        doorStatus[1 - 1] = recv[28];
        //29	2号门门磁(0表示关上, 1表示打开)	1	0x00
        doorStatus[2 - 1] = recv[29];
        //30	3号门门磁(0表示关上, 1表示打开)	1	0x00
        doorStatus[3 - 1] = recv[30];
        //31	4号门门磁(0表示关上, 1表示打开)	1	0x00
        doorStatus[4 - 1] = recv[31];

        //32	1号门按钮(0表示松开, 1表示按下)	1	0x00
        pbStatus[1 - 1] = recv[32];
        //33	2号门按钮(0表示松开, 1表示按下)	1	0x00
        pbStatus[2 - 1] = recv[33];
        //34	3号门按钮(0表示松开, 1表示按下)	1	0x00
        pbStatus[3 - 1] = recv[34];
        //35	4号门按钮(0表示松开, 1表示按下)	1	0x00
        pbStatus[4 - 1] = recv[35];

        //36	故障号
        //等于0 无故障
        //不等于0, 有故障(先重设时间, 如果还有问题, 则要返厂家维护)	1
        int errCode = recv[36];

        //37	控制器当前时间
        //时	1	0x21
        //38	分	1	0x30
        //39	秒	1	0x58

        //40-43	流水号	4
        long sequenceId = 0;
        sequenceId = ByteUtils.bytesToInt(ByteUtils.cutOut(recv,40,4));
//        ByteUtils.bytesToLong(recv, 40, 4);
        //48
        //特殊信息1(依据实际使用中返回)
        //键盘按键信息	1


        //49	继电器状态	1	 [0表示门上锁, 1表示门开锁. 正常门上锁时, 值为0000]
        int relayStatusVal = recv[49];
        if ((relayStatusVal & 0x1) > 0)
        {
            //一号门 开锁
            relayStatus[0]=0;
        }
        else
        {
            //一号门 上锁
            relayStatus[0]=1;
        }
        if ((relayStatusVal & 0x2) > 0)
        {
            //二号门 开锁
            relayStatus[1]=0;
        }
        else
        {
            //二号门 上锁
            relayStatus[1]=1;
        }
        if ((relayStatusVal & 0x4) > 0)
        {
            //三号门 开锁
            relayStatus[2]=0;
        }
        else
        {
            //三号门 上锁
            relayStatus[2]=1;
        }
        if ((relayStatusVal & 0x8) > 0)
        {
            //四号门 开锁
            relayStatus[3]=0;
        }
        else
        {
            //四号门 上锁
            relayStatus[3]=1;
        }

        //50	门磁状态的8-15bit位[火警/强制锁门]
        //Bit0  强制锁门
        //Bit1  火警
        int otherInputStatus = recv[50];
        if ((otherInputStatus & 0x1) > 0)
        {
            //强制锁门
        }
        if ((otherInputStatus & 0x2) > 0)
        {
            //火警
        }

        //51	V5.46版本支持 控制器当前年	1	0x13
        //52	V5.46版本支持 月	1	0x06
        //53	V5.46版本支持 日	1	0x22

        controllerTime = String.format("%02x%02x-%02x-%02x %02x:%02x:%02x",
                0x20, recv[51],recv[52], recv[53], recv[37], recv[38], recv[39]);
    }

    public byte[] getRecv() {
        return recv;
    }

    public void setRecv(byte[] recv) {
        this.recv = recv;
    }

//    public EntranceLog getLog() {
//        return log;
//    }
//
//    public void setLog(EntranceLog log) {
//        this.log = log;
//    }

    public int[] getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(int[] doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int[] getPbStatus() {
        return pbStatus;
    }

    public void setPbStatus(int[] pbStatus) {
        this.pbStatus = pbStatus;
    }

    public int[] getRelayStatus() {
        return relayStatus;
    }

    public void setRelayStatus(int[] relayStatus) {
        this.relayStatus = relayStatus;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getOtherInputStatus() {
        return otherInputStatus;
    }

    public void setOtherInputStatus(int otherInputStatus) {
        this.otherInputStatus = otherInputStatus;
    }

    public String getControllerTime() {
        return controllerTime;
    }

    public void setControllerTime(String controllerTime) {
        this.controllerTime = controllerTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
