package com.salama.company.Multinational.Company.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        String path,
        String message,
        @JsonProperty("status_code")
        int statusCode,
        @JsonProperty("local_datetime")
        LocalDateTime localDateTime,
        String trace
) {
}
