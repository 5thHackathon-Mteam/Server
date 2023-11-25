package com.example.hackathon.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    AUTHENTICATION_REQUIRED(401, "C001", "인증이 필요합니다."),
    ACCESS_DENIED(403, "C002", "권한이 없는 사용자입니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러입니다."),
    UPLOAD_S3_ERROR(500, "C004", "서버에서 S3 사진 업로드 에러입니다."),
    CONNECT_S3_ERROR(500, "C004", "서버에서 S3 연결 에러입니다."),
    INVALID_REQUEST_ERROR(400, "C001", "잘못된 요청입니다.");


    private final int status;
    private final String code;
    private final String message;

}
