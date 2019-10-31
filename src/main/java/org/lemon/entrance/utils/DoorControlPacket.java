package org.lemon.entrance.utils;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 *  @author: Lemon
 *  @Date:
 *  @Description: 报文处理
 */

public class DoorControlPacket {
    public static int WGPacketSize = 64;			    //报文长度
    //2015-04-29 22:22:41  			//类型
    public static int Type = 0x17;		//2015-04-29 22:22:50			//类型
    public static long SpecialFlag = 0x55AAAA55;     //特殊标识 防止误操作

    public int functionID;		                     //功能号
    public long iDevSn;                              //设备序列号 4字节, 9位数

    public byte[] data = new byte[56];               //56字节的数据 [含流水号]
    public byte[] recv = new byte[WGPacketSize];     //接收到的数据

    public DoorControlPacket()
    {
        Reset();
    }
    public void Reset()  //数据复位
    {
        for (int i = 0; i < 56; i++)
        {
            data[i] = 0;
        }
    }
    static long sequenceId;     //序列号
    public DatagramPacket toDatagramPacket(String ip, Integer port) //生成64字节指令包
    {
        byte[] buff = new byte[WGPacketSize];
        sequenceId++;
        buff[0] = (byte)Type;
        buff[1] = (byte)functionID;
        byte[] res= ByteUtils.longToBytes(iDevSn);
        buff[4]=res[0];
        buff[5]=res[1];
        buff[6]=res[2];
        buff[7]=res[3];
        //Array.Copy(System.BitConverter.GetBytes(iDevSn), 0, buff, 4, 4);
        //Array.Copy(data, 0, buff, 8, data.Length);
        for(int i=0;i<data.length;i++){
            buff[8+i]=data[i];
        }
        //Array.Copy(System.BitConverter.GetBytes(sequenceId), 0, buff, 40, 4);
        res= ByteUtils.longToBytes(sequenceId);
        buff[40]=res[0];
        buff[41]=res[1];
        buff[42]=res[2];
        buff[43]=res[3];
        DatagramPacket packet=new DatagramPacket(buff,WGPacketSize,new InetSocketAddress(ip,port));
        return packet;
    }
}
