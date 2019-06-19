package com.hengan.qrcode.utils;

import com.alibaba.fastjson.JSON;
import com.hengan.qrcode.entity.Employee;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.Key;
import java.util.*;

/**
 * /////////////////////////////////////////////////////////////////
 * code is far away from bug with the buddha protecting.  --- by cyq
 * //                                                             //
 * //                          _ooOoo_                            //
 * //                         o8888888o                           //
 * //                         88" . "88                           //
 * //                         (| ^_^ |)                           //
 * //                         O\  =  /O                           //
 * //                      ____/`---'\____                        //
 * //                    .'  \\|     |//  `.                      //
 * //                   /  \\|||  :  |||//  \                     //
 * //                  /  _||||| -:- |||||-  \                    //
 * //                  |   | \\\  -  /// |   |                    //
 * //                  | \_|  ''\---/''  |   |                    //
 * //                  \  .-\__  `-`  ___/-. /                    //
 * //                ___`. .'  /--.--\  `. . ___                  //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".               //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |              //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /              //
 * //      ========`-.____`-.___\_____/___.-`____.-'========      //
 * //                           `=---='                           //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^     //
 * //         佛祖保佑       永无BUG     永不修改                 //
 * /////////////////////////////////////////////////////////////////
 *  @author cyq
 */

public class SyncVIPUtil {

    //测试环境
    public static String SERVE_TEST = "http://test.openapi.qcterp.com";
    //正式环境
    public static String SERVE = "http://openapi.qcterp.com";

    public static String KEY = "329DB51EFED14C2DBFF54E927F7578DC";
    public static String KEY_TEST = "B2D7D2D3438549E5B19DBE14E96F7B85";
    public static String COMPANYID = "900";
    public static String COMPANYID_TEST = "1";

    public static String SEARCH = "/member/getmember";
    public static String CREATE = "/member/createmember";
    public static String UPDATE = "/member/updatemember";
    public static String UPDATE_STATUS = "/member/updatememberstate";

    /**
     * 查询
     * @param memberNum
     * @return
     */
    public static String search(String memberNum){
        SortedMap<String,String> paramMap = new TreeMap<String,String>();
        paramMap.put("method","qct.erp.api.memberstate");
        paramMap.put("charset","utf-8");
        paramMap.put("companyid",COMPANYID);
        paramMap.put("version","1.0");
        paramMap.put("memberNum",memberNum);

        String key = KEY;
        String sign = generateSignature(paramMap,key);
        List<NameValuePair> formParams = getFormParam(paramMap, sign);
        String data = "";
        try {
            HttpEntity httpEntity = HttpClientPool.PostForm(SERVE+SEARCH,null,formParams);
            data = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 新建会员
     * @param employee
     * sex  -1: 未知、 0:女、 1:男
     * @return
     */
    public static String create(Employee employee){
        String sex = "-1";
        if (employee.getSex().equals("0"))
            sex = "-1";
        else if (employee.getSex().equals("2"))
            sex = "0";
        else sex = employee.getSex();
        SortedMap<String,String> paramMap = new TreeMap<String,String>();
        paramMap.put("method","qct.erp.api.createmember");
        paramMap.put("charset","utf-8");
        paramMap.put("companyid",COMPANYID);
        paramMap.put("version","1.0");
        paramMap.put("Storeid","1");
        paramMap.put("MemberNo",employee.getWokecode());
        paramMap.put("MobilePhone",employee.getMobile());
        paramMap.put("RealName",employee.getName());
        paramMap.put("Sex",sex);
        String key = KEY;
        String sign = generateSignature(paramMap,key);
        List<NameValuePair> formParams = getFormParam(paramMap, sign);
        String data = "";
        try {
            HttpEntity httpEntity = HttpClientPool.PostForm(SERVE+CREATE,null,formParams);
            data = EntityUtils.toString(httpEntity);
//            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 更新会员信息
     * @param employee
     * @return sex  -1: 未知、 0:女、 1:男
     *
     */
    public static String update(Employee employee){
        String sex = "-1";
        if(employee.getSex().equals("0"))
            sex = "-1";
        else if(employee.getSex().equals("2"))
            sex = "0";
        else sex = employee.getSex();
        SortedMap<String,String> paramMap = new TreeMap<String,String>();
        paramMap.put("method","qct.erp.api.updatemember");
        paramMap.put("charset","utf-8");
        paramMap.put("companyid",COMPANYID);
        paramMap.put("version","1.0");
        paramMap.put("MemberNo",employee.getWokecode());
        paramMap.put("MobilePhone",employee.getMobile());
        paramMap.put("RealName",employee.getName());
        paramMap.put("Sex",sex);
        String key = KEY;
        String sign = generateSignature(paramMap,key);
        List<NameValuePair> formParams = getFormParam(paramMap, sign);
        String data = "";
        try {
            HttpEntity httpEntity = HttpClientPool.PostForm(SERVE+UPDATE,null,formParams);
            data = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     *
     * @param memberNo
     * @param state 0: 禁用、 1:可用、 2:无效
     * @return
     */
    public static String updateStatus(String memberNo,String state){
        SortedMap<String,String> paramMap = new TreeMap<String,String>();
        paramMap.put("method","qct.erp.api.updatememberstate");
        paramMap.put("charset","utf-8");
        paramMap.put("companyid",COMPANYID);
        paramMap.put("version","1.0");
        paramMap.put("Storeid","1");
        paramMap.put("MemberNo",memberNo);
        paramMap.put("State",state);
        String key = KEY;
        String sign = generateSignature(paramMap,key);
        List<NameValuePair> formParams = getFormParam(paramMap, sign);
        String data = "";
        try {
            HttpEntity httpEntity = HttpClientPool.PostForm(SERVE+UPDATE_STATUS,null,formParams);
            data = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static String generateSignature(SortedMap<String,String> parameters,String key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        //最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        sb.append("key=" + key);
        String sign = null;
        try {
            sign = EncryptUtil.md5Digest(sb.toString()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static List<NameValuePair> getFormParam(SortedMap<String,String> paramMap,String sign){
        List<NameValuePair> formParams = new ArrayList<>();
        Set es = paramMap.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k)) {
                formParams.add(new BasicNameValuePair(k,v.toString()));
            }
        }
        formParams.add(new BasicNameValuePair("sign",sign));
        return formParams;
    }
    public static void main(String[] args) {
//        search("038437");

        Employee employee = new Employee();
        employee.setSex("1");
        employee.setMobile("13799775943");
        employee.setWokecode("16001670");
        employee.setName("林端祥");
        employee.setStatus("00000");
        employee.setMessage("成功");
        create(employee);

//        updateStatus("17105223","1");
    }
}
