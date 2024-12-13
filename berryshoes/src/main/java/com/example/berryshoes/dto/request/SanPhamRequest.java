package com.example.berryshoes.dto.request;

import lombok.Data;

@Data
public class SanPhamRequest {
    private String maSanPham;
    private String tenSanPham;
    private Integer idThuongHieu;
    private Integer idChatLieu;
    private Integer idDeGiay;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer trangThai;
    private String anh;
    private Integer giaBan;
}
