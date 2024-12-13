package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.NhanVienRequest;
import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.NhanVien;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {
    List<NhanVien> getAllNhanVien();

    Optional<NhanVien> getNhanVienById(Integer id);

    NhanVien createNhanVien(NhanVienRequest requestDTO);

    NhanVien updateNhanVien(Integer id, NhanVienRequest requestDTO);

    void deleteNhanVien(Integer id);
    // Tìm nhân viên theo email
    NhanVien findNhanVienByEmail(String email);

    // Tìm nhân viên theo mã nhân viên
    List<NhanVien> findNhanVienByMa(String maNhanVien);

    // Lấy tất cả nhân viên sắp xếp theo lần cập nhật cuối giảm dần
    List<NhanVien> getAllOrderByLanCapNhatCuoiDesc();

    // Lấy tất cả nhân viên ngoại trừ một ID
    List<NhanVien> getAllExceptId(Integer id);

    // Tìm nhân viên theo tên hoặc số điện thoại
    List<NhanVien> findByHoVaTenOrSoDienThoai(String hoVaTen, String soDienThoai);

    // Cập nhật mật khẩu cho khách hàng
    void updatePassword(String taiKhoan, String newPassword);

    // Kiểm tra tồn tại số điện thoại
    boolean existsBySoDienThoai(String soDienThoai);

    // Kiểm tra tồn tại email
    boolean existsByEmail(String email);
}
