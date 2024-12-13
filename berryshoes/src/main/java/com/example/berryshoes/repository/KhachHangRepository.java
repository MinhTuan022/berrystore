package com.example.berryshoes.repository;

import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    // Tìm Khách Hàng theo tài khoản
//    @Query("SELECT kh FROM KhachHang kh WHERE kh.taiKhoan = ?1")
//    KhachHang findKhachHangByTaikhoan(String taiKhoan);

    // Tìm Khách Hàng theo tên hoặc số điện thoại
    @Query("SELECT kh FROM KhachHang kh WHERE kh.hoVaTen LIKE %:hoVaTen% OR kh.soDienThoai LIKE %:soDienThoai%")
    List<KhachHang> findByHoVaTenOrSoDienThoai(@Param("hoVaTen") String hoVaTen,
                                               @Param("soDienThoai") String soDienThoai);

//    // Tìm Khách Hàng theo CCCD
//    @Query("SELECT kh FROM KhachHang kh WHERE kh.cccd LIKE %:cccd%")
//    List<KhachHang> findKhachHangByCccd(@Param("cccd") String cccd);

    // Tìm Khách Hàng theo ngày sinh
    @Query("SELECT kh FROM KhachHang kh WHERE kh.ngaySinh BETWEEN :startDate AND :endDate")
    List<KhachHang> findKhachHangByNgaySinhBetween(@Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

    // Tìm Khách Hàng theo email
    @Query("SELECT kh FROM KhachHang kh WHERE kh.email = :email")
    KhachHang findKhachHangByEmail(@Param("email") String email);

    // Tìm Khách Hàng theo email (không dùng query)
    @Query("select k from KhachHang k where k.email = ?1")
    KhachHang findByEmail(String email);

    @Query("select k from KhachHang  k where k.email = ?1")
    Optional<KhachHang> findKhByEmail(String email);

//    @Query("select k from KhachHang  k where k.taiKhoan = ?1")
//    Optional<KhachHang> findKhByTaiKhoan(String taiKhoan);

    // Cập nhật mật khẩu cho Khách Hàng
//    @Transactional
//    @Modifying
//    @Query("UPDATE KhachHang kh SET kh.matKhau = :newPassword WHERE kh.taiKhoan = :taiKhoan")
//    void updatePassword(@Param("taiKhoan") String taiKhoan, @Param("newPassword") String newPassword);

    // Kiểm tra tồn tại số điện thoại
    boolean existsBySoDienThoai(String soDienThoai);

    // Kiểm tra tồn tại email
    boolean existsByEmail(String email);
    // Tìm Khách Hàng theo số điện thoại
    @Query("SELECT k FROM KhachHang k WHERE k.soDienThoai = ?1")
    KhachHang searchKhachHangBySoDienThoai(String soDienThoai);

    // Tìm Khách Hàng theo ID
    @Query("SELECT k FROM KhachHang k WHERE k.id = ?1")
    KhachHang searchKhachHangById(Integer id);
    // Lấy tất cả Khách Hàng, sắp xếp theo ID giảm dần
    List<KhachHang> findAllByOrderByIdDesc();
    // lấy max mã KH
    @Query("SELECT MAX(kh.maKhachHang) FROM KhachHang kh")
    Optional<String> findMaxMaKhachHang();
    //phân trang
    @Query("select kh from KhachHang kh where kh.trangThai = ?1")
    Page<KhachHang> findByTrangThai(Integer trangthai, Pageable pageable);
}
