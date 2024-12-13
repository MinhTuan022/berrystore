package com.example.berryshoes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SanPhamChiTietRequest {
    private String maSanPhamChiTiet;
    private String qrCode;
    private Integer soLuong;
    private Double giaTien;
    private String moTa;
    private Integer idSanPham; // ID của sản phẩm
    private Integer idKichCo; // ID của kích cỡ
    private Integer idMauSac; // ID của màu sắc
    private Integer idDotGiamGia; // ID của đợt giảm giá (nếu có)
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
//    public SanPhamChiTietRequest(Integer id, String maSanPhamChiTiet, Integer soLuong, Double giaTien,
//                                 String ngayTao, String tenKichCo, String maMauSac, String tenMauSac,
//                                 Double giaTriGiam, String ngayBatDau,
//                                 String tenThuongHieu, String tenChatLieu, String tenDeGiay) {
//        // Gán giá trị cho các trường
//    }
}
