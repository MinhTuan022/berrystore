package com.example.berryshoes.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SanPhamResponse {
    private Integer id;
    private String maSanPham;
    private String tenSanPham;
    private Timestamp ngayTao;
    private String nguoiTao;
    private Timestamp lanCapNhatCuoi;
    private String nguoiCapNhat;
    private Boolean trangThai;
}
