package com.example.berryshoes.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PhuongThucThanhToanResponse {
    private Integer id;
    private Integer idHoaDon;
    private String tenPhuongThuc;
    private String moTa;
    private BigDecimal tongTien;
    private String maGiaoDichVnPay;
    private Timestamp ngayTao;
    private Timestamp lanCapNhatCuoi;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
}
