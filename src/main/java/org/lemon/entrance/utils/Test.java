package org.lemon.entrance.utils;


import java.text.ParseException;

public class Test {
    /**
     * 卡号 22323636
     * @param args
     */
    public static void main(String args[]) throws ParseException {
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        EntranceController doorController=new EntranceController();
//        doorController.devsn="122217403";
//        doorController.ip="192.168.1.10";
//        doorController.port=60000;
//        DoorControlService controlService=new DoorControlService();
//        DoorControlResponse response =  controlService.queryRecordIndex(doorController,0xffffffff);
////        DoorControlResponse response =  controlService.rbacCount(doorController);
//        if(response.success) {
//            LoggerUtils.debug(Test.class,"msg:"+response.getMsg());
//        }else {
//            LoggerUtils.error(Test.class, response.getMsg());
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
        System.out.println( ByteUtils.intToHex(30)==(byte)(0x30) );
//        controlService.setDoorOpenDelay(doorController,1,0x05,0x03);
//        controlService.openDoor(doorController,1);
//        LoggerUtils.error(Test.class,"开门",null);
//        for (int t = 0; t <response.getRelayStatus().length ; t++) {
//            LoggerUtils.debug(Test.class, String.valueOf(response.getRelayStatus()[t]));
//        }
//        System.out.println(intToByteArray(30)[3]);
    }


}
