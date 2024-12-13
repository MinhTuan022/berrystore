package com.example.berryshoes.dto.response;

import com.example.berryshoes.entity.KhachHang;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DiaChiResponse {
    private Integer id;
    private KhachHang khachHang; // Để lấy thông tin khách hàng
    private String tenDuong;
    private String xaPhuong;
    private String quanHuyen;
    private String tinhThanhPho;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private Integer ProvinceId;
    private Integer DistrictId;
    private String WardCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayTao;

    private String nguoiTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lanCapNhatCuoi;

    private String nguoiCapNhat;
    private Integer trangThai;
}
