package org.lemon.entrance.utils;

import java.nio.charset.Charset;



/**
 *  @author: Lemon
 *  @Date: 2019/10/31 22:14
 *  @Description: Byte 数据处理
 */
public class ByteUtils {

    /**
     * (01)、位移加密
     * @param bytes
     */
    public static void byteJiaMi(byte[] bytes){
        for (int w = 0; w < bytes.length; w++){
            int a = bytes[w];
            a = ~a;
            bytes[w] = (byte)a;
        }
    }


    /**
     * 字符串转数组
     * @param str
     * @return
     */
    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }


    /**
     * (02)、从bytes上截取一段
     * @param bytes 母体
     * @param off 起始
     * @param length 个数
     * @return byte[]
     */
    public static byte[] cutOut(byte[] bytes, int off, int length){
        byte[] bytess = new byte[length];
        System.arraycopy(bytes, off, bytess, 0, length);
        return bytess;
    }

    /**
     * 将字节转换为二进制字符串
     * @param bytes 字节数组
     * @return 二进制字符串
     */
    public static String byteToBit(byte... bytes){
        StringBuffer sb = new StringBuffer();
        int z, len;
        String str;
        for(int w = 0; w < bytes.length ; w++){
            z = bytes[w];
            z |= 256;
            str = Integer.toBinaryString(z);
            len = str.length();
            sb.append(str.substring(len-8, len));
        }
        return sb.toString();
    }

    /**
     *
     */
    public static byte intToByte(int num){
        return intToBytes(num)[0];
    }


    /**
     * 字节数组转换成16进制字符串
     * @param raw
     * @return
     */
    public static String getHex(byte [] raw ) {
        String HEXES = "0123456789ABCDEF";
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    /**
     * 将一个short转换成字节数组
     * @param sh short
     * @return 字节数组
     */
    public static byte[] valueOf(short sh){
        byte[] shortBuf = new byte[2];
        for(int i=0;i<2;i++) {
            int offset = (shortBuf.length - 1 -i)*8;
            shortBuf[i] = (byte)((sh>>>offset)&0xff);
        }
        return shortBuf;
    }

    /**
     * 将一个int转换成字节数组
     * @param in int
     * @return 字节数组
     */
    public static byte[] valueOf(int in){
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((in >>> offset) & 0xFF);
        }
        return b;
    }


    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes ( byte[] src, int begin, int count){
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;
    }


    /**
     * short型转换成byte型
     * @param data
     * @return
     */
    public static byte[] shortToBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    /**
     * char型转换成byte型
     * @param data
     * @return
     */
    public static byte[] charToBytes(char data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    /**
     * int型转换成byte型
     * @param data
     * @return
     */
    public static byte[] intToBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    /**
     * long型转换成byte型
     * @param data
     * @return
     */
    public static byte[] longToBytes(long data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    /**
     * float型转换成byte型
     * @param data
     * @return
     */
    public static byte[] floatToBytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return intToBytes(intBits);
    }

    /**
     * double型转换成byte型
     * @param data
     * @return
     */
    public static byte[] doubleToBytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return longToBytes(intBits);
    }

    /**
     * String型转换成byte型
     * @param data
     * @param charsetName
     * @return
     */
    public static byte[] stringToBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    /**
     * String型转换成byte型
     * @param data
     * @return
     */
    public static byte[] stringToBytes(String data) {
        return stringToBytes(data, "GBK");
    }

    /**
     * byte型转换成short型
     * @param bytes
     * @return
     */
    public static short bytesToShort(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    /**
     * byte型转换成char型
     * @param bytes
     * @return
     */
    public static char bytesToChar(byte[] bytes) {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    /**
     * byte型转换成int型
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }

    /**
     * byte型转换成long型
     * @param bytes
     * @return
     */
    public static long bytesToLong(byte[] bytes) {
        return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8))
                | (0xff0000L & ((long) bytes[2] << 16))
                | (0xff000000L & ((long) bytes[3] << 24))
                | (0xff00000000L & ((long) bytes[4] << 32))
                | (0xff0000000000L & ((long) bytes[5] << 40))
                | (0xff000000000000L & ((long) bytes[6] << 48))
                | (0xff00000000000000L & ((long) bytes[7] << 56));
    }

    /**
     * byte型转换成float型
     * @param bytes
     * @return
     */
    public static float bytesToFloat(byte[] bytes) {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }

    /**
     * byte型转换成double型
     * @param bytes
     * @return
     */
    public static double bytesToDouble(byte[] bytes) {
        long l = bytesToLong(bytes);
        return Double.longBitsToDouble(l);
    }

    /**
     * byte型转换成String型
     * @param bytes
     * @param charsetName
     * @return
     */
    public static String bytesToString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    /**
     *  byte型转换成String型
     * @param bytes
     * @return
     */
    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, "GBK");
    }

    /**
     * int转为16进制byte
     * @param num
     * @return
     */
    public static byte intToHexByte(int num) {
        return (byte)(intToHex(num));
    }

    /**
     * 十进制转16进制
     * @param num
     * @return
     */
    public static int intToHex(int num) {
//        LoggerUtils.info(String.valueOf(num));
        return Integer.parseInt(String.valueOf(num),16);
    }

}