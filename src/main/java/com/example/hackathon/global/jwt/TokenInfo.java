package com.example.hackathon.global.jwt;

public record TokenInfo(String grantType, String accessToken, String refreshToken) {}