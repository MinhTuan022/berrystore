package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.SanPhamChiTietRequest;
import com.example.berryshoes.dto.request.SanPhamChiTietUpdateRequest;
import com.example.berryshoes.entity.SanPhamChiTiet;

import java.util.List;
import java.util.Optional;
public interface SanPhamChiTietService {
    List<SanPhamChiTiet> getAll();
    Optional<SanPhamChiTiet> getById(Integer id);
    SanPhamChiTiet create(SanPhamChiTietRequest requestDTO);
    SanPhamChiTiet update(Integer id, SanPhamChiTietUpdateRequest requestDTO);
    void delete(Integer id);
}