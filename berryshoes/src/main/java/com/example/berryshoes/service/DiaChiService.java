package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.DiaChiRequest;
import com.example.berryshoes.entity.DiaChi;
import com.example.berryshoes.entity.KhachHang;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DiaChiService {
    List<DiaChi> getAllDiaChi();

    Optional<DiaChi> getDiaChiById(Integer id);

    DiaChi createDiaChi(DiaChiRequest requestDTO, KhachHang khachHang);

    DiaChi updateDiaChi(Integer id, DiaChiRequest requestDTO);

    void deleteDiaChi(Integer id);
    DiaChi findByTenNguoiNhanAndSdtNguoiNhanAndTenDuongAndTinhThanhPhoAndQuanHuyenAndXaPhuong(
            String ten, String sdt, String tenduong, String tinhthanh, String quanhuyen, String xaphuong);

    List<DiaChi> findByTrangThai(Integer trangThai);

    DiaChi findByIdKhachHangMacDinh(Integer idKhachHang);

    List<DiaChi> findByHoTenHoacSdt(String ht, String sdt);

    List<DiaChi> findByKey(String name, Date startDate, Date endDate, Integer status);

    List<DiaChi> findByTinhThanhPho(String tinhThanhPho);

    List<DiaChi> findByIdKhachHang(Integer idKhachHang);

    List<DiaChi> findByIdKhachHangAndTrangThai(Integer idKhachHang, Integer trangThai);

    DiaChi findByIdDiaChi(Integer idDiaChi);
}
