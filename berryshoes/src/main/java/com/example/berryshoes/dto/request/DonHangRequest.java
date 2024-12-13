package com.example.berryshoes.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DonHangRequest {

    List<SanPhamChiTietPayment> sanPhamChiTietPayment = new ArrayList<>();

    private String maGiamGia;

    /*
    * lấy tất cả chuỗi sau dấu ? từ url
    * http://localhost:3000/thanhcong?vnp_Amount=700000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14663286&vnp_CardType=ATM&vnp_OrderInfo=1731254406842&vnp_PayDate=20241110230105&vnp_ResponseCode=00&vnp_TmnCode=B77INC60&vnp_TransactionNo=14663286&vnp_TransactionStatus=00&vnp_TxnRef=65770654&vnp_SecureHash=824ce6c8011896bae97a3a79db8c60f448cb4708c68536466e37002ec108e5997f90c1788d1f705ca75d9eddf86d32c89580788b548bd4160f6b6adf3dbbb76f
    * */
    private String urlVnpay;

    private String vnpOrderInfo;

    private Integer toDistrictId;

    private String toWardCode;

    private String diaChiNhan;

    private String ghiChu;
}
