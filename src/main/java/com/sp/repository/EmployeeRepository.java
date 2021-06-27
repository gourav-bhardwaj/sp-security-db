package com.sp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sp.dto.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public List<Employee> findByEmpEmail(String empEmail);
}
