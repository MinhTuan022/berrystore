package com.example.berryshoes.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NhanVienResponse {
    private Integer id;
    private String maNhanVien;
    private String anh;
    private String hoVaTen;
    private Boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private String taiKhoan;
    private Integer vaiTro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayTao;

    private String nguoiTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lanCapNhatCuoi;

    private String nguoiCapNhat;
    private Integer trangThai;
}
