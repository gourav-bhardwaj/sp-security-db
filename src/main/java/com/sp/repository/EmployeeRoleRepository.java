package com.sp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sp.dto.EmployeeRole;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {
	public List<EmployeeRole> findByEmpRoleCode(String empRoleCode);
	public boolean existsByEmpRoleCode(String empRoleCode);
}
