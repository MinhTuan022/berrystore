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
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    // Tìm nhân viên theo ID
    @Query("SELECT n FROM NhanVien n WHERE n.id = :id")
    NhanVien findNhanVienById(@Param("id") Integer id);

    // Tìm nhân viên theo email
    @Query("SELECT n FROM NhanVien n WHERE n.email = :email")
    NhanVien findNhanVienByEmail(@Param("email") String email);

    @Query("SELECT n FROM NhanVien n WHERE n.email = ?1")
    Optional<NhanVien> findByEmail(String email);

    // Tìm nhân viên theo mã nhân viên
    @Query("SELECT n FROM NhanVien n WHERE n.maNhanVien LIKE %:maNhanVien%")
    List<NhanVien> timNhanVienTheoMa(@Param("maNhanVien") String maNhanVien);

    // Lấy tất cả nhân viên, sắp xếp theo lần cập nhật cuối giảm dần
    @Query("SELECT n FROM NhanVien n ORDER BY n.lanCapNhatCuoi DESC")
    List<NhanVien> getAllOrderByLanCapNhatCuoiDesc();

    // Lấy tất cả nhân viên ngoại trừ nhân viên có ID được cung cấp, sắp xếp theo lần cập nhật cuối giảm dần
    @Query("SELECT n FROM NhanVien n WHERE n.id <> :id ORDER BY n.lanCapNhatCuoi DESC")
    List<NhanVien> getAllExceptId(@Param("id") Integer id);

    // Tìm kiếm nhân viên theo tên hoặc số điện thoại
    @Query("SELECT nv FROM NhanVien nv WHERE nv.hoVaTen LIKE %:hoVaTen% OR nv.soDienThoai LIKE %:soDienThoai%")
    List<NhanVien> findByHoVaTenOrSoDienThoai(@Param("hoVaTen") String hoVaTen,
                                              @Param("soDienThoai") String soDienThoai);

    // Tìm kiếm nhân viên theo tên hoặc số điện thoại, sắp xếp theo lần cập nhật cuối
    @Query("SELECT n FROM NhanVien n WHERE " +
            "(:key IS NULL OR n.hoVaTen LIKE %:key% OR n.soDienThoai LIKE %:key%) AND " +
            "(:startDate IS NULL OR n.ngaySinh >= :startDate) AND " +
            "(:endDate IS NULL OR n.ngaySinh <= :endDate) " +
            "ORDER BY n.lanCapNhatCuoi DESC")
    List<NhanVien> findByKe(@Param("key") String key,
                            @Param("startDate") Date startDate,
                            @Param("endDate") Date endDate);
    // Cập nhật mật khẩu cho Khách Hàng
    @Transactional
    @Modifying
    @Query("UPDATE NhanVien nv SET nv.matKhau = :newPassword WHERE nv.taiKhoan = :taiKhoan")
    void updatePassword(@Param("taiKhoan") String taiKhoan, @Param("newPassword") String newPassword);

    // Kiểm tra tồn tại số điện thoại
    boolean existsBySoDienThoai(String soDienThoai);

    // Kiểm tra tồn tại email
    boolean existsByEmail(String email);

    @Query("select nv from NhanVien nv where nv.trangThai = ?1")
    Page<NhanVien> findByTrangThai(Integer trangthai, Pageable pageable);
}

