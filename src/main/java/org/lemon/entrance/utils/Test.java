package org.lemon.entrance.utils;


import org.lemon.entrance.model.EntranceControllerModel;
import org.lemon.entrance.service.DoorControlService;

import java.text.ParseException;

public class Test {
    /**
     * 卡号 22323636
     * @param args
     */
    public static void main(String args[]) throws ParseException {
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        EntranceControllerModel doorController=new EntranceControllerModel();
        doorController.setDevsn("122217403");
        doorController.setIp("192.168.1.10");
        doorController.setPort(60000);
        DoorControlService controlService=new DoorControlService();
//        DoorControlResponse response =  controlService.queryRecordIndex(doorController,0xffffffff);
////        DoorControlResponse response =  controlService.rbacCount(doorController);
//        if(response.success) {
//            LoggerUtils.debug("msg:"+response.getMsg());
//        }else {
//            LoggerUtils.error( response.getMsg());
//        }

//        EntranceRBACModel EntranceRBACModel=new EntranceRBACModel();
//        EntranceRBACModel.setCid("22323636");
//        EntranceRBACModel.setDevsn("122217403");
//        EntranceRBACModel.setDoor1(0x01);
//        EntranceRBACModel.setDoor2(0x01);
//        EntranceRBACModel.setDoor3(0x00);
//        EntranceRBACModel.setDoor4(0x00);
//        EntranceRBACModel.setTmstart(dateformat.parse("2019-10-01"));
//        EntranceRBACModel.setTmend(dateformat.parse("2019-11-01"));
//        DoorControlResponse response =controlService.rbacInsertOrUpdate(doorController,EntranceRBACModel);
//        if(response.success){
//            LoggerUtils.debug(Test.class,response.getMsg());
//        }else {
//            LoggerUtils.debug(Test.class,response.getMsg());
//        }
//        LoggerUtils.info("好的");
//        System.out.println( ByteUtils.intToHex(30)==(byte)(0x30) );
//          DoorControlResponse response =  controlService.setDoorOpenDelay(doorController,1,10,03);
//          LoggerUtils.info(response.msg);
//        controlService.openDoor(doorController,1);
//        LoggerUtils.error(Test.class,"开门",null);
//        for (int t = 0; t <response.getRelayStatus().length ; t++) {
//            LoggerUtils.debug(Test.class, String.valueOf(response.getRelayStatus()[t]));
//        }
//        System.out.println(intToByteArray(30)[3]);
        DoorControlResponse response=controlService.setListenServer(doorController,"192.168.1.5",61005);
        if(response.getErrCode()==0){
            LoggerUtils.debug(response.getMsg());
        }

    }


}
