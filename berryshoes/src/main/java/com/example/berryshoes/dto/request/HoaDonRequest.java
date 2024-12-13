package com.example.berryshoes.dto.request;

import com.example.berryshoes.entity.DiaChi;
import com.example.berryshoes.entity.PhieuGiamGia;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HoaDonRequest {

    private List<Integer> listIdGioHang = new ArrayList<>();

    private PhieuGiamGia phieuGiamGia;

    private DiaChi diaChi;

    private String ghiChu;

    private String vnpOrderInfo;

    private String vnpayUrl;
}
