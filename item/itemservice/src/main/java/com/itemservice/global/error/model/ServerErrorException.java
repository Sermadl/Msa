package com.itemservice.global.error.model;

public class ServerErrorException extends CustomException {
    public ServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
