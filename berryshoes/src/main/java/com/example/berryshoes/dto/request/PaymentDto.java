package com.example.berryshoes.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentDto {

    List<SanPhamChiTietPayment> sanPhamChiTietPayment = new ArrayList<>();

    private String maGiamGia;

    private Integer toDistrictId;

    private String toWardCode;

    // trang quay trở lại khi thanh toán thành công (trang phía frontend)
    private String returnUrl;
}
