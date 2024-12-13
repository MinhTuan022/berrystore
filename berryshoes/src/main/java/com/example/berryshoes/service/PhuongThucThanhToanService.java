package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.PhuongThucThanhToanRequest;
import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.entity.PhuongThucThanhToan;

import java.util.List;
import java.util.Optional;
public interface PhuongThucThanhToanService {
    List<PhuongThucThanhToan> getAll();
    Optional<PhuongThucThanhToan> getById(Integer id);
    PhuongThucThanhToan create(PhuongThucThanhToanRequest requestDTO);
    PhuongThucThanhToan update(Integer id, PhuongThucThanhToanRequest requestDTO);
    void delete(Integer id);
    PhuongThucThanhToan getByIdHoaDon(Integer idHoaDon);
    List<PhuongThucThanhToan> getAllByHoaDon(HoaDon hd);
}