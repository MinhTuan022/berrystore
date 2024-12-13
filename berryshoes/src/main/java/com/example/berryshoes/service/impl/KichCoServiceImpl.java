package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.KichCoRequest;
import com.example.berryshoes.entity.KichCo;
import com.example.berryshoes.repository.KichCoRepository;
import com.example.berryshoes.service.KichCoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KichCoServiceImpl implements KichCoService {
    private final KichCoRepository kichCoRepository;

    @Override
    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAll();
    }

    @Override
    public Optional<KichCo> getKichCoById(Integer id) {
        return kichCoRepository.findById(id);
    }

    @Override
    public KichCo createKichCo(KichCoRequest requestDTO) {
        KichCo kichCo = KichCo.builder()
                .tenKichCo(requestDTO.getTenKichCo())
                .nguoiTao(requestDTO.getNguoiTao())
                .trangThai(requestDTO.getTrangThai())
                .build();
        return kichCoRepository.save(kichCo);
    }

    @Override
    public KichCo updateKichCo(Integer id, KichCoRequest requestDTO) {
        Optional<KichCo> optionalKichCo = kichCoRepository.findById(id);
        if (optionalKichCo.isPresent()) {
            KichCo kichCo = optionalKichCo.get();
            kichCo.setTenKichCo(requestDTO.getTenKichCo());
            kichCo.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            kichCo.setTrangThai(requestDTO.getTrangThai());
            return kichCoRepository.save(kichCo);
        }
        return null;
    }

    @Override
    public void deleteKichCo(Integer id) {
        if (kichCoRepository.existsById(id)) {
            kichCoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Kích cỡ không tồn tại");
        }
    }
    @Override
    public List<KichCo> searchKichCoByTenAndTrangthai(String ten, Integer trangThai) {
        return kichCoRepository.findByTenAndTrangthai(ten, trangThai);
    }

    @Override
    public boolean existsByTenKichCo(String tenKichCo) {
        return kichCoRepository.existsByTenKichCo(tenKichCo);
    }

    @Override
    public KichCo saveKichCo(KichCo kichCo) {
        return kichCoRepository.save(kichCo);
    }

    @Override
    public void updateTrangThaiToFalseById(Integer id) {
        kichCoRepository.updateTrangThaiToFalseById(id);
    }
}
