package com.sp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMP_ROLE_TAB")
public class EmployeeRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMP_ROLE_ID")
	private int empRoleId;
	@Column(name = "EMP_ROLE_NAME")
	private String empRoleName;
	@Column(name = "EMP_ROLE_CODE")
	private String empRoleCode;
	@Column(name = "EMP_ROLE_ENABLE")
	private boolean empRoleEnable = true;

	public EmployeeRole() {}

	public EmployeeRole(String empRoleName) {
		this.setEmpRoleName(empRoleName);
	}

	public int getEmpRoleId() {
		return empRoleId;
	}

	public void setEmpRoleId(int empRoleId) {
		this.empRoleId = empRoleId;
	}

	public String getEmpRoleName() {
		return empRoleName;
	}

	public void setEmpRoleName(String empRoleName) {
		this.empRoleName = empRoleName;
		this.empRoleCode = "ROLE_" + this.empRoleName.toUpperCase().replaceAll("\\s", "_");
	}

	public String getEmpRoleCode() {
		return this.empRoleCode;
	}

	public boolean isEmpRoleEnable() {
		return empRoleEnable;
	}

	public void setEmpRoleEnable(boolean empRoleEnable) {
		this.empRoleEnable = empRoleEnable;
	}

}
