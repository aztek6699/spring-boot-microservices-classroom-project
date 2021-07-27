package com.example.teacherservice.model;

import java.util.List;

public class GenericResponse {

    Boolean isSuccess;
    String msg;
    int respCode;
    List<?> respData;

    public GenericResponse(Boolean isSuccess, String msg, int respCode, List<?> respData) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.respCode = respCode;
        this.respData = respData;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public List<?> getRespData() {
        return respData;
    }

    public void setRespData(List<?> respData) {
        this.respData = respData;
    }
}
