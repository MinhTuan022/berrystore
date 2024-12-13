package com.example.berryshoes.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PhieuGiamGiaRequest {
    private String maCode;
    private String tenPhieu;
    private Double giaTriGiamToiDa;
    private Integer giaTriGiam;
    private Double donToiThieu;
    private Short soLuong;
    private Boolean loaiPhieu;
    private Boolean kieuPhieu;
    private Timestamp ngayBatDau;
    private Timestamp ngayKetThuc;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
