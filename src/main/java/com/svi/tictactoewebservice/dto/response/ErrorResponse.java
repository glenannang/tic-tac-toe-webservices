package com.svi.tictactoewebservice.dto.response;

public class ErrorResponse {

    private String msg;

    public ErrorResponse() {
    }

    public ErrorResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}