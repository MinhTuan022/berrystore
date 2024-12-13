package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
    DiaChi findByTenNguoiNhanAndSdtNguoiNhanAndTenDuongAndTinhThanhPhoAndQuanHuyenAndXaPhuong(
            String ten, String sdt, String tenduong, String tinhthanh, String quanhuyen, String xaphuong);

    List<DiaChi> findByTrangThai(Integer trangThai);

    @Query("SELECT d FROM DiaChi d WHERE d.khachHang.id = :idKhachHang AND d.trangThai = 1")
    DiaChi findByIdKhachHangMacDinh(@Param("idKhachHang") Integer idKhachHang);


//    @Query("SELECT d FROM DiaChi d WHERE d.khachHang.id = ?1")

    @Query("SELECT d FROM DiaChi d WHERE d.khachHang.hoVaTen = ?1 OR d.khachHang.soDienThoai = ?1")
    List<DiaChi> findByHoTenHoacSdt(String ht, String sdt);

    @Query("SELECT d FROM DiaChi d WHERE " +
            "(:name is null or d.khachHang.hoVaTen LIKE %:name% or d.khachHang.soDienThoai LIKE %:name%) and " +
            "(:startDate is null or d.ngayTao >= :startDate) and " +
            "(:endDate is null or d.ngayTao <= :endDate) and " +
            "(:status is null or d.trangThai = :status) ORDER BY d.lanCapNhatCuoi DESC")
    List<DiaChi> findByKey(@Param("name") String name,
                           @Param("startDate") Date startDate,
                           @Param("endDate") Date endDate,
                           @Param("status") Integer status);

    @Query("SELECT d FROM DiaChi d WHERE d.tinhThanhPho LIKE %?1%")
    List<DiaChi> findByTinhThanhPho(@Param("tinhThanhPho") String tinhThanhPho);

    @Query("SELECT d FROM DiaChi d WHERE d.khachHang.id = ?1")
    List<DiaChi> findByIdKhachHang(Integer idKhachHang);

    @Query("SELECT d FROM DiaChi d WHERE d.khachHang.id = ?1 AND d.trangThai = ?2")
    List<DiaChi> findByIdKhachHangAndTrangThai(Integer idKhachHang, Integer trangThai);

    @Query("SELECT d FROM DiaChi d WHERE d.id = ?1")
    DiaChi findByIdDiaChi(Integer idDiaChi);
}
