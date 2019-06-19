package com.hengan.qrcode.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hengan.qrcode.entity.Employee;
import com.hengan.qrcode.service.EmployeeService;
import com.hengan.qrcode.utils.SyncVIPUtil;
import com.hengan.qrcode.utils.WXApiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SyncScheduled {

    @Autowired
    private EmployeeService employeeService;

    @Scheduled(cron = "15 16 16 * * ?") // 每天23:00:00执行一次
    public void syncStart() {
        JSONArray userDetailList = WXApiUtil.getUserDetailList("2849");
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < userDetailList.size(); i++) {
            Employee employee = new Employee();
            JSONObject jsonObject = userDetailList.getJSONObject(i);
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
        System.out.println("总共："+list.size());
        employeeService.deleteALL();
        employeeService.insertBatch(list);
        update();
        delete();
        add();
    }
    public void update(){
        List<Employee> list = employeeService.selectUpdate();
        int fail = 0;
        int success = 0;
        for (int i = 0; i <list.size() ; i++) {
            Employee employee = list.get(i);
            String data = SyncVIPUtil.update(employee);
            JSONObject jsonObject = JSON.parseObject(data);
            String msg = jsonObject.getString("message");
            String code = jsonObject.getString("code");
            employee.setStatus(code);
            employee.setMessage(msg);
            if(code.equals("00000")){
                employee.setMessage("成功");
                success++;
            }else{
                fail++;
            }
            employeeService.updateEdit(employee);
        }
        System.out.println("总更新量：【"+list.size()+"】,成功：【"+success+"】,失败:【"+fail+"】");
    }
    public void delete(){
        List<Employee> list = employeeService.selectDel();
        int fail = 0;
        int success = 0;
        for (int i = 0; i <list.size() ; i++) {
            Employee employee = list.get(i);
            String data = SyncVIPUtil.updateStatus(employee.getWokecode(),"2");
            JSONObject jsonObject = JSON.parseObject(data);
            String code = jsonObject.getString("code");
            if(code.equals("00000")){
                employeeService.removeDel(employee.getWokecode());
                success++;
            }else {
                fail++;
            }
        }
        System.out.println("总离职量：【"+list.size()+"】,成功：【"+success+"】,失败:【"+fail+"】");
    }
    public void add(){
        List<Employee> list = employeeService.selectAdd();
        int fail = 0;
        int success = 0;
        for (int i = 0; i <list.size() ; i++) {
            Employee employee = list.get(i);
            String data = SyncVIPUtil.create(employee);
            JSONObject jsonObject = JSON.parseObject(data);
            String msg = jsonObject.getString("message");
            String code = jsonObject.getString("code");
            employee.setStatus(code);
            employee.setMessage(msg);
            if(code.equals("00000")){
                employee.setMessage("成功");
                success++;
            }else {
                fail++;
            }
            employeeService.insertAdd(employee);
        }
        System.out.println("总新增量：【"+list.size()+"】,成功：【"+success+"】,失败:【"+fail+"】");
    }

//    public static void main(String[] args) {
//        new SyncScheduled().delete();
//    }
}
