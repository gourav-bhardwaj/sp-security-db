package com.sp.service;

import java.util.List;
import com.sp.dto.EmployeeRole;

public interface EmployeeRoleService {
	public EmployeeRole saveEmployeeRole(EmployeeRole employeeRole);
	public EmployeeRole removeEmployeeRole(int empRoleId);
	public EmployeeRole getEmployeeRole(int empRoleId);
	public EmployeeRole getEmployeeRoleByCode(String empRole);
	public List<EmployeeRole> getEmployeeRoles();
	public boolean isEmployeeRoleExist(String empRoleName);
}
