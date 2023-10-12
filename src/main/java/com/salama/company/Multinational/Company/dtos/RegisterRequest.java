package com.salama.company.Multinational.Company.dtos;

public record RegisterRequest(
        String name,
        String email,
        String password,
        float salary,
        int age
) {
}
