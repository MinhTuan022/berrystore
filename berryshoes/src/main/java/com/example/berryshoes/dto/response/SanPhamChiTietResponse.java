package com.example.berryshoes.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SanPhamChiTietResponse {
    private Integer id;
    private String maSanPhamChiTiet;
    private String qrCode;
    private Integer soLuong;
    private Double giaTien;
    private String moTa;
    private Timestamp ngayTao;
    private String nguoiTao;
    private Timestamp lanCapNhatCuoi;
    private String nguoiCapNhat;
    private Boolean trangThai;
}
