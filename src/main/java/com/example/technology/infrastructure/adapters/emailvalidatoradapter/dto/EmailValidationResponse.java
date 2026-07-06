package com.example.technology.infrastructure.adapters.emailvalidatoradapter.dto;

public record EmailValidationResponse(String deliverability, String quality_score) {
}
