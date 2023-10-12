package com.salama.company.Multinational.Company;

import com.salama.company.Multinational.Company.annotations.IgnoreResponseBinding;
import com.salama.company.Multinational.Company.entities.base.BaseResponse;
import com.salama.company.Multinational.Company.errors.ApiError;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;


/**
 * This Advice modifies the responses to have the same base response for all requests
 */
@ControllerAdvice
public class CustomResponseAdvise implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object o,
            MethodParameter methodParameter,
            @NonNull MediaType mediaType,
            @NonNull Class aClass,
            @NonNull ServerHttpRequest serverHttpRequest,
            @NonNull ServerHttpResponse serverHttpResponse
    ) {
        if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)) {
            if (!Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBinding.class)) {
                if (!(o instanceof ApiError) && !(o instanceof BaseResponse<?>)) {
                    return new BaseResponse<>(o);
                }
            }
        }
        return o;
    }
}
