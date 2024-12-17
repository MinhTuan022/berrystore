package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.SanPhamChiTietRequest;
import com.example.berryshoes.dto.request.SanPhamChiTietUpdateRequest;
import com.example.berryshoes.entity.SanPhamChiTiet;
import com.example.berryshoes.repository.KichCoRepository;
import com.example.berryshoes.repository.MauSacRepository;
import com.example.berryshoes.repository.SanPhamChiTietRepository;
import com.example.berryshoes.repository.SanPhamRepository;
import com.example.berryshoes.service.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final SanPhamRepository sanPhamRepository;
    private final KichCoRepository kichCoRepository;
    private final MauSacRepository mauSacRepository;

    // Lấy tất cả chi tiết sản phẩm
    public List<SanPhamChiTiet> getAll() {
        return sanPhamChiTietRepository.findAll().stream().filter(i -> i.getTrangThai() == 1).toList();
    }

    // Lấy chi tiết sản phẩm theo ID
    public Optional<SanPhamChiTiet> getById(Integer id) {
        return sanPhamChiTietRepository.findById(id);
    }

    // Tạo mới chi tiết sản phẩm
    public SanPhamChiTiet create(SanPhamChiTietRequest requestDTO) {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        sanPhamChiTiet.setMaSanPhamChiTiet(requestDTO.getMaSanPhamChiTiet());
        sanPhamChiTiet.setQrCode(requestDTO.getQrCode());
        sanPhamChiTiet.setSoLuong(requestDTO.getSoLuong());
        sanPhamChiTiet.setGiaTien(requestDTO.getGiaTien());
        sanPhamChiTiet.setMoTa(requestDTO.getMoTa());

        // Thiết lập mối quan hệ với các thực thể khác
        sanPhamChiTiet.setSanPham(sanPhamRepository.findById(requestDTO.getIdSanPham()).orElse(null));

        // Nếu muốn chỉ lấy 1 kích cỡ thì sử dụng idKichCo.get(0), nếu muốn sử dụng toàn bộ danh sách kích cỡ thì cần xử lý theo logic của bạn.
        if (requestDTO.getIdKichCo() != null && !requestDTO.getIdKichCo().isEmpty()) {
            // Ví dụ chỉ lấy kích cỡ đầu tiên trong danh sách
            sanPhamChiTiet.setKichCo(kichCoRepository.findById(requestDTO.getIdKichCo().get(0)).orElse(null));
        }

        sanPhamChiTiet.setMauSac(mauSacRepository.findById(requestDTO.getIdMauSac()).orElse(null));
        // sanPhamChiTiet.setDotGiamGia(dotGiamGiaRepository.findById(requestDTO.getIdDotGiamGia()).orElse(null));
        sanPhamChiTiet.setNguoiTao(requestDTO.getNguoiTao());
        sanPhamChiTiet.setTrangThai(requestDTO.getTrangThai());

        return sanPhamChiTietRepository.save(sanPhamChiTiet);
    }


    public void update_quality_detail_product(Integer id, Integer quality, Integer quality_new) {
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = sanPhamChiTietRepository.findById(id);
        if (optionalSanPhamChiTiet.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
            sanPhamChiTiet.setSoLuong(Math.max((sanPhamChiTiet.getSoLuong() - quality) - quality_new, 0));
            if (sanPhamChiTiet.getSoLuong() <= 0) {
                sanPhamChiTiet.setTrangThai(2);
            }
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        }
    }

    // Cập nhật chi tiết sản phẩm
    public SanPhamChiTiet update(Integer id, SanPhamChiTietUpdateRequest requestDTO) {
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = sanPhamChiTietRepository.findById(id);
        if (optionalSanPhamChiTiet.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
            sanPhamChiTiet.setMaSanPhamChiTiet(requestDTO.getMaSanPhamChiTiet());
            sanPhamChiTiet.setQrCode(requestDTO.getQrCode());
            sanPhamChiTiet.setSoLuong(requestDTO.getSoLuong());
            sanPhamChiTiet.setGiaTien(requestDTO.getGiaTien());
            sanPhamChiTiet.setMoTa(requestDTO.getMoTa());
            // Thiết lập lại mối quan hệ
            sanPhamChiTiet.setSanPham(sanPhamRepository.findById(requestDTO.getIdSanPham()).orElse(null));
            sanPhamChiTiet.setKichCo(kichCoRepository.findById(requestDTO.getIdKichCo()).orElse(null));
            sanPhamChiTiet.setMauSac(mauSacRepository.findById(requestDTO.getIdMauSac()).orElse(null));
//             sanPhamChiTiet.setDotGiamGia(dotGiamGiaRepository.findById(requestDTO.getIdDotGiamGia()).orElse(null));
            sanPhamChiTiet.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            sanPhamChiTiet.setTrangThai(requestDTO.getTrangThai());

            return sanPhamChiTietRepository.save(sanPhamChiTiet);
        }
        return null;
    }



    // Xóa chi tiết sản phẩm
    public void delete(Integer id) {
        if (sanPhamChiTietRepository.existsById(id)) {
            sanPhamChiTietRepository.deleteById(id);
        } else {
            throw new RuntimeException("Chi tiết sản phẩm không tồn tại");
        }
    }
}
