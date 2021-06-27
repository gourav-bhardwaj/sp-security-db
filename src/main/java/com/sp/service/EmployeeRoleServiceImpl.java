package com.sp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sp.dto.EmployeeRole;
import com.sp.exception.ApplicationException;
import com.sp.repository.EmployeeRoleRepository;

@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService {
	
	@Autowired
	private EmployeeRoleRepository repository;

	@Override
	public EmployeeRole saveEmployeeRole(EmployeeRole employeeRole) {
		if (null == employeeRole) {
			throw new IllegalArgumentException("employee role shouldn't be null");
		}
		return this.repository.save(employeeRole);
	}

	@Override
	public EmployeeRole removeEmployeeRole(int empRoleId) {
		if (empRoleId <= 0) {
			throw new IllegalArgumentException("Invalid empId");
		}
		EmployeeRole roleObj = getEmployeeRole(empRoleId);
		this.repository.deleteById(empRoleId);
		return roleObj;
	}

	@Override
	public EmployeeRole getEmployeeRole(int empRoleId) {
		if (empRoleId <= 0) {
			throw new IllegalArgumentException("Invalid empId");
		}
		return this.repository.findById(empRoleId)
				.orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Employee role does not found by empRoleId : {}", empRoleId));
	}

	@Override
	public List<EmployeeRole> getEmployeeRoles() {
		return this.repository.findAll();
	}

	@Override
	public EmployeeRole getEmployeeRoleByCode(String empRole) {
		if (null == empRole || StringUtils.hasText(empRole)) {
			throw new IllegalArgumentException("Invalid empRole");
		}
		List<EmployeeRole> roles = this.repository.findByEmpRoleCode("ROLE_" + empRole.toUpperCase().replaceAll("\\s", "_"));
		if (null == roles || roles.isEmpty()) {
			throw new ApplicationException(HttpStatus.NOT_FOUND, "Employee role does not found : %s", empRole);
		}
		return roles.get(0);
	}

	@Override
	public boolean isEmployeeRoleExist(String empRoleName) {
		return this.repository.existsByEmpRoleCode("ROLE_" + empRoleName.toUpperCase().replaceAll("\\s", "_"));
	}

}
