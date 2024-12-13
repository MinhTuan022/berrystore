package com.example.berryshoes.enums;

public enum JwtEnum {
    USER_ID("userId"),
    EMAIL("email"),
    ROLE("role"), // Vai trò nhân viên hoặc khách hàng
    AUTHORITIES_SYSTEM("authoritiesSystem");

    private final String val;

    JwtEnum(String value) {
        this.val = value;
    }

    public String val() {
        return val;
    }
}
