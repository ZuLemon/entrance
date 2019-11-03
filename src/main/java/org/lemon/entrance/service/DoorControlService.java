package org.lemon.entrance.service;

import org.lemon.entrance.model.EntranceControllerModel;
import org.lemon.entrance.model.EntranceRBACModel;
import org.lemon.entrance.utils.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DoorControlService {
    DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 查询控制器状态
     * @param controller
     * @return
     */
    public DoorControlResponse queryStatus(EntranceControllerModel controller) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x20;
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            res.setSuccess(true);
        }
        return res;
    }

    /**
     * 查询指定索引记录
     * @param controller
     * @param index
     * @return
     */
    public DoorControlResponse queryRecordIndex(EntranceControllerModel controller, int index) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0xB0;
        packet.data=ByteUtils.intToBytes(index);
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            res.setSuccess(true);
        }
        return res;
    }

    /**
     * 远程开门
     * @param controller
     * @param door
     * @return
     */
    public DoorControlResponse openDoor(EntranceControllerModel controller, int door) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x40;
        packet.data[0] = (byte)(door & 0xff); //2013-11-03 20:56:33
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        res.setSuccess(res.getRecv()[8]==1);
        return res;
    }
    /**
     * 设置开门时延/在线
     * @param controller
     * @param door 门号
     * @param delay 延迟
     * @param online 0x03在线 0x02 常闭 01常开
     * @return
     */
    public DoorControlResponse setDoorOpenDelay(EntranceControllerModel controller, int door, int delay, int online) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x80;

        packet.data[0] = (byte)(door & 0xff);
        packet.data[1] = (byte)(online & 0xff);
        packet.data[2] = (byte)(delay & 0xff);
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null) {
            if (res.getRecv()[0] == res.getRecv()[8] && res.getRecv()[1] == res.getRecv()[9] && res.getRecv()[2] == res.getRecv()[10]) {
                //成功时, 返回值与设置一致
                res.setSuccess(true);
            } else {
                //失败
                res.setSuccess(false);
            }
        }
        return res;
    }

    public DoorControlResponse timeCheck(EntranceControllerModel controller,String date) throws ParseException {
        Calendar calendar=Calendar.getInstance();
        Date myDate2 = dateFormat2.parse(date);
        calendar.setTime(myDate2);
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x30;
        packet.data[0] = ByteUtils.intToHexByte((calendar.get(Calendar.YEAR) - Calendar.getInstance().get(Calendar.YEAR) % 100) / 100);
        packet.data[1] = ByteUtils.intToHexByte((int)(calendar.get(Calendar.YEAR) % 100)); //st.GetMonth());
        packet.data[2] = ByteUtils.intToHexByte(calendar.get(Calendar.MONTH));
        packet.data[3] = ByteUtils.intToHexByte(calendar.get(Calendar.DAY_OF_MONTH));
        packet.data[4] = ByteUtils.intToHexByte(calendar.get(Calendar.HOUR_OF_DAY));
        packet.data[5] = ByteUtils.intToHexByte(calendar.get(Calendar.MINUTE));
        packet.data[6] = ByteUtils.intToHexByte(calendar.get(Calendar.SECOND));
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null) {
            Boolean bSame = true;
            for (int i = 0; i < 7; i++)
            {
                if (packet.data[i] != res.getRecv()[8 + i])
                {
                    bSame = false;
                    break;
                }
            }
            if (bSame)
            {
                res.setSuccess(true);
            }else{
                res.setSuccess(false);
            }
        }
        return res;
    }

    public DoorControlResponse timeRead(EntranceControllerModel controller) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x32;
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            res.setSuccess(true);
        }
        return res;
    }
    /**
     * 添加或修改
     * @param rbac
     */
    public DoorControlResponse rbacInsertOrUpdate(EntranceControllerModel controller, EntranceRBACModel rbac) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x50;
        String cid=rbac.getCid().trim();
        byte[] cidb=ByteUtils.intToBytes(Integer.parseInt(cid));
        packet.data[0]=cidb[0];
        packet.data[1]=cidb[1];
        packet.data[2]=cidb[2];
        packet.data[3]=cidb[3];
        System.out.println(">卡号>"+ByteUtils.bytesToInt(cidb));
        Calendar cal=Calendar.getInstance();
        cal.setTime(rbac.getTmstart());
        //20 10 01 01 起始日期:  2010年01月01日   (必须大于2001年)
        packet.data[4] = ByteUtils.intToHexByte((cal.get(Calendar.YEAR) -cal.get(Calendar.YEAR) % 100) / 100);
        packet.data[5] = ByteUtils.intToHexByte((int)(cal.get(Calendar.YEAR) % 100)); //st.GetMonth());
        packet.data[6] = ByteUtils.intToHexByte(cal.get(Calendar.MONTH));
        packet.data[7] = ByteUtils.intToHexByte(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(">开始时间>"+ packet.data[4]+"-"+packet.data[5]+"-"+packet.data[6]+"-"+packet.data[7]);
        //20 29 12 31 截止日期:  2029年12月31日
        cal.setTime(rbac.getTmend());
        packet.data[8] = ByteUtils.intToHexByte((cal.get(Calendar.YEAR) - cal.get(Calendar.YEAR) % 100) / 100);
        packet.data[9] = ByteUtils.intToHexByte((int)(cal.get(Calendar.YEAR) % 100)); //st.GetMonth());
        packet.data[10] = ByteUtils.intToHexByte(cal.get(Calendar.MONTH));
        packet.data[11] = ByteUtils.intToHexByte(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(">结束时间>"+packet.data[8]+"-"+packet.data[9]+"-"+packet.data[10]+"-"+packet.data[11]);
        //01 允许通过 一号门 [对单门, 双门, 四门控制器有效]
        packet.data[12] = rbac.getDoor1().byteValue();
        //01 允许通过 二号门 [对双门, 四门控制器有效]
        packet.data[13] = rbac.getDoor2().byteValue();  //如果禁止2号门, 则只要设为 0x00
        //01 允许通过 三号门 [对四门控制器有效]
        packet.data[14] = rbac.getDoor3().byteValue();
        //01 允许通过 四号门 [对四门控制器有效]
        packet.data[15] = rbac.getDoor4().byteValue();

        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            if(res.getRecv()[8]==1) {
                res.setSuccess(true);
            }else{
                res.setSuccess(false);
                res.setCode(1);
                res.setMsg("权限添加失败");
            }
        }
        return res;
    }

    /**
     * 清除卡号权限
     * @param controller
     * @param rbac
     * @return
     */
    public DoorControlResponse rbacRemove(EntranceControllerModel controller, EntranceRBACModel rbac) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x52;
        byte[] cidb=ByteUtils.intToBytes(Integer.parseInt(rbac.getCid().trim()));
        packet.data[0]=cidb[0];
        packet.data[1]=cidb[1];
        packet.data[2]=cidb[2];
        packet.data[3]=cidb[3];
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            if(res.getRecv()[8]==1) {
                res.setSuccess(true);
            }else{
                res.setSuccess(false);
                res.setCode(1);
                res.setMsg("权限删除失败");
            }
        }
        return res;
    }

    /**
     * 清除控制器权限
     * @param controller
     * @return
     */
    public DoorControlResponse rbacClear(EntranceControllerModel controller) {
        DoorControlPacket packet=new DoorControlPacket();
        packet.iDevSn=Long.parseLong(controller.getDevsn());
        packet.functionID=0x54;
        packet.data[0]=0x55;
        packet.data[1]=(byte)0xaa;
        packet.data[2]=(byte)0xaa;
        packet.data[3]=0x55;
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            if(res.getRecv()[8]==1) {
                res.setSuccess(true);
            }else{
                res.setSuccess(false);
            }
        }
        return res;
    }

    /**
     * 获取权限记录
     * @param controller
     * @return
     */
    public DoorControlResponse rbacCount(EntranceControllerModel controller) {
        DoorControlPacket packet = new DoorControlPacket();
        packet.iDevSn = Long.parseLong(controller.getDevsn());
        packet.functionID=0x58;
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            res.setSuccess(true);
            res.setMsg(String.valueOf(ByteUtils.bytesToInt(ByteUtils.subBytes(res.getRecv(),8,4))));
        }
        return res;
    }

    /**
     * 获取指定索引权限
     * @param controller
     * @return
     */
    public DoorControlResponse getIndexRbac(EntranceControllerModel controller, int index) {
        DoorControlPacket packet = new DoorControlPacket();
        packet.iDevSn = Long.parseLong(controller.getDevsn());
        packet.functionID=0x5C;
        packet.data=ByteUtils.intToBytes(index);
        DoorControlResponse res= DoorControlClient.send(packet.toDatagramPacket(controller.getIp(),controller.getPort()));
        if(res!=null){
            res.setSuccess(true);
            res.setMsg(String.valueOf(ByteUtils.bytesToInt(ByteUtils.subBytes(res.getRecv(),8,4))));
        }
        return res;
    }
}
