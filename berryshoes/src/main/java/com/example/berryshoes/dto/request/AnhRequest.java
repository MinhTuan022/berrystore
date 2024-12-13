package com.example.berryshoes.dto.request;

import lombok.Data;

@Data
public class AnhRequest {
    private Integer idSanPhamChiTiet; // ID của sản phẩm chi tiết
    private String tenAnh;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Boolean trangThai; // Dùng Boolean để khớp với kiểu dữ liệu trong entity
}
