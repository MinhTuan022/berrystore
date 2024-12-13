package com.example.berryshoes.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhuongThucThanhToanRequest {
    private Integer idHoaDon; // ID hóa đơn
    private String tenPhuongThuc; // Tên phương thức thanh toán
    private String moTa; // Mô tả
    private BigDecimal tongTien; // Tổng tiền
    private String maGiaoDichVnPay; // Mã giao dịch VnPay
    private String nguoiTao; // Người tạo
    private String nguoiCapNhat; // Người cập nhật
    private Integer trangThai; // Trạng thái
}
