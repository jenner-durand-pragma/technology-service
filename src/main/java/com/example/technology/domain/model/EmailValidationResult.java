package com.example.technology.domain.model;

public record EmailValidationResult(String deliverability, String quality_score) { }

