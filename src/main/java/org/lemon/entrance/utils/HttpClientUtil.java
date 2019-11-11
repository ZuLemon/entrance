package org.lemon.entrance.utils;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @Author : Lemon
 * @Desc : HTTP请求
 * @Date : 2016/08/11 11:19
 **/
public class HttpClientUtil {


    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static String sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity,String.class);

        return response.getBody();
    }

    /**
     * 向目的URL发送get请求
     * @param url       目的url
     * @param params    发送的参数
     * @param headers   发送的http头，可在外部设置好参数后传入
     * @return  String
     */
    public static String sendGetRequest(String url, MultiValueMap<String, String> params,HttpHeaders headers){
        RestTemplate client = new RestTemplate();

        HttpMethod method = HttpMethod.GET;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }

    /***
     * 向目的URL发送post json请求
     * @param url
     * @param json
     * @return
     */
    public static String sendJSONRequest(String url, String json){
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        LoggerUtils.info(url);
        //传递JSON参数
        HttpEntity<String> jsonEntity = new HttpEntity<>(json, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, jsonEntity,String.class);
        return response.getBody();
    }

}