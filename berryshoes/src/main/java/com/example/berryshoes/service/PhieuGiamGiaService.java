package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.PhieuGiamGiaRequest;
import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.entity.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PhieuGiamGiaService {
    List<PhieuGiamGia> getAllPhieuGiamGia();
    Optional<PhieuGiamGia> getPhieuGiamGiaById(Integer id);
    PhieuGiamGia create(PhieuGiamGiaRequest requestDTO);
    PhieuGiamGia update(Integer id, PhieuGiamGiaRequest requestDTO);
    void delete(Integer id);
    // Tìm kiếm phiếu giảm giá theo điều kiện và phân trang
    Page<PhieuGiamGia> findAllOrderByNgayTaoDESC(String keySearch, Timestamp tungaySearch, Timestamp denngaySearch,
                                                 Boolean kieuSearch, Boolean loaiSearch, Integer ttSearch, Pageable pageable);

    // Tìm tất cả phiếu giảm giá theo hóa đơn
    List<PhieuGiamGia> findAllByHoaDon(HoaDon hoaDon);

    PhieuGiamGia findPhieuGiamGiaByMaCode(String maCode);

    // Tìm phiếu giảm giá theo kiểu phiếu và trạng thái
    List<PhieuGiamGia> findAllByKieuPhieuAndTrangThai(Boolean kieuPhieu, Integer trangThai);

    // Lấy phiếu giảm giá mới nhất
    PhieuGiamGia findFirstByOrderByNgayTaoDesc();
}
