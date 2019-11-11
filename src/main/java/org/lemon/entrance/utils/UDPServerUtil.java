package org.lemon.entrance.utils;

import com.alibaba.fastjson.JSON;
import org.lemon.entrance.model.DoorRecordModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServerUtil {

  public static final int MAX_UDP_DATA_SIZE = 1024;
  public static final int UDP_PORT = Integer.parseInt(new PropertiesUtil().getValue("entrance.listenPort"));
  private static int recordIndex = 0;
  private static String posturl=new PropertiesUtil().getValue("entrance.sendURL");

//  public static void main(String[] args) throws SocketException {
//    new Thread(new UDPProcess(UDP_PORT)).start();
//  }

  public void start(){
    try {
      new Thread(new UDPProcess(UDP_PORT)).start();
    } catch (SocketException e) {
      LoggerUtils.error(e.getMessage());
    }
  }

   class UDPProcess implements Runnable {
    DatagramSocket socket = null;

    public UDPProcess(final int port) throws SocketException {
      socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
      while (true) {
        byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
          socket.receive(packet);
          new Thread(new Process(packet)).start();
        } catch (IOException e) {
          LoggerUtils.error(e.getMessage());
        }
      }

    }
  }

   class Process implements Runnable {

    public Process(DatagramPacket packet) {
//            byte[] buffer = packet.getData();// 接收到的UDP信息，然后解码
      displayPacketDetails(packet);
    }

    @Override
    public void run() {
    }

  }


  private void displayPacketDetails(DatagramPacket packet) {
    byte[] msgBuffer = packet.getData();
//    int length = packet.getLength();
//    int offset = packet.getOffset();
//    int remotePort = packet.getPort();
    InetAddress remoteAddr = packet.getAddress();
    if (msgBuffer[1] == 0x20) {
      int recordIndexGet = ByteUtils.bytesToInt(ByteUtils.subBytes(msgBuffer, 8, 4));
      if (recordIndex < recordIndexGet) {
        recordIndex = recordIndexGet;
        DoorRecordModel doorRecordModel = DoorControlClient.parseRcordInfo(packet.getData());
        doorRecordModel.setControllerIp(remoteAddr.toString());
        LoggerUtils.info(JSON.toJSONString(doorRecordModel));
        HttpClientUtil.sendJSONRequest(posturl, JSON.toJSONString(doorRecordModel));
      }

    }
  }
}