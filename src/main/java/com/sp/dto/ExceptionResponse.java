package com.sp.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
	private int status;
	private String message;
	private String path;
	private LocalDateTime timestamp;
}
