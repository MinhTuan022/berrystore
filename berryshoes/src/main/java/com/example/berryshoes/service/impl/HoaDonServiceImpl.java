package com.example.berryshoes.service.impl;

import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.repository.HoaDonRepository;
import com.example.berryshoes.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> findHoaDonByMaOrSdtOrEmail(String inputSearch) {
        return hoaDonRepository.findHoaDonByMaOrSdtOrEmail(inputSearch);
    }

    @Override
    public HoaDon findHoaDonById(Integer id) {
        return hoaDonRepository.findHoaDonById(id);
    }

    @Override
    public List<HoaDon> findAllByKhachHang(Integer id) {
        return hoaDonRepository.findAllByKhachHang(id);
    }

    @Override
    public List<HoaDon> findHoaDonByTrangThaiAndKhachhang(Integer trangThai, Integer id) {
        return hoaDonRepository.findHoaDonByTrangThaiAndKhachhang(trangThai, id);
    }

    @Override
    public Page<HoaDon> findAllByTrangThaiAndLoaiHoaDonAndNgayTaoBetween(Integer trangThai, Boolean loaihd, Date tu, Date den, Pageable pageable) {
        return hoaDonRepository.findAllByTrangThaiAndLoaiHoaDonAndNgayTaoGreaterThanEqualAndNgayTaoLessThanEqual(trangThai, loaihd, tu, den, pageable);
    }

    @Override
    public Page<HoaDon> findAll(Pageable pageable) {
        return hoaDonRepository.findAlls(pageable);
    }

    @Override
    public Page<HoaDon> findAllByTrangThai(Integer trangThai, Pageable pageable) {
        return hoaDonRepository.findAllByTrangthai(trangThai, pageable);
    }

    @Override
    public Long countAllByTrangThai(Integer trangThai) {
        return hoaDonRepository.countAllByTrangThai(trangThai);
    }

    @Override
    public Page<HoaDon> findAllByLoaiHoaDonAndNgayTaoBetween(Boolean loaihd, Date tu, Date den, Pageable pageable) {
        return hoaDonRepository.findAllByLoaiHoaDonAndNgayTaoGreaterThanEqualAndNgayTaoLessThanEqual(loaihd, tu, den, pageable);
    }

    @Override
    public Page<HoaDon> findAllByTrangThaiAndNgayTaoBetween(Integer trangThai, Date tu, Date den, Pageable pageable) {
        return hoaDonRepository.findAllByTrangThaiAndNgayTaoGreaterThanEqualAndNgayTaoLessThanEqual(trangThai, tu, den, pageable);
    }

    @Override
    public Page<HoaDon> findAllByTrangThaiAndLoaiHoaDon(Integer trangThai, Boolean loaihd, Pageable pageable) {
        return hoaDonRepository.findAllByTrangThaiAndLoaiHoaDon(trangThai, loaihd, pageable);
    }

    @Override
    public List<HoaDon> findAllById(Integer id) {
        return hoaDonRepository.findAllById(id);
    }

    @Override
    public List<HoaDon> timHDGanNhat() {
        return hoaDonRepository.timHDGanNhat();
    }

    @Override
    public HoaDon timHDTheoMaHD(String mahd) {
        return hoaDonRepository.timHDTheoMaHD(mahd);
    }

    @Override
    public boolean existsById(Integer id) {
        return hoaDonRepository.existsById(id);
    }
}
