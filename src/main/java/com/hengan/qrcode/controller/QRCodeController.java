package com.hengan.qrcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.hengan.qrcode.utils.DateTimeUtils;
import com.hengan.qrcode.utils.WXApiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class QRCodeController {

    @RequestMapping(value = "/qrCode",method = RequestMethod.GET)
    public String hello(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        JSONObject obj =WXApiUtil.getUserInfo(code);
        String workCode = obj.getString("UserId");
        String result = "";
        if(StringUtils.isNotBlank(workCode)) {
            JSONObject objDetial =WXApiUtil.getUserDetail(workCode);
            String mobile = objDetial.getString("mobile");
            result += "mp="+mobile+"&mid="+workCode+"&time="+DateTimeUtils.getFormatDate(new Date(), "yyyyMMddHHmmss");
        }
        model.addAttribute("result", result);
        return "qrCode";
    }
}
