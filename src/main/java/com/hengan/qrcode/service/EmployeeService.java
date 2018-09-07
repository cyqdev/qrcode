package com.hengan.qrcode.service;

import com.hengan.qrcode.entity.Employee;

import java.util.List;

public interface EmployeeService {
        /**
         * 根据接口查询所用的用户
         */
        //public List<Employee> findAllEmployee();
    public  Employee selectByCode(String workCode);
    public void insertBatch(List<Employee> list);
    public List<Employee> selectUpdate();
    public List<Employee> selectAdd();
    public List<Employee> selectDel();
    public void insertAdd(Employee employee);
    public void updateEdit(Employee employee);
    public void removeDel(String wokecode);
    public void deleteALL();
}
