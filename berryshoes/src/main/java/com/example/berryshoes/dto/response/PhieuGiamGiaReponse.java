package com.example.berryshoes.dto.response;


import lombok.Data;

import java.sql.Timestamp;
@Data
public class PhieuGiamGiaReponse {
    private Integer id;
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
    private Timestamp ngayTao;
    private Timestamp lanCapNhatCuoi;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
