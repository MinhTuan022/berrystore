package com.example.berryshoes.dto.request;

import lombok.Data;

import java.util.Date;
@Data
public class GioHangRequest {
    private int id;
    private int idKhachHang;
    private int idSanPhamChiTiet;
    private short soLuong;
    private Date ngayTao;
    private boolean trangThai;
}
