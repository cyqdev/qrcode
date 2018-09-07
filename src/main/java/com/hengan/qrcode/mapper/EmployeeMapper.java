package com.hengan.qrcode.mapper;

import com.hengan.qrcode.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    public Employee selectByCode(String id);
    public void insertBatch(List<Employee> list);
    public List<Employee> selectUpdate();
    public List<Employee> selectAdd();
    public List<Employee> selectDel();
    public void deleteALL();
    public void insertAdd(Employee employee);
    public void updateEdit(Employee employee);
    public void removeDel(String wokecode);
}
