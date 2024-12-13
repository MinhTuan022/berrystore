package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.BanTaiQuayRequest;
import com.example.berryshoes.dto.request.DonHangRequest;
import com.example.berryshoes.dto.request.PaymentDto;
import com.example.berryshoes.dto.request.SanPhamChiTietPayment;
import com.example.berryshoes.dto.response.ResponsePayment;
import com.example.berryshoes.entity.*;
import com.example.berryshoes.exception.MessageException;
import com.example.berryshoes.ghn.GhnClient;
import com.example.berryshoes.repository.*;
import com.example.berryshoes.utils.UserUltis;
import com.example.berryshoes.vnpay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ban-hang")
public class BanHangController {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Autowired
    private KhachHangPhieuGiamRepository khachHangPhieuGiamRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private UserUltis userUltis;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    @Autowired
    private GhnClient ghnClient;

    @Value("${khoiluong.default}")
    private Float khoiLuongDf;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;

    /*
     * api sẽ bổ sung tính phí ship sau rồi lấy tổng tiền + phí ship nữa
     * */
    @PostMapping("/create-url-vnpay")
    public ResponseEntity<?> createUrlVnpay(@RequestBody PaymentDto paymentDto, HttpServletRequest request){
        Double tongTien = 0D;
        float khoiluong = 0F;
        for(SanPhamChiTietPayment payment : paymentDto.getSanPhamChiTietPayment()){
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            if(spct.getSoLuong() < payment.getSoLuong()){
                throw new MessageException("Số lượng sản phẩm: "+spct.getSanPham().getTenSanPham()+" chỉ còn: "+spct.getSoLuong());
            }
            tongTien += spct.getGiaTien() * payment.getSoLuong();
            khoiluong += khoiLuongDf * payment.getSoLuong();
        }
        if(paymentDto.getMaGiamGia() != null){
            PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findPhieuGiamGiaByMaCode(paymentDto.getMaGiamGia());
            if(phieuGiamGia.getNgayKetThuc().before(new Timestamp(System.currentTimeMillis()))){
                throw new MessageException("Đợt giảm giá đã kết thúc");
            }
            KhachHang khachHang = userUltis.getLoggedInKhachHang(request);
            Optional<KhachHangPhieuGiam> khachHangPhieuGiam = khachHangPhieuGiamRepository.findByMaGGAndKhId(paymentDto.getMaGiamGia(), khachHang.getId());
            if(khachHangPhieuGiam.isEmpty()){
                throw new MessageException("Bạn không được áp dụng mã giảm giá này");
            }
            if(tongTien < phieuGiamGia.getDonToiThieu()){
                throw new MessageException("Bạn phải mua thêm: "+ (phieuGiamGia.getDonToiThieu() - tongTien)+" để được áp dụng mã giảm giá này");
            }
            // giảm theo %
            if(phieuGiamGia.getLoaiPhieu() == false){
                tongTien = tongTien - (tongTien * phieuGiamGia.getGiaTriGiam() / 100);
            }
            // giảm giá cứng cố định
            else{
                tongTien = tongTien - phieuGiamGia.getGiaTriGiam();
            }
        }
        int canNang = 0;
        if(khoiluong != (int) khoiluong ){
            canNang =  (int) khoiluong + 1;
        }
        else{
            canNang = (int) khoiluong;
        }
        Map<String, Object> response = ghnClient.calculateShippingFee(paymentDto.getToDistrictId(), paymentDto.getToWardCode(), canNang);
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        int totalShip = (int) data.get("total");

        System.out.println("Khoi luong: "+canNang);
        System.out.println("tien ship: "+totalShip);
        tongTien += totalShip;
        System.out.println("tong tien: "+tongTien.intValue());
        String vnpOrderInfo = String.valueOf(System.currentTimeMillis());
        String vnpayUrl = vnPayService.createOrder(tongTien.intValue(), vnpOrderInfo, paymentDto.getReturnUrl());
        ResponsePayment responsePayment = new ResponsePayment(vnpayUrl,vnpOrderInfo);
        return new ResponseEntity<>(responsePayment, HttpStatus.CREATED);
    }


