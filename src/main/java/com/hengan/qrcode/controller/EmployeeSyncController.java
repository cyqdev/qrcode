package com.hengan.qrcode.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hengan.qrcode.service.EmployeeService;
import com.hengan.qrcode.utils.DateTimeUtils;
import com.hengan.qrcode.entity.Employee;
import com.hengan.qrcode.utils.WXApiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sync")
public class EmployeeSyncController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("t1")
    public String getC1() {
        JSONArray userDetailList = WXApiUtil.getUserDetailList("2849");
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject = userDetailList.getJSONObject(i);
            Employee employee = new Employee();
            String userid = jsonObject.get("userid").toString();
            String name = jsonObject.get("name").toString();
            String mobile = jsonObject.get("mobile").toString();
            String gender = jsonObject.get("gender")+"";
            employee.setWokecode(userid);
            employee.setName(name);
            employee.setMobile(mobile);
            employee.setSex(gender);
            list.add(employee);
        }
        employeeService.insertBatch(list);
        return "ssss";
    }

    @RequestMapping("t2")
    public String getC2() {
        List<Employee> list = employeeService.selectDel();
        System.out.println(list);
        return "222";
    }
    @RequestMapping("t3")
    public String getC3() {
        Employee employee = employeeService.selectByCode("17105223");
        return employee.toString();
    }
    @RequestMapping("t4")
    public String getC4() {
        Employee employee = new Employee();
        employee.setWokecode("17105223");
        employee.setName("QQ");
        employee.setSex("1");
        employee.setStatus("00000");
        employee.setMessage("成灌灌灌灌灌灌");
        employee.setMobile("1111111111");
        employeeService.insertAdd(employee);
        return employee.toString();
    }
    @RequestMapping("t5")
    public String getC5() {
        Employee employee = new Employee();
        employee.setWokecode("17105223");
        employee.setName("Q1111");
        employee.setSex("2");
        employee.setStatus("1111");
        employee.setMessage("哒哒哒");
        employee.setMobile("22222222");
        employeeService.updateEdit(employee);
        return employee.toString();
    }
    @RequestMapping("t6")
    public String getC6() {
        String s = "17105223";
        employeeService.removeDel(s);
        return "sss";
    }
}
