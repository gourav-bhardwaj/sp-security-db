package com.sp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sp.constants.Roles;
import com.sp.dto.Employee;
import com.sp.dto.EmployeeRole;
import com.sp.dto.GenericResponse;
import com.sp.dto.UserAuthDTO;
import com.sp.mapper.EmployeeMapper;
import com.sp.service.EmployeeRoleService;
import com.sp.service.EmployeeService;

@RestController
public class UserAuthController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signUp")
	public ResponseEntity<GenericResponse> signUpProcess(@RequestBody UserAuthDTO userAuth) {
		Employee employee = new Employee();
		employee.setEmpName(userAuth.getName());
		employee.setEmpEmail(userAuth.getEmail());
		employee.setEmpPassword(this.passwordEncoder.encode(userAuth.getPassword()));
		EmployeeRole empRole = null;
		if (!employeeRoleService.isEmployeeRoleExist(userAuth.getRole()) && (userAuth.getRole().equalsIgnoreCase(Roles.ROLE_ADMIN) || userAuth.getRole().equalsIgnoreCase(Roles.ROLE_SUPER_ADMIN))) {
			empRole = this.employeeRoleService.saveEmployeeRole(new EmployeeRole(userAuth.getRole()));
		} else {
			empRole = employeeRoleService.getEmployeeRoleByCode(userAuth.getRole());
		}
		employee.setEmpRoles(Arrays.asList(empRole));
		EmployeeMapper empObj = new EmployeeMapper(this.employeeService.saveEmployee(employee));
		return ResponseEntity.ok(new GenericResponse(HttpStatus.CREATED.value(), 
				"Employee has been registered successfully", null, empObj));
	}
	
	@GetMapping("/logout")
	public void logoutProcess() {
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
	}
	
	@PreAuthorize("hasRole('ADMIN_00')")
	@GetMapping("/users")
	public ResponseEntity<GenericResponse> getAllUsers() {
		List<EmployeeMapper> employees = this.employeeService.getEmployees().stream().map(EmployeeMapper::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), null, employees, null));
	}

}
