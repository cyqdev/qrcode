package com.hengan.qrcode.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hengan.qrcode.entity.WXToken;
import org.apache.commons.lang.StringUtils;

public class WXApiUtil {

    public static WXToken token =  new WXToken();
    public static WXToken token1 =  new WXToken();

    /**
     * 获取token
     * @return
     */
    public static WXToken getAccessToken(){
        String corpid = "wxbaaa22057237762a";
        String corpsecret = "qmGVnGCN1WMJnLRaikXrezZL3zqpSp5xjp57i7GAskThpbw8b0gvYnO8Y7K8JfnK";
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#id&corpsecret=#secrect";
        url = url.replace("#id", corpid);
        url = url.replace("#secrect", corpsecret);
        String accessToken = (String) JSON.parseObject(GetWeixinHttpRequestByte.get(url)).get("access_token");
        token.setAccessToken(accessToken);
        token.setEXpire(false);
        System.out.println(token);
        return token;
    }
    /**
     * 获取token1
     * @return
     */
    public static WXToken getAccessToken1(){
        String corpid = "wxbaaa22057237762a";
        String corpsecret = "4uRR8245ko5cJKs93gg6h0eCha3DSrWxRFunXT-EXMI";
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#id&corpsecret=#secrect";
        url = url.replace("#id", corpid);
        url = url.replace("#secrect", corpsecret);
        String accessToken = (String) JSON.parseObject(GetWeixinHttpRequestByte.get(url)).get("access_token");
        token1.setAccessToken(accessToken);
        token1.setEXpire(false);
        System.out.println(token);
        return token;
    }



    /**
     * 用户信息
     * @param code
     * @return
     */
    public static JSONObject getUserInfo(String code){
        if(StringUtils.isEmpty(token1.getAccessToken()) || token1.isEXpire()){
            getAccessToken1();
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=#access_token&code=#code";
        url = url.replace("#code",code);
        url = url.replace("#access_token", token1.getAccessToken());
        JSONObject obj = JSON.parseObject(GetWeixinHttpRequestByte.get(url));
        return obj;
    }


    public static JSONObject getUserDetail(String userId){
        if(StringUtils.isEmpty(token1.getAccessToken()) || token1.isEXpire()){
            getAccessToken1();
        }
        String detailUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=#access_token&userid=#userid";
        detailUrl = detailUrl.replace("#access_token", token1.getAccessToken());
        detailUrl = detailUrl.replace("#userid",userId);
        String detailResult = GetWeixinHttpRequestByte.get(detailUrl);
        JSONObject detailObj = JSON.parseObject(detailResult);
        return detailObj;
    }

    /**
     * 根据部门遍历人员
     * @param departmentID
     * @return
     */
    public static JSONArray getUserDetailList(String departmentID){
        if(StringUtils.isEmpty(token.getAccessToken()) || token.isEXpire()){
            getAccessToken();
        }
        String  Employee_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
        Employee_LIST = Employee_LIST.replace("ACCESS_TOKEN",token.getAccessToken());
        Employee_LIST = Employee_LIST.replace("DEPARTMENT_ID",departmentID);
        Employee_LIST = Employee_LIST.replace("FETCH_CHILD","1");
        String Result = GetWeixinHttpRequestByte.get(Employee_LIST);
        JSONObject json = JSON.parseObject(Result);
        JSONArray arr= json.getJSONArray("userlist");
        return  arr;
    }

    public static String getDepartmentList(){
        if(StringUtils.isEmpty(token.getAccessToken()) || token.isEXpire()){
            getAccessToken();
        }
        String department_url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
        department_url = department_url.replace("ACCESS_TOKEN",token.getAccessToken());
        String Result = GetWeixinHttpRequestByte.get(department_url);
        return Result;
    }
    public static String getTag(String tagid){
        if(StringUtils.isEmpty(token.getAccessToken()) || token.isEXpire()){
            getAccessToken();
        }
        String department_url = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token=#ACCESS_TOKEN&tagid=#TAGID";
        department_url = department_url.replace("#ACCESS_TOKEN",token.getAccessToken());
        department_url = department_url.replace("#TAGID",tagid);
        String Result = GetWeixinHttpRequestByte.get(department_url);
        return Result;
    }
}
