package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    @Insert("insert into emplloyee (name, username, password, phone, sex, id_number, create_time, uodate_time, update_user, status))"+
  "values" + "(#{name}, #{username})")
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}
