package com.example.berryshoes.repository;

import com.example.berryshoes.entity.GioHang;
import com.example.berryshoes.entity.KhachHang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    @Query("SELECT gh FROM GioHang gh WHERE gh.khachHang = :khachhang AND gh.trangThai = :trangThai")
    GioHang findCurrentGioHang(@Param("khachhang") KhachHang khachhang, @Param("trangThai") Integer trangThai);
    GioHang findByKhachHang(KhachHang khachHang);

    @Query(value = """
            SELECT g FROM GioHang g where g.khachHang.id= :id
            """)
    GioHang findByIdKhachHang(Integer id);
    @Query("SELECT g FROM GioHang g WHERE g.id =?1 and g.id =?2")
    GioHang findByGiohangIdAndSanPhamChiTietId(Integer gioHangId, Integer spctId);
    @Transactional
    @Modifying
    @Query("UPDATE GioHang gh SET gh.soLuong = :soLuong WHERE gh.id = :id")
    void updateSoLuongById(Integer soLuong, Integer id);
    @Query(value = """
            SELECT g FROM GioHang g where g.id=:id
            """)
    List<GioHang> findGioHangByGiohang(Integer id);
    @Modifying
    @Transactional
    @Query("DELETE FROM GioHang g where g.id = :Id")
    void deleteGioHang(@Param("Id") Integer Id);
    @Query(value = """
            SELECT k FROM KhachHang k where k.id= :id
            """)
    KhachHang findByKhachHang(Integer id);

    @Query("select g from GioHang g where g.khachHang.id = ?1 and g.sanPhamChiTiet.id = ?2")
    GioHang findByKhachHangAndSanPhamChiTiet(Integer khachHangId, Integer spct);

    @Query("select count(g.id) from GioHang g where g.khachHang.id = ?1")
    Long demGioHang(Integer id);

    @Query("select g from GioHang g where g.khachHang.id = ?1")
    List<GioHang> findByKhachHangId(Integer idKhachHang);
}