    /*
     *  api tạo đơn hàng khi người dùng về trang thanh cong
     * */


    @PostMapping("/tao-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody DonHangRequest request, HttpServletRequest httpServletRequest){
        Double tongTien = 0D;
        float khoiluong = 0F;
        KhachHang khachHang = userUltis.getLoggedInKhachHang(httpServletRequest);
        for(SanPhamChiTietPayment payment : request.getSanPhamChiTietPayment()){
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            if(spct.getSoLuong() < payment.getSoLuong()){
                throw new MessageException("Số lượng sản phẩm: "+spct.getSanPham().getTenSanPham()+" chỉ còn: "+spct.getSoLuong());
            }
            tongTien += spct.getGiaTien() * payment.getSoLuong();
            khoiluong += khoiLuongDf * payment.getSoLuong();
        }
        PhieuGiamGia pgg = null;
        if(request.getMaGiamGia() != null){
            PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findPhieuGiamGiaByMaCode(request.getMaGiamGia());
            if(phieuGiamGia.getNgayKetThuc().before(new Timestamp(System.currentTimeMillis()))){
                throw new MessageException("Đợt giảm giá đã kết thúc");
            }
            Optional<KhachHangPhieuGiam> khachHangPhieuGiam = khachHangPhieuGiamRepository.findByMaGGAndKhId(request.getMaGiamGia(), khachHang.getId());
            if(khachHangPhieuGiam.isEmpty()){
                throw new MessageException("Bạn không được áp dụng mã giảm giá này");
            }
            if(tongTien < phieuGiamGia.getDonToiThieu()){
                throw new MessageException("Bạn phải mua thêm: "+ (phieuGiamGia.getDonToiThieu() - tongTien)+" để được áp dụng mã giảm giá này");
            }
            // giảm theo %
            if(phieuGiamGia.getLoaiPhieu() == false){
                tongTien = tongTien - (tongTien * phieuGiamGia.getGiaTriGiam() / 100);
            }
            // giảm giá cứng cố định
            else{
                tongTien = tongTien - phieuGiamGia.getGiaTriGiam();
            }
            pgg = phieuGiamGia;
        }
        if(phuongThucThanhToanRepository.findBymaGiaoDichVnPay(request.getVnpOrderInfo()) > 0){
            throw new MessageException("Mã thanh toán đã được sử dụng");
        }
        int paymentStatus = vnPayService.orderReturnByUrl(request.getUrlVnpay());
        System.out.println("status check: "+paymentStatus );
        if(paymentStatus != 1){
            throw new MessageException("Thanh toán thất bại");
        }
        int canNang = 0;
        if(khoiluong != (int) khoiluong ){
            canNang =  (int) khoiluong + 1;
        }
        else{
            canNang = (int) khoiluong;
        }
        Map<String, Object> response = ghnClient.calculateShippingFee(request.getToDistrictId(), request.getToWardCode(), canNang);
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        int totalShip = (int) data.get("total");

        System.out.println("Khoi luong: "+canNang);
        System.out.println("tien ship: "+totalShip);
        tongTien += totalShip;
        /*
         * tạo đơn hàng sau khi đã check thanh toán thành công
         * */
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setTongTien(new BigDecimal(tongTien));
        hoaDon.setLoaiHoaDon(true);
        hoaDon.setNgayTao(new Timestamp(System.currentTimeMillis()));
        hoaDon.setTrangThai(1);
        hoaDon.setPhieuGiamGia(pgg);
        hoaDon.setEmail(khachHang.getEmail());
        hoaDon.setDiaChi(request.getDiaChiNhan());
        hoaDon.setPhiVanChuyen(new BigDecimal(totalShip));
        hoaDon.setTenKhachHang(khachHang.getHoVaTen());
        hoaDon.setGhiChu(request.getGhiChu());
        hoaDon.setMaHoaDon("HD"+System.currentTimeMillis());
        hoaDonRepository.save(hoaDon);
        for(SanPhamChiTietPayment payment : request.getSanPhamChiTietPayment()){
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setTrangThai(1);
            hdct.setGhiChu(request.getGhiChu());
            hdct.setHoaDon(hoaDon);
            hdct.setGiaSanPham(new BigDecimal(spct.getGiaTien()));
            hdct.setSanPhamChiTiet(spct);
            hdct.setSoLuong(payment.getSoLuong().shortValue());
            hoaDonChiTietRepository.save(hdct);

            spct.setSoLuong(spct.getSoLuong() - payment.getSoLuong().shortValue());
            sanPhamRepository.save(spct.getSanPham());
        }

        // lưu lại lịch sử thanh toán tránh việc người dùng f5 lại web sẽ ghi lại đơn hàng 1 lần nữa
        PhuongThucThanhToan phuongThucThanhToan = new PhuongThucThanhToan();
        phuongThucThanhToan.setHoaDon(hoaDon);
        phuongThucThanhToan.setNgayTao(new Timestamp(System.currentTimeMillis()));
        phuongThucThanhToan.setTrangThai(1);
        phuongThucThanhToan.setMaGiaoDichVnPay(request.getVnpOrderInfo());
        phuongThucThanhToan.setTenPhuongThuc("VNPAY");
        phuongThucThanhToan.setTongTien(new BigDecimal(tongTien));
        phuongThucThanhToanRepository.save(phuongThucThanhToan);
        return new ResponseEntity<>(hoaDon,HttpStatus.CREATED);
    }

