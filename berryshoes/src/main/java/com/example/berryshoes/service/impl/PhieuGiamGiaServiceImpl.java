package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.PhieuGiamGiaRequest;
import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.entity.PhieuGiamGia;
import com.example.berryshoes.repository.PhieuGiamGiaRepository;
import com.example.berryshoes.service.PhieuGiamGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {
    private final PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Override
    public List<PhieuGiamGia> getAllPhieuGiamGia() {
        List<PhieuGiamGia> phieuGiamGias = phieuGiamGiaRepository.findAll();
        Date now = new Date();

        for (PhieuGiamGia phieu : phieuGiamGias) {
            if (now.before(phieu.getNgayBatDau())) {
                phieu.setTrangThai(2); // Chưa bắt đầu
            } else if (now.after(phieu.getNgayKetThuc())) {
                phieu.setTrangThai(3); // Đã hết hạn
            }
        }
        return phieuGiamGias;
//        return phieuGiamGiaRepository.findAll();
    }

    @Override
    public Optional<PhieuGiamGia> getPhieuGiamGiaById(Integer id) {
        return phieuGiamGiaRepository.findById(id);
    }

    @Override
    public PhieuGiamGia create(PhieuGiamGiaRequest requestDTO) {
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        phieuGiamGia.setMaCode(requestDTO.getMaCode());
        phieuGiamGia.setTenPhieu(requestDTO.getTenPhieu());
        phieuGiamGia.setGiaTriGiamToiDa(requestDTO.getGiaTriGiamToiDa());
        phieuGiamGia.setGiaTriGiam(requestDTO.getGiaTriGiam());
        phieuGiamGia.setDonToiThieu(requestDTO.getDonToiThieu());
        phieuGiamGia.setSoLuong(requestDTO.getSoLuong());
        phieuGiamGia.setLoaiPhieu(requestDTO.getLoaiPhieu());
        phieuGiamGia.setKieuPhieu(requestDTO.getKieuPhieu());
        phieuGiamGia.setNgayBatDau(Timestamp.valueOf(requestDTO.getNgayBatDau().toLocalDateTime()));
        phieuGiamGia.setNgayKetThuc(Timestamp.valueOf(requestDTO.getNgayKetThuc().toLocalDateTime()));
        phieuGiamGia.setNguoiTao(requestDTO.getNguoiTao());
        phieuGiamGia.setTrangThai(requestDTO.getTrangThai());

        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    @Override
    public PhieuGiamGia update(Integer id, PhieuGiamGiaRequest requestDTO) {
        Optional<PhieuGiamGia> optionalPhieuGiamGia = phieuGiamGiaRepository.findById(id);
        if (optionalPhieuGiamGia.isPresent()) {
            PhieuGiamGia phieuGiamGia = optionalPhieuGiamGia.get();
            phieuGiamGia.setMaCode(requestDTO.getMaCode());
            phieuGiamGia.setTenPhieu(requestDTO.getTenPhieu());
            phieuGiamGia.setGiaTriGiamToiDa(requestDTO.getGiaTriGiamToiDa());
            phieuGiamGia.setGiaTriGiam(requestDTO.getGiaTriGiam());
            phieuGiamGia.setDonToiThieu(requestDTO.getDonToiThieu());
            phieuGiamGia.setSoLuong(requestDTO.getSoLuong());
            phieuGiamGia.setLoaiPhieu(requestDTO.getLoaiPhieu());
            phieuGiamGia.setKieuPhieu(requestDTO.getKieuPhieu());
            phieuGiamGia.setNgayBatDau(Timestamp.valueOf(requestDTO.getNgayBatDau().toLocalDateTime()));
            phieuGiamGia.setNgayKetThuc(Timestamp.valueOf(requestDTO.getNgayKetThuc().toLocalDateTime()));
            phieuGiamGia.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            phieuGiamGia.setTrangThai(requestDTO.getTrangThai());

            return phieuGiamGiaRepository.save(phieuGiamGia);
        }
        return null;
    }

    public PhieuGiamGia update_sl(Integer id, Short sl) {
        Optional<PhieuGiamGia> optionalPhieuGiamGia = phieuGiamGiaRepository.findById(id);
        optionalPhieuGiamGia.get().setSoLuong((short) (optionalPhieuGiamGia.get().getSoLuong() + sl));
        if (optionalPhieuGiamGia.get().getSoLuong() == 0) {
            optionalPhieuGiamGia.get().setTrangThai(2);
        }
        return phieuGiamGiaRepository.save(optionalPhieuGiamGia.get());
    }

    @Override
    public void delete(Integer id) {
        if (phieuGiamGiaRepository.existsById(id)) {
            phieuGiamGiaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Phiếu giảm giá không tồn tại");
        }
    }
    @Override
    public List<PhieuGiamGia> findAllByHoaDon(HoaDon hoaDon) {
        return null;
    }

    @Override
    public PhieuGiamGia findPhieuGiamGiaByMaCode(String maCode) {
        return phieuGiamGiaRepository.findPhieuGiamGiaByMaCode(maCode);
    }

    @Override
    public List<PhieuGiamGia> findAllByKieuPhieuAndTrangThai(Boolean kieuPhieu, Integer trangThai) {
        return phieuGiamGiaRepository.findAllByKieuphieuaAndTrangthais(kieuPhieu, trangThai);
    }

    @Override
    public PhieuGiamGia findFirstByOrderByNgayTaoDesc() {
        return phieuGiamGiaRepository.findFirstByOrderByNgayTaoDesc();
    }
    @Override
    public Page<PhieuGiamGia> findAllOrderByNgayTaoDESC(String keySearch, Timestamp tungaySearch, Timestamp denngaySearch,
                                                        Boolean kieuSearch, Boolean loaiSearch, Integer ttSearch, Pageable pageable) {
        return phieuGiamGiaRepository.findAllOrderByNgayTaoDESC(keySearch, tungaySearch, denngaySearch,
                kieuSearch, loaiSearch, ttSearch, pageable);
    }
}
