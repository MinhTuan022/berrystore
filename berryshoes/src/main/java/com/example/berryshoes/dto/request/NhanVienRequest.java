package com.example.berryshoes.dto.request;

import lombok.Data;

import java.sql.Date;

@Data
public class NhanVienRequest {
    private String maNhanVien;
    private String anh;
    private String hoVaTen;
    private Date ngaySinh;
    private Boolean gioiTinh;
    private String queQuan;
    private String cccd;
    private String soDienThoai;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private Integer vaiTro;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
