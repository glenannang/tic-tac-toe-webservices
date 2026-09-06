package com.svi.tictactoewebservice.dto.response;

import java.util.List;

public class RoomListResponse {

    private List<RoomResponse> list;
    private String msg;

    public RoomListResponse(List<RoomResponse> list, String msg) {
        this.list = list;
        this.msg = msg;
    }

    public List<RoomResponse> getList() {
        return list;
    }

    public void setList(List<RoomResponse> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}