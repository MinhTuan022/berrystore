package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.SanPhamRequest;
import com.example.berryshoes.entity.SanPham;
import com.example.berryshoes.repository.ChatLieuRepository;
import com.example.berryshoes.repository.DeGiayRepository;
import com.example.berryshoes.repository.SanPhamRepository;
import com.example.berryshoes.repository.ThuongHieuRepository;
import com.example.berryshoes.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final ThuongHieuRepository thuongHieuRepository;
    private final ChatLieuRepository chatLieuRepository;
    private final DeGiayRepository deGiayRepository;

    // Lấy tất cả sản phẩm
    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<SanPham> getById(Integer id) {
        return sanPhamRepository.findById(id);
    }

    // Tạo mới sản phẩm
    public SanPham create(SanPhamRequest requestDTO) {
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham(requestDTO.getMaSanPham());
        sanPham.setTenSanPham(requestDTO.getTenSanPham());
        // Thiết lập mối quan hệ với các thực thể khác
         sanPham.setThuongHieu(thuongHieuRepository.findById(requestDTO.getIdThuongHieu()).orElse(null));
         sanPham.setChatLieu(chatLieuRepository.findById(requestDTO.getIdChatLieu()).orElse(null));
         sanPham.setDeGiay(deGiayRepository.findById(requestDTO.getIdDeGiay()).orElse(null));
        sanPham.setNguoiTao(requestDTO.getNguoiTao());
        sanPham.setTrangThai(requestDTO.getTrangThai());

        return sanPhamRepository.save(sanPham);
    }

    // Cập nhật sản phẩm
    public SanPham update(Integer id, SanPhamRequest requestDTO) {
        Optional<SanPham> optionalSanPham = sanPhamRepository.findById(id);
        if (optionalSanPham.isPresent()) {
            SanPham sanPham = optionalSanPham.get();
            sanPham.setMaSanPham(requestDTO.getMaSanPham());
            sanPham.setTenSanPham(requestDTO.getTenSanPham());
            // Thiết lập lại mối quan hệ
             sanPham.setThuongHieu(thuongHieuRepository.findById(requestDTO.getIdThuongHieu()).orElse(null));
             sanPham.setChatLieu(chatLieuRepository.findById(requestDTO.getIdChatLieu()).orElse(null));
             sanPham.setDeGiay(deGiayRepository.findById(requestDTO.getIdDeGiay()).orElse(null));
            sanPham.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            sanPham.setTrangThai(requestDTO.getTrangThai());
            return sanPhamRepository.save(sanPham);
        }
        return null;
    }

    // Xóa sản phẩm
    public void delete(Integer id) {
        if (sanPhamRepository.existsById(id)) {
            sanPhamRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
    }
}
