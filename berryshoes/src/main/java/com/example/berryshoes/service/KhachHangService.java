package com.example.berryshoes.service;
import com.example.berryshoes.dto.request.KhachHangRequest;
import com.example.berryshoes.entity.KhachHang;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface KhachHangService {
    List<KhachHang> getAllKhachHang();
    Optional<KhachHang> getKhachHangById(Integer id);
    KhachHang createKhachHang(KhachHangRequest requestDTO);
    KhachHang updateKhachHang(Integer id, KhachHangRequest requestDTO);
    void deleteKhachHang(Integer id);
    // Tìm khách hàng theo tài khoản
    KhachHang findKhachHangByTaikhoan(String taiKhoan);

    // Tìm khách hàng theo tên hoặc số điện thoại
    List<KhachHang> findByHoVaTenOrSoDienThoai(String hoVaTen, String soDienThoai);

    // Tìm khách hàng theo ngày sinh trong khoảng
    List<KhachHang> findKhachHangByNgaySinhBetween(Date startDate, Date endDate);

    // Tìm khách hàng theo email
    KhachHang findKhachHangByEmail(String email);

    // Cập nhật mật khẩu cho khách hàng
//    void updatePassword(String taiKhoan, String newPassword);

    // Kiểm tra tồn tại số điện thoại
    boolean existsBySoDienThoai(String soDienThoai);

    // Kiểm tra tồn tại email
    boolean existsByEmail(String email);

    // Tìm khách hàng theo số điện thoại
    KhachHang searchKhachHangBySoDienThoai(String soDienThoai);

    // Tìm khách hàng theo ID
    KhachHang searchKhachHangById(Integer id);

    // Lấy tất cả khách hàng, sắp xếp theo ID giảm dần
    List<KhachHang> findAllByOrderByIdDesc();
}