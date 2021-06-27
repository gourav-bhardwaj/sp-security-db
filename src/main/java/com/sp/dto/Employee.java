package com.sp.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMP_TAB")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMP_ID")
	private long empId;
	@Column(name = "EMP_NAME", nullable = false)
	private String empName;
	@Column(name = "EMP_EMAIL_ID", nullable = false, updatable = false)
	private String empEmail;
	@Column(name = "EMP_PASSWORD", nullable = false)
	private String empPassword;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "EMP_ROLE_ASSOC_TAB", 
	joinColumns = {@JoinColumn(name="EMP_ID")}, 
	inverseJoinColumns = {@JoinColumn(name="EMP_ROLE_ID")})
	private List<EmployeeRole> empRoles;
	@Column(name = "EMP_DOJ", nullable = false)
	private LocalDateTime empDoj = LocalDateTime.now();
	@Column(name = "EMP_ACC_EXPIRE", nullable = false)
	private LocalDateTime empAccExpire = empDoj.plusMonths(3);
	@Column(name = "ENABLE", nullable = false, updatable = true)
	private boolean empEnable = true;
}
