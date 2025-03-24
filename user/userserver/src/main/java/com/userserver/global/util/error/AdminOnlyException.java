package com.userserver.global.util.error;

import com.userserver.global.error.model.CustomException;

public class AdminOnlyException extends CustomException {
    public AdminOnlyException() {
        super();
    }
}
