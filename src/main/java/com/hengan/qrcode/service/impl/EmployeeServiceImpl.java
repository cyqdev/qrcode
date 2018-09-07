package com.hengan.qrcode.service.impl;

import com.hengan.qrcode.mapper.EmployeeMapper;
import com.hengan.qrcode.entity.Employee;
import com.hengan.qrcode.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee selectByCode(String workCode) {
        return employeeMapper.selectByCode(workCode);
    }

    @Override
    public void insertBatch(List<Employee> list) {
        employeeMapper.insertBatch(list);
    }

    @Override
    public List<Employee> selectUpdate() {
        return employeeMapper.selectUpdate();
    }

    @Override
    public List<Employee> selectAdd() {
        return employeeMapper.selectAdd();
    }

    @Override
    public List<Employee> selectDel() {
        return employeeMapper.selectDel();
    }

    @Override
    public void insertAdd(Employee employee) {
        employeeMapper.insertAdd(employee);
    }

    @Override
    public void updateEdit(Employee employee) {
        employeeMapper.updateEdit(employee);
    }

    @Override
    public void removeDel(String wokecode) {
        employeeMapper.removeDel(wokecode);
    }

    @Override
    public void deleteALL() {
        employeeMapper.deleteALL();
    }
}
