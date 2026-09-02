package com.svi.tictactoewebservice.dto.response;

import java.util.List;

public class GameListResponse {

    private List<GameId> list;
    private String msg;

    public GameListResponse() {
    }

    public GameListResponse(List<GameId> list, String msg) {
        this.list = list;
        this.msg = msg;
    }

    public List<GameId> getList() {
        return list;
    }

    public void setList(List<GameId> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class GameId {

        private String id;

        public GameId() {
        }

        public GameId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
