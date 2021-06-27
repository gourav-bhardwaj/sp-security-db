package com.sp.service;

import java.util.List;
import com.sp.dto.Employee;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee);
	public Employee getEmployee(long empId);
	public Employee getEmployeeByEmail(String empEmail);
	public List<Employee> getEmployees();
	public Employee deleteEmployee(long empId);
	public Employee updateEmployee(Employee employee);
}
