package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.KichCoRequest;
import com.example.berryshoes.entity.KichCo;

import java.util.List;
import java.util.Optional;

public interface KichCoService {
    List<KichCo> getAllKichCo();

    Optional<KichCo> getKichCoById(Integer id);

    KichCo createKichCo(KichCoRequest requestDTO);

    KichCo updateKichCo(Integer id, KichCoRequest requestDTO);

    void deleteKichCo(Integer id);
    List<KichCo> searchKichCoByTenAndTrangthai(String ten, Integer trangThai);

    boolean existsByTenKichCo(String tenKichCo);

    KichCo saveKichCo(KichCo kichCo);

    void updateTrangThaiToFalseById(Integer id);
}
