package com.sp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sp.dto.Employee;
import com.sp.mapper.EmployeeMapper;
import com.sp.repository.EmployeeRepository;

@Service
public class EmployeeAuthService implements UserDetailsService {
	
	@Autowired
	private EmployeeRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Employee> empList = this.repository.findByEmpEmail(username);
		if (null == empList || empList.isEmpty()) {
			throw new UsernameNotFoundException(String.format("Username %s not found in DB", username));
		}
		return new EmployeeMapper(empList.get(0));
	}

}
