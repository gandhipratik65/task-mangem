package com.banq.assign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String timestamp;
    private String message;
    private Map<String, String> errors;
}