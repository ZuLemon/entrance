package org.lemon.entrance.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.lemon.entrance.model.EntranceControllerModel;
import org.lemon.entrance.model.EntranceRBACModel;
import org.lemon.entrance.model.ReturnModel;
import org.lemon.entrance.service.DoorControlService;
import org.lemon.entrance.utils.DoorControlResponse;
import org.lemon.entrance.utils.JsonXMLUtils;
import org.lemon.entrance.utils.LoggerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Map;

/**
 * @Author : Lemon
 * @Desc :
 * @Date : 2019/11/1 21:44
 **/
@Controller
@RequestMapping("/controller")
public class EntranceControllerController {

    @Resource(name = "doorControlService")
    private DoorControlService service;
    private ReturnModel returnModel = null;

    /***
     * 打开指定门
     * @param controller json
     * {
     * 	"ip":"192.168.1.13",
     * 	"devsn":"122217403",
     * 	"port":60000
     * }
     * @param door
     * @return
     */

    @RequestMapping("openDoor/{door}")
    @ResponseBody
    public ReturnModel openDoor(@RequestBody EntranceControllerModel controller, @PathVariable("door") int door) {
        returnModel = new ReturnModel();
        DoorControlResponse response =  service.openDoor(controller,door);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        }else {
            returnModel.setSuccess(false);
            returnModel.setException("开门失败:"+response.getMsg());
        }
//        LoggerUtils.info(String.valueOf(door));
        return returnModel;
    }

    /***
     * 设置闭锁延迟
     * @param controller
     * @param door 门号
     * @param delay 延迟时间
     * @param online 0x03在线 0x02 常闭 01常开
     * @return
     */
    @RequestMapping("setDoorOpenDelay/{door}")
    @ResponseBody
    public ReturnModel setDoorOpenDelay(@RequestBody EntranceControllerModel controller, @PathVariable("door") int door, int delay, int online) {
        returnModel = new ReturnModel();
        DoorControlResponse response = service.setDoorOpenDelay(controller, door, delay, online);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        } else {
            returnModel.setSuccess(false);
            returnModel.setException("设置失败:"+response.getMsg());
        }
        return returnModel;
    }

    /***
     * 获取控制器时间
     * @param controller
     * @return
     */
    @RequestMapping("timeRead")
    @ResponseBody
    public ReturnModel timeRead(@RequestBody EntranceControllerModel controller) {
        returnModel = new ReturnModel();
        DoorControlResponse response = service.timeRead(controller);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        } else {
            returnModel.setSuccess(false);
            returnModel.setException("读取时间失败:"+response.getMsg());
        }
        return returnModel;
    }

    /***
     * 设置控制器时间
     * @param controller
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    @RequestMapping("timeCheck")
    @ResponseBody
    public ReturnModel timeCheck(@RequestBody EntranceControllerModel controller, String date) {
        returnModel = new ReturnModel();
        DoorControlResponse response = null;
        try {
            response = service.timeCheck(controller, date);
            if(null!=response && response.getErrCode()==0){
                returnModel.setObject(response);
            } else {
                returnModel.setSuccess(false);
                returnModel.setException("设置时间失败:"+response.getMsg());
            }
        } catch (ParseException e) {
            returnModel.setSuccess(false);
            returnModel.setException("时间转换失败");
        }

        return returnModel;
    }

    /***
     * 添加更新权限
     * @param models
     *   {
     *    	"controller": {
     *      	"ip":"192.168.1.13",
     *       	"devsn":"122217403",
     *       	"port":60000
     *    	},
     *    	"rbac":{
     *    		"devsn":"122217403",
     *    		"cid":"22323636",
     *    		"tmstart":"2019-11-06 10:00:00",
     *    		"tmend":"2019-12-01 10:00:00",
     *    		"door1":1
     *    	}
     *    }
     * @return
     * @throws Exception
     */
    @RequestMapping("rbacInsertOrUpdate")
    @ResponseBody
    public ReturnModel rbacInsertOrUpdate(@RequestBody Map<String,Object> models) throws Exception {
        LoggerUtils.info(models.toString());
        returnModel = new ReturnModel();
        EntranceControllerModel controllerModel = JsonXMLUtils.mapToObj((Map<String,Object>) models.get("controller"),EntranceControllerModel.class);
        EntranceRBACModel rbacModel=JsonXMLUtils.mapToObj((Map<String,Object>) models.get("rbac"),EntranceRBACModel.class);
        DoorControlResponse response = null;
        response = service.rbacInsertOrUpdate(controllerModel, rbacModel);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        } else {
            returnModel.setSuccess(false);
            returnModel.setException("添加权限失败:"+response.getMsg());
        }
        return returnModel;
    }

    /***
     * 清除卡号权限
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping("rbacRemove")
    @ResponseBody
    public ReturnModel rbacRemove(@RequestBody Map<String,Object> models) throws Exception {
        returnModel = new ReturnModel();
        EntranceControllerModel controllerModel = JsonXMLUtils.mapToObj((Map<String,Object>) models.get("controller"),EntranceControllerModel.class);
        EntranceRBACModel rbacModel=JsonXMLUtils.mapToObj((Map<String,Object>) models.get("rbac"),EntranceRBACModel.class);
        DoorControlResponse response = null;
        response = service.rbacRemove(controllerModel, rbacModel);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        } else {
            returnModel.setSuccess(false);
            returnModel.setException("清除权限失败:"+response.getMsg());
        }
        return returnModel;
    }

    @RequestMapping("rbacClear")
    @ResponseBody
    public ReturnModel rbacClear(@RequestBody EntranceControllerModel controller) throws Exception {
        returnModel = new ReturnModel();
        DoorControlResponse response = null;
        response = service.rbacClear(controller);
        if(null!=response && response.getErrCode()==0){
            returnModel.setObject(response);
        } else {
            returnModel.setSuccess(false);
            returnModel.setException("清除权限失败:"+response.getMsg());
        }
        return returnModel;
    }

}
