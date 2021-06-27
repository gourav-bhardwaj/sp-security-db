package com.sp.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sp.dto.EmployeeRole;
import com.sp.dto.GenericResponse;
import com.sp.exception.ApplicationException;
import com.sp.service.EmployeeRoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserRoleController {
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@PostMapping("roles")
	public ResponseEntity<GenericResponse> saveEmployeeRole(@RequestBody EmployeeRole empRole) {
		if (null == empRole) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid empRole");
		}
		EmployeeRole role = this.employeeRoleService.saveEmployeeRole(empRole);
		log.info("New employee designation has been created successfully");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{roleCode}")
				.buildAndExpand(empRole.getEmpRoleCode()).toUri();
		return ResponseEntity.created(uri).body(new GenericResponse(HttpStatus.CREATED.value(),
				"New employee designation has been created successfully", null, role));
	}
	
	@GetMapping("roles/{roleCode}")
	public ResponseEntity<GenericResponse> getEmployeeRole(@PathVariable String roleCode) {
		if (null == roleCode || StringUtils.hasText(roleCode)) {
			log.error("invalid employee designation code : {}", roleCode);
			throw new ApplicationException(HttpStatus.NOT_FOUND, "invalid employee designation code : %s", roleCode);
		}
		EmployeeRole roleObj = this.employeeRoleService.getEmployeeRoleByCode(roleCode);
		return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), null, null, roleObj));
	}

}
