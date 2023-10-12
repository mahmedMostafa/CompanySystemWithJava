package com.salama.company.Multinational.Company.entities.base;

public class BaseResponse<T> {

    private final String status = "success";
    private T data;

     public BaseResponse(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> BaseResponse<T> of(T data) {
        return new BaseResponse<>(data);
    }
}
