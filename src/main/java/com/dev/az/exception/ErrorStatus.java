package com.dev.az.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorStatus {

    UNDEFINED_ERROR(0, "에러가 발생했습니다"),

    INPUT_WRONG_EMAIL_FORMAT(1, "잘못된 이메일 형식입니다."),

    INPUT_DUPLICATE_EMAIL(2, "이미 사용중인 이메일입니다.");

    private final int errorNumber;

    private final String errorMessage;

    ErrorStatus(int errorNumber, String errorMessage) {
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
    }
}
