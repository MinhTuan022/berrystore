package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.DiaChiRequest;
import com.example.berryshoes.entity.DiaChi;
import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.repository.DiaChiRepository;
import com.example.berryshoes.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Override
    public List<DiaChi> getAllDiaChi() {
        return diaChiRepository.findAll();
    }

    @Override
    public Optional<DiaChi> getDiaChiById(Integer id) {
        return diaChiRepository.findById(id);
    }

    @Override
    public DiaChi createDiaChi(DiaChiRequest requestDTO, KhachHang khachHang) {
        DiaChi diaChi = DiaChi.builder()
                .khachHang(khachHang)
                .tenDuong(requestDTO.getTenDuong())
                .xaPhuong(requestDTO.getXaPhuong())
                .quanHuyen(requestDTO.getQuanHuyen())
                .tinhThanhPho(requestDTO.getTinhThanhPho())
                .tenNguoiNhan(requestDTO.getTenNguoiNhan())
                .sdtNguoiNhan(requestDTO.getSdtNguoiNhan())
                .ngayTao(new Timestamp(System.currentTimeMillis()))
                .build();
        return diaChiRepository.save(diaChi);
    }

    @Override
    public DiaChi updateDiaChi(Integer id, DiaChiRequest requestDTO) {
        Optional<DiaChi> optionalDiaChi = diaChiRepository.findById(id);
        if (optionalDiaChi.isPresent()) {
            DiaChi diaChi = optionalDiaChi.get();
            diaChi.setTenDuong(requestDTO.getTenDuong());
            diaChi.setXaPhuong(requestDTO.getXaPhuong());
            diaChi.setQuanHuyen(requestDTO.getQuanHuyen());
            diaChi.setTinhThanhPho(requestDTO.getTinhThanhPho());
            diaChi.setTenNguoiNhan(requestDTO.getTenNguoiNhan());
            diaChi.setSdtNguoiNhan(requestDTO.getSdtNguoiNhan());
            return diaChiRepository.save(diaChi);
        }
        return null;
    }

    @Override
    public void deleteDiaChi(Integer id) {
        if (diaChiRepository.existsById(id)) {
            diaChiRepository.deleteById(id);
        } else {
            throw new RuntimeException("Địa chỉ không tồn tại");
        }
    }
    @Override
    public DiaChi findByTenNguoiNhanAndSdtNguoiNhanAndTenDuongAndTinhThanhPhoAndQuanHuyenAndXaPhuong(
            String ten, String sdt, String tenduong, String tinhthanh, String quanhuyen, String xaphuong) {
        return diaChiRepository.findByTenNguoiNhanAndSdtNguoiNhanAndTenDuongAndTinhThanhPhoAndQuanHuyenAndXaPhuong(ten, sdt, tenduong, tinhthanh, quanhuyen, xaphuong);
    }

    @Override
    public List<DiaChi> findByTrangThai(Integer trangThai) {
        return diaChiRepository.findByTrangThai(trangThai);
    }

    @Override
    public DiaChi findByIdKhachHangMacDinh(Integer idKhachHang) {
        return diaChiRepository.findByIdKhachHangMacDinh(idKhachHang);
    }

    @Override
    public List<DiaChi> findByHoTenHoacSdt(String ht, String sdt) {
        return diaChiRepository.findByHoTenHoacSdt(ht, sdt);
    }

    @Override
    public List<DiaChi> findByKey(String name, Date startDate, Date endDate, Integer status) {
        return diaChiRepository.findByKey(name, startDate, endDate, status);
    }

    @Override
    public List<DiaChi> findByTinhThanhPho(String tinhThanhPho) {
        return diaChiRepository.findByTinhThanhPho(tinhThanhPho);
    }

    @Override
    public List<DiaChi> findByIdKhachHang(Integer idKhachHang) {
        return diaChiRepository.findByIdKhachHang(idKhachHang);
    }

    @Override
    public List<DiaChi> findByIdKhachHangAndTrangThai(Integer idKhachHang, Integer trangThai) {
        return diaChiRepository.findByIdKhachHangAndTrangThai(idKhachHang, trangThai);
    }

    @Override
    public DiaChi findByIdDiaChi(Integer idDiaChi) {
        return diaChiRepository.findByIdDiaChi(idDiaChi);
    }
}