    @PostMapping("/ban-tai-quay")
    public ResponseEntity<?> banTaiQuay(@RequestBody BanTaiQuayRequest banTaiQuayRequest, HttpServletRequest request){
        Double tongTien = 0D;
        NhanVien nhanVien = userUltis.getLoggedInNhanVien(request);
        for(SanPhamChiTietPayment payment : banTaiQuayRequest.getSanPhamChiTietPayment()){
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            if(spct.getSoLuong() < payment.getSoLuong()){
                throw new MessageException("Số lượng sản phẩm: "+spct.getSanPham().getTenSanPham()+" chỉ còn: "+spct.getSoLuong());
            }
            tongTien += spct.getGiaTien() * payment.getSoLuong();
        }
        HoaDon hoaDon = new HoaDon();
        hoaDon.setTongTien(new BigDecimal(tongTien));
        hoaDon.setLoaiHoaDon(false);
        hoaDon.setNgayTao(new Timestamp(System.currentTimeMillis()));
        hoaDon.setTrangThai(8);
        hoaDon.setMaHoaDon("HD"+System.currentTimeMillis());
        hoaDon.setNhanVien(nhanVien);

        hoaDonRepository.save(hoaDon);
        for(SanPhamChiTietPayment payment : banTaiQuayRequest.getSanPhamChiTietPayment()){
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setTrangThai(1);
            hdct.setHoaDon(hoaDon);
            hdct.setGiaSanPham(new BigDecimal(spct.getGiaTien()));
            hdct.setSanPhamChiTiet(spct);
            hdct.setSoLuong(payment.getSoLuong().shortValue());
            hoaDonChiTietRepository.save(hdct);
            spct.setSoLuong(spct.getSoLuong() - payment.getSoLuong().shortValue());
            sanPhamRepository.save(spct.getSanPham());
        }
        return new ResponseEntity<>(hoaDon, HttpStatus.CREATED);
    }
    //
    @PostMapping("/tao-hoa-don-cho")
    public ResponseEntity<?> taoHoaDonCho(HttpServletRequest request, @RequestParam(required = false) Integer khachHangId) {
        NhanVien nhanVien = userUltis.getLoggedInNhanVien(request);
        KhachHang khachHang = null;

        // Nếu không cung cấp thông tin khách hàng, mặc định là khách lẻ
        if (khachHangId != null) {
            khachHang = khachHangRepository.findById(khachHangId)
                    .orElseThrow(() -> new MessageException("Không tìm thấy khách hàng."));
        }

        HoaDon hoaDon = new HoaDon();
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setLoaiHoaDon(false); // Bán tại quầy
        hoaDon.setTrangThai(0); // Trạng thái "chờ"
        hoaDon.setNgayTao(new Timestamp(System.currentTimeMillis()));
        hoaDon.setMaHoaDon("HD" + System.currentTimeMillis());

        hoaDonRepository.save(hoaDon);

        return new ResponseEntity<>(hoaDon, HttpStatus.CREATED);
    }
    @PostMapping("/them-san-pham-vao-hoa-don")
    public ResponseEntity<?> themSanPhamVaoHoaDon(@RequestParam Integer hoaDonId, @RequestBody List<SanPhamChiTietPayment> sanPhamChiTietPayments) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new MessageException("Không tìm thấy hóa đơn chờ."));

        Double tongTien = 0D;
        for (SanPhamChiTietPayment payment : sanPhamChiTietPayments) {
            SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdSPCT(payment.getIdSpct());
            if (spct.getSoLuong() < payment.getSoLuong()) {
                throw new MessageException("Số lượng sản phẩm: " + spct.getSanPham().getTenSanPham() + " chỉ còn: " + spct.getSoLuong());
            }

            // Cập nhật chi tiết hóa đơn
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setHoaDon(hoaDon);
            hdct.setSanPhamChiTiet(spct);
            hdct.setSoLuong(payment.getSoLuong().shortValue());
            hdct.setGiaSanPham(new BigDecimal(spct.getGiaTien()));
            hdct.setTrangThai(0); // Trạng thái chưa thanh toán
            hoaDonChiTietRepository.save(hdct);

            // Cập nhật tồn kho
            spct.setSoLuong(spct.getSoLuong() - payment.getSoLuong().shortValue());
            sanPhamRepository.save(spct.getSanPham());

            // Tính tổng tiền
            tongTien += spct.getGiaTien() * payment.getSoLuong();
        }

        // Cập nhật tổng tiền hóa đơn
        hoaDon.setTongTien(new BigDecimal(tongTien));
        hoaDonRepository.save(hoaDon);

        return new ResponseEntity<>(hoaDon, HttpStatus.OK);
    }
    @PostMapping("/thanh-toan-hoa-don")
    public ResponseEntity<?> thanhToanHoaDon(@RequestParam Integer hoaDonId, HttpServletRequest request) {
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new MessageException("Không tìm thấy hóa đơn."));

        if (hoaDon.getTrangThai() != 0) {
            throw new MessageException("Hóa đơn không ở trạng thái chờ thanh toán.");
        }

        // Cập nhật trạng thái hóa đơn là "đã thanh toán"
        hoaDon.setTrangThai(1);
        hoaDon.setNgayTao(new Timestamp(System.currentTimeMillis()));
        hoaDonRepository.save(hoaDon);

        // Tạo lịch sử thanh toán
        PhuongThucThanhToan phuongThucThanhToan = new PhuongThucThanhToan();
        phuongThucThanhToan.setHoaDon(hoaDon);
        phuongThucThanhToan.setNgayTao(new Timestamp(System.currentTimeMillis()));
        phuongThucThanhToan.setTrangThai(1);
        phuongThucThanhToan.setTenPhuongThuc("Tiền mặt");
        phuongThucThanhToan.setTongTien(hoaDon.getTongTien());
        phuongThucThanhToanRepository.save(phuongThucThanhToan);

        return new ResponseEntity<>(hoaDon, HttpStatus.OK);
    }


}
