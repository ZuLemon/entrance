package org.lemon.entrance.utils;

import org.lemon.entrance.model.DoorRecordModel;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 描述：向门禁控制器发送控制命令
 */
public class DoorControlClient {
    private static Object own= DoorControlClient.class;
    /// <summary>
    /// 显示记录信息
    /// </summary>
    /// <param name="recv"></param>
    public static DoorRecordModel parseRcordInfo(byte[] recv)
    {
        DoorRecordModel recordModel=new DoorRecordModel();
//        for(int i=0;i<recv.length;i++){
//            System.out.print(String.format("%02X ",recv[i]));
//        }
//        LoggerUtils.debug("接收字节"+recv.length);
//        EntranceLog log=new EntranceLog();
        int devsn= ByteUtils.bytesToInt(recv,4,4);
        recordModel.setDevsn(devsn);
//        LoggerUtils.info("SN："+devsn);
//        log.setDevsn(devsn.toString());
//        LoggerUtils.debug(devsn.toString());
        //8-11	记录的索引号
        //(=0表示没有记录)	4	0x00000000
        int recordIndex = ByteUtils.bytesToInt(ByteUtils.subBytes(recv,8,4));
        recordModel.setRecordIndex(recordIndex);
//        ByteUtils.byteToLong(recv, 8, 4);
//        log.setIndex(recordIndex);
//        LoggerUtils.debug( String.valueOf(recordIndex));

        //12	记录类型**********************************************
        //0=无记录
        //1=刷卡记录
        //2=门磁,按钮, 设备启动, 远程开门记录
        //3=报警记录	1
        //0xFF=表示指定索引位的记录已被覆盖掉了.  请使用索引0, 取回最早一条记录的索引值
        int recordType=recv[12];
        recordModel.setRecordType(recordType);
//        log.setType(recordType);
//        LoggerUtils.debug("记录类型"+recordType);

        //13	有效性(0 表示不通过, 1表示通过)	1
        int recordValid = recv[13];
        recordModel.setRecordValid(recordValid);
//        log.setPass((int)recv[13]);
//        LoggerUtils.debug(String.valueOf((int)recv[13]));

        //14	门号(1,2,3,4)	1
        int recordDoorNO = recv[14];
        recordModel.setRecordDoorNO(recordDoorNO);
//        log.setDoor((int)recv[13]);
//        LoggerUtils.debug(String.valueOf((int)recv[13]));
        //15	进门/出门(1表示进门, 2表示出门)	1	0x01
        int recordInOrOut = recv[15];
        recordModel.setRecordInOrOut((recordInOrOut == 1 ? "进门" : "出门"));
//        log.setEntrance((int)recv[15]);
//        LoggerUtils.debug("recv[15]>"+String.valueOf((int)recv[15]));
        //16-19	卡号(类型是刷卡记录时)
        //或编号(其他类型记录)	4
        String recordCardNO = null;
        recordCardNO = String.valueOf(ByteUtils.bytesToInt(ByteUtils.subBytes(recv,16,4)));
        recordModel.setRecordCardNo(recordCardNO);
//        ByteUtils.byteToHexString(recv, 16, 4);
//        log.setEid(recordCardNO);
//        LoggerUtils.debug("recordCardNO>"+recordCardNO);
        //20-26	刷卡时间:
        //年月日时分秒 (采用BCD码)见设置时间部分的说明
        String recordTime = "2000-01-01 00:00:00";
        recordTime = String.format("%02x%02x-%02x-%02x %02x:%02x:%02x",
                recv[20], recv[21], recv[22], recv[23], recv[24], recv[25], recv[26]);
        recordModel.setRecordTime(recordTime);
//        log.setCreateDate(DateTimeUtils.sm_sf_parse(recordTime));
//        LoggerUtils.debug(recordTime);

        //2012.12.11 10:49:59	7
        //27	记录原因代码(可以查 “刷卡记录说明.xls”文件的ReasonNO)
        //处理复杂信息才用	1
        int reason = recv[27];
        recordModel.setRecordDesc(getReasonDetailChinese(reason));
        //0=无记录
        //1=刷卡记录
        //2=门磁,按钮, 设备启动, 远程开门记录
        //3=报警记录	1
        //0xFF=表示指定索引位的记录已被覆盖掉了.  请使用索引0, 取回最早一条记录的索引值
//        if (recordType == 0)
//        {
//            //log(string.Format("索引位={0}  无记录", recordIndex));
////            log.setRemarks("索引位="+recordIndex+"  无记录");
////            LoggerUtils.debug("索引位="+recordIndex+"  无记录");
//
//        }
//        else if (recordType == 0xff)
//        {
////            log.setRemarks(" 指定索引位的记录已被覆盖掉了,请使用索引0, 取回最早一条记录的索引值");
////            LoggerUtils.debug("指定索引位的记录已被覆盖掉了,请使用索引0, 取回最早一条记录的索引值");
//        }
//        else if (recordType == 1) //2015-06-10 08:49:31 显示记录类型为卡号的数据
//        {
//            //卡号
//            //log(string.Format("索引位={0}  ", recordIndex));
//            //log(string.Format("  卡号 = {0}", recordCardNO));
//            //log(string.Format("  门号 = {0}", recordDoorNO));
//            //log(string.Format("  进出 = {0}", recordInOrOut == 1 ? "进门" : "出门"));
//            //log(string.Format("  有效 = {0}", recordValid == 1 ? "通过" : "禁止"));
//            //log(string.Format("  时间 = {0}", recordTime));
//            //log(string.Format("  描述 = {0}", getReasonDetailChinese(reason)));
////            log.setRemarks(getReasonDetailChinese(reason));
//            LoggerUtils.debug("卡开门 索引位=  "+recordIndex);
//            LoggerUtils.debug("编号= "+recordCardNO);
//            LoggerUtils.debug("门号=  "+recordDoorNO);
//            LoggerUtils.debug("进出=  "+ (recordInOrOut == 1 ? "进门" : "出门"));
//            LoggerUtils.debug("有效=  "+(recordValid == 1 ? "通过" : "禁止"));
//            LoggerUtils.debug("时间=  "+recordTime);
//            LoggerUtils.debug("描述 = "+getReasonDetailChinese(reason));
//        }
//        else if (recordType == 2)
//        {
//            //其他处理
//            //门磁,按钮, 设备启动, 远程开门记录
//            LoggerUtils.debug("远程开门记录 索引位=  "+recordIndex);
//            LoggerUtils.debug("编号= "+recordCardNO);
//            LoggerUtils.debug("门号=  "+recordDoorNO);
//            LoggerUtils.debug("时间=  "+recordTime);
//            LoggerUtils.debug("描述 = "+getReasonDetailChinese(reason));
//        }
//        else if (recordType == 3)
//        {
//            //其他处理
//            //报警记录
//            //log(string.Format("索引位={0}  报警记录", recordIndex));
//            //log(string.Format("  编号 = {0}", recordCardNO));
//            //log(string.Format("  门号 = {0}", recordDoorNO));
//            //log(string.Format("  时间 = {0}", recordTime));
//            //log(string.Format("  描述 = {0}", getReasonDetailChinese(reason)));
////            log.setRemarks(getReasonDetailChinese(reason));
//            LoggerUtils.debug("报警记录索引位=  "+recordIndex);
//            LoggerUtils.debug("编号= "+recordCardNO);
//            LoggerUtils.debug("门号=  "+recordDoorNO);
//            LoggerUtils.debug("时间=  "+recordTime);
//            LoggerUtils.debug("描述 = "+getReasonDetailChinese(reason));
//        }
        return recordModel;
    }

