package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.MauSacRequest;
import com.example.berryshoes.entity.MauSac;
import com.example.berryshoes.repository.MauSacRepository;
import com.example.berryshoes.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MauSacServiceImpl implements MauSacService {
    private final MauSacRepository mauSacRepository;

    @Override
    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    @Override
    public Optional<MauSac> getMauSacById(Integer id) {
        return mauSacRepository.findById(id);
    }

    @Override
    public Optional<MauSac> getById(Integer id) {
        return mauSacRepository.findById(id);
    }

    @Override
    public MauSac createMauSac(MauSacRequest requestDTO) {
        MauSac mauSac = MauSac.builder()
                .maMauSac(requestDTO.getMaMauSac())
                .tenMauSac(requestDTO.getTenMauSac())
                .nguoiTao(requestDTO.getNguoiTao())
                .trangThai(requestDTO.getTrangThai())
                .build();
        return mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac updateMauSac(Integer id, MauSacRequest requestDTO) {
        Optional<MauSac> optionalMauSac = mauSacRepository.findById(id);
        if (optionalMauSac.isPresent()) {
            MauSac mauSac = optionalMauSac.get();
            mauSac.setMaMauSac(requestDTO.getMaMauSac());
            mauSac.setTenMauSac(requestDTO.getTenMauSac());
            mauSac.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            mauSac.setTrangThai(requestDTO.getTrangThai());
            return mauSacRepository.save(mauSac);
        }
        return null;
    }

    @Override
    public void deleteMauSac(Integer id) {
        if (mauSacRepository.existsById(id)) {
            mauSacRepository.deleteById(id);
        } else {
            throw new RuntimeException("Màu sắc không tồn tại");
        }
    }
    @Override
    public void deleteByTrangThai(Integer id) {
        mauSacRepository.updateTrangThaiToFalseById(id); // Giả sử không xóa vĩnh viễn mà chỉ cập nhật trạng thái
    }
    @Override
    public List<MauSac> findByTenAndTrangThai(String ten, Integer trangThai) {
        return mauSacRepository.findByTenAndTrangThai(ten, trangThai);
    }
}
