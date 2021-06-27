package com.sp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sp.dto.Employee;
import com.sp.exception.ApplicationException;
import com.sp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee saveEmployee(Employee employee) {
		if (null == employee) {
			throw new IllegalArgumentException("employee should not be null");
		}
		return this.repository.save(employee);
	}

	@Override
	public Employee getEmployee(long empId) {
		return this.repository.findById(empId)
				.orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "employee not found with empId: %s", empId));
	}

	@Override
	public List<Employee> getEmployees() {
		return this.repository.findAll();
	}

	@Override
	public Employee deleteEmployee(long empId) {
		Employee employee = this.repository.getById(empId);
		this.repository.deleteById(empId);
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		if (employee.getEmpId() == 0) {
			throw new IllegalArgumentException("employee id should not be zero");
		}
		Employee empObj = this.repository.getById(employee.getEmpId());
		empObj.setEmpName(employee.getEmpName());
		empObj.setEmpPassword(employee.getEmpPassword());
		empObj.setEmpEnable(employee.isEmpEnable());
		empObj.setEmpAccExpire(employee.getEmpAccExpire());
		return this.repository.save(empObj);
	}

	@Override
	public Employee getEmployeeByEmail(String empEmail) {
		List<Employee> empList = this.repository.findByEmpEmail(empEmail);
		if (null == empList || empList.isEmpty()) {
			throw new ApplicationException(HttpStatus.NOT_FOUND, "User not found by username : %s", empEmail);
		}
		return empList.get(0);
	}

}
