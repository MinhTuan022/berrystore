package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.DeGiayRequest;
import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.repository.DeGiayRepository;
import com.example.berryshoes.service.DeGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeGiayServiceImpl implements DeGiayService {

    @Autowired
    private DeGiayRepository deGiayRepository;

    @Override
    public List<DeGiay> getAllDeGiay() {
        return deGiayRepository.findAll();
    }

    @Override
    public Optional<DeGiay> getDeGiayById(Integer id) {
        return deGiayRepository.findById(id);
    }

    @Override
    public DeGiay createDeGiay(DeGiayRequest requestDTO) {
        DeGiay deGiay = DeGiay.builder()
                .tenDeGiay(requestDTO.getTenDeGiay())
                .nguoiTao(requestDTO.getNguoiTao())
                .trangThai(requestDTO.getTrangThai())
                .build();
        return deGiayRepository.save(deGiay);
    }

    @Override
    public DeGiay updateDeGiay(Integer id, DeGiayRequest requestDTO) {
        Optional<DeGiay> optionalDeGiay = deGiayRepository.findById(id);
        if (optionalDeGiay.isPresent()) {
            DeGiay deGiay = optionalDeGiay.get();
            deGiay.setTenDeGiay(requestDTO.getTenDeGiay());
            deGiay.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            deGiay.setTrangThai(requestDTO.getTrangThai());
            return deGiayRepository.save(deGiay);
        }
        return null;
    }

    @Override
    public void deleteDeGiay(Integer id) {
        if (deGiayRepository.existsById(id)) {
            deGiayRepository.deleteById(id);
        } else {
            throw new RuntimeException("Đế giày không tồn tại");
        }
    }

    @Override
    public List<DeGiay> findByTenAndTrangThai(String ten, Integer trangThai) {
        return deGiayRepository.findByTenAndTrangThai(ten, trangThai);
    }

    @Override
    public List<DeGiay> findAllByOrderByNgayTaoDesc() {
        return deGiayRepository.findAllByOrderByNgayTaoDesc();
    }

    @Override
    public List<DeGiay> findDeGiayByTen(String ten) {
        return deGiayRepository.findDeGiayByTen(ten);
    }

    @Override
    public List<DeGiay> findDeGiayByTenAndTrangThaiFalse(String ten) {
        return deGiayRepository.findDeGiayByTenAndTrangThaiFalse(ten);
    }


    @Override
    public void updateTrangThaiToFalseById(Integer id) {
        deGiayRepository.updateTrangThaiToFalseById(id);
    }

    @Override
    public boolean existsByTenDeGiay(String ten) {
        return deGiayRepository.existsByTenDeGiay(ten);
    }
}
