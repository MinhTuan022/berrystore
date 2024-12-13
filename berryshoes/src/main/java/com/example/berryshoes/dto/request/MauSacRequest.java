package com.example.berryshoes.dto.request;

import lombok.Data;

@Data
public class MauSacRequest {
    private String maMauSac;
    private String tenMauSac;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
