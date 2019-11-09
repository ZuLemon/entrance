package org.lemon.entrance.utils;

import sun.rmi.runtime.Log;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerTest {
  private static int recordIndex=0;
  public static void main(String[] args) throws Exception {
    final int LOCAL_PORT = 61005;
    final String SERVER_NAME = "192.168.1.5";
    DatagramSocket udpSocket = new DatagramSocket(LOCAL_PORT,
        InetAddress.getByName(SERVER_NAME));

    System.out.println("Created UDP  server socket at "
        + udpSocket.getLocalSocketAddress() + "...");
    while (true) {
      System.out.println("Waiting for a  UDP  packet...");
      DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
      udpSocket.receive(packet);
      displayPacketDetails(packet);
//      udpSocket.send(packet);
    }
  }
  public static void displayPacketDetails(DatagramPacket packet)  {
    byte[] msgBuffer = packet.getData();
    int length = packet.getLength();
    int offset = packet.getOffset();
    int remotePort = packet.getPort();
    InetAddress remoteAddr = packet.getAddress();
    if(msgBuffer[1]==0x20){
      int sn=ByteUtils.bytesToInt(ByteUtils.subBytes(msgBuffer,4,4));
      LoggerUtils.info("控制器SN:"+sn);
      int recordIndexGet=ByteUtils.bytesToInt(ByteUtils.subBytes(msgBuffer,8,4));
      if(recordIndex<recordIndexGet){
        recordIndex=recordIndexGet;
        new DoorControlResponse(packet);
      }

    }
//    String msg = new String(msgBuffer, 4, 4,"UTF8");
//      LoggerUtils.info(ByteUtils.bytesToString(msgBuffer,"utf-8"));
//      LoggerUtils.info(ByteUtils.bytesToString(msgBuffer,"GBK"));
//      LoggerUtils.info(ByteUtils.bytesToString(msgBuffer,"UTF8"));
//    long sn=ByteUtils.bytesToLong(ByteUtils.subBytes(msgBuffer,4,4));
//    String msg=new String(msgBuffer,4,4,"GBK");
//    System.out.println("Received a  packet:[IP Address=" + remoteAddr
//        + ", port=" + remotePort + ", message="
//             + "]");
  }
}