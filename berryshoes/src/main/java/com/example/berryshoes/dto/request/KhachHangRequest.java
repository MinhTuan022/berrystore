package com.example.berryshoes.dto.request;

import lombok.Data;

import java.sql.Date;
@Data
public class KhachHangRequest {
    private String maKhachHang;
    private String anh;
    private String hoVaTen;
    private Date ngaySinh;
    private Boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