    static String[] RecordDetails ={
            //记录原因 (类型中 SwipePass 表示通过; SwipeNOPass表示禁止通过; ValidEvent 有效事件(如按钮 门磁 超级密码开门); Warn 报警事件)
            //代码  类型   英文描述  中文描述
            "1","SwipePass","Swipe","刷卡开门",
            "2","SwipePass","Swipe Close","刷卡关",
            "3","SwipePass","Swipe Open","刷卡开",
            "4","SwipePass","Swipe Limited Times","刷卡开门(带限次)",
            "5","SwipeNOPass","Denied Access: PC Control","刷卡禁止通过: 电脑控制",
            "6","SwipeNOPass","Denied Access: No PRIVILEGE","刷卡禁止通过: 没有权限",
            "7","SwipeNOPass","Denied Access: Wrong PASSWORD","刷卡禁止通过: 密码不对",
            "8","SwipeNOPass","Denied Access: AntiBack","刷卡禁止通过: 反潜回",
            "9","SwipeNOPass","Denied Access: More Cards","刷卡禁止通过: 多卡",
            "10","SwipeNOPass","Denied Access: First Card Open","刷卡禁止通过: 首卡",
            "11","SwipeNOPass","Denied Access: Door Set NC","刷卡禁止通过: 门为常闭",
            "12","SwipeNOPass","Denied Access: InterLock","刷卡禁止通过: 互锁",
            "13","SwipeNOPass","Denied Access: Limited Times","刷卡禁止通过: 受刷卡次数限制",
            "14","SwipeNOPass","Denied Access: Limited Person Indoor","刷卡禁止通过: 门内人数限制",
            "15","SwipeNOPass","Denied Access: Invalid Timezone","刷卡禁止通过: 卡过期或不在有效时段",
            "16","SwipeNOPass","Denied Access: In Order","刷卡禁止通过: 按顺序进出限制",
            "17","SwipeNOPass","Denied Access: SWIPE GAP LIMIT","刷卡禁止通过: 刷卡间隔约束",
            "18","SwipeNOPass","Denied Access","刷卡禁止通过: 原因不明",
            "19","SwipeNOPass","Denied Access: Limited Times","刷卡禁止通过: 刷卡次数限制",
            "20","ValidEvent","Push Button","按钮开门",
            "21","ValidEvent","Push Button Open","按钮开",
            "22","ValidEvent","Push Button Close","按钮关",
            "23","ValidEvent","Door Open","门打开[门磁信号]",
            "24","ValidEvent","Door Closed","门关闭[门磁信号]",
            "25","ValidEvent","Super Password Open Door","超级密码开门",
            "26","ValidEvent","Super Password Open","超级密码开",
            "27","ValidEvent","Super Password Close","超级密码关",
            "28","Warn","Controller Power On","控制器上电",
            "29","Warn","Controller Reset","控制器复位",
            "30","Warn","Push Button Invalid: Disable","按钮不开门: 按钮禁用",
            "31","Warn","Push Button Invalid: Forced Lock","按钮不开门: 强制关门",
            "32","Warn","Push Button Invalid: Not On Line","按钮不开门: 门不在线",
            "33","Warn","Push Button Invalid: InterLock","按钮不开门: 互锁",
            "34","Warn","Threat","胁迫报警",
            "35","Warn","Threat Open","胁迫报警开",
            "36","Warn","Threat Close","胁迫报警关",
            "37","Warn","Open too long","门长时间未关报警[合法开门后]",
            "38","Warn","Forced Open","强行闯入报警",
            "39","Warn","Fire","火警",
            "40","Warn","Forced Close","强制关门",
            "41","Warn","Guard Against Theft","防盗报警",
            "42","Warn","7*24Hour Zone","烟雾煤气温度报警",
            "43","Warn","Emergency Call","紧急呼救报警",
            "44","RemoteOpen","Remote Open Door","操作员远程开门",
            "45","RemoteOpen","Remote Open Door By USB Reader","发卡器确定发出的远程开门"
    };
    static String getReasonDetailChinese(int Reason) //中文
    {
        if (Reason > 45)
        {
            return "";
        }
        if (Reason <= 0)
        {
            return "";
        }
        return RecordDetails[(Reason - 1) * 4 + 3]; //中文信息
    }

    static String getReasonDetailEnglish(int Reason) //英文描述
    {
        if (Reason > 45)
        {
            return "";
        }
        if (Reason <= 0)
        {
            return "";
        }
        return RecordDetails[(Reason - 1) * 4 + 2]; //英文信息
    }
    /**
     * =================================================================================================================
     * 网络层驱动
     */
    private static DatagramSocket clientSocket = null;

    /**
     * 获取本地UPD SOCKET对象
     * @return
     */
    public static DatagramSocket getDatagramSocket()  {
        try {
            if(clientSocket==null){
                clientSocket=  new DatagramSocket( ) ;
                clientSocket.setSoTimeout(3000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    public static DoorControlResponse send(DatagramPacket packet) throws Exception {
        try {
            getDatagramSocket().send(packet);
//            System.out.println("lenght:"+packet.getLength());
//            for(int i=0;i<packet.getLength();i++){
//                System.out.print(String.format("%02X ",packet.getData()[i]));
//            }
            byte recvdata[]=new byte[128];
            packet.setData(recvdata);
            getDatagramSocket().receive(packet);
            return new DoorControlResponse(packet);
        } catch ( Exception e) {
           LoggerUtils.error(e.getMessage());
           throw new Exception(e.getMessage());
        }
    }
}
