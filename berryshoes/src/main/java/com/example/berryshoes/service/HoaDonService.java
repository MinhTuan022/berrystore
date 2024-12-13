package com.example.berryshoes.service;

import com.example.berryshoes.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface HoaDonService {
    List<HoaDon> findHoaDonByMaOrSdtOrEmail(String inputSearch);
    HoaDon findHoaDonById(Integer id);
    List<HoaDon> findAllByKhachHang(Integer id);
    List<HoaDon> findHoaDonByTrangThaiAndKhachhang(Integer trangThai, Integer id);
    Page<HoaDon> findAllByTrangThaiAndLoaiHoaDonAndNgayTaoBetween(Integer trangThai, Boolean loaihd, Date tu, Date den, Pageable pageable);
    Page<HoaDon> findAll(Pageable pageable);
    Page<HoaDon> findAllByTrangThai(Integer trangThai, Pageable pageable);
    Long countAllByTrangThai(Integer trangThai);
    Page<HoaDon> findAllByLoaiHoaDonAndNgayTaoBetween(Boolean loaihd, Date tu, Date den, Pageable pageable);
    Page<HoaDon> findAllByTrangThaiAndNgayTaoBetween(Integer trangThai, Date tu, Date den, Pageable pageable);
    Page<HoaDon> findAllByTrangThaiAndLoaiHoaDon(Integer trangThai, Boolean loaihd, Pageable pageable);
    List<HoaDon> findAllById(Integer id);
    List<HoaDon> timHDGanNhat();
    HoaDon timHDTheoMaHD(String mahd);
    boolean existsById(Integer id);
}
