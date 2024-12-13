package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.ChiTietSpSearch;
import com.example.berryshoes.dto.request.SearchDto;
import com.example.berryshoes.dto.response.SanPhamChiTietSpecification;
import com.example.berryshoes.dto.response.SanPhamSpecification;
import com.example.berryshoes.entity.SanPham;
import com.example.berryshoes.repository.SanPhamChiTietRepository;
import com.example.berryshoes.repository.SanPhamRepository;
import com.example.berryshoes.service.SanPhamChiTietService;
import com.example.berryshoes.dto.request.SanPhamChiTietRequest;
import com.example.berryshoes.entity.SanPhamChiTiet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
@RequiredArgsConstructor
public class SanPhamChiTietController {

    private final SanPhamChiTietService sanPhamChiTietService;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final SanPhamRepository sanPhamRepository;

    // Lấy tất cả chi tiết sản phẩm
    @GetMapping
    public ResponseEntity<List<SanPhamChiTiet>> getAllSanPhamChiTiet() {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietService.getAll();
        return ResponseEntity.ok(sanPhamChiTietList);
    }

    @GetMapping("/findBySanPham")
    public ResponseEntity<List<SanPhamChiTiet>> findBySanPham(@RequestParam Integer sanpham) {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamRepository.findBySanPham(sanpham);
        return ResponseEntity.ok(sanPhamChiTietList);
    }

    // Lấy chi tiết sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamChiTiet> getSanPhamChiTietById(@PathVariable Integer id) {
        Optional<SanPhamChiTiet> sanPhamChiTiet = sanPhamChiTietService.getById(id);
        return sanPhamChiTiet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới chi tiết sản phẩm
    @PostMapping
    public ResponseEntity<SanPhamChiTiet> createSanPhamChiTiet(@RequestBody SanPhamChiTietRequest requestDTO) {
        SanPhamChiTiet createdSanPhamChiTiet = sanPhamChiTietService.create(requestDTO);
        return ResponseEntity.ok(createdSanPhamChiTiet);
    }

    // Cập nhật chi tiết sản phẩm
    @PostMapping("/{id}")
    public ResponseEntity<SanPhamChiTiet> updateSanPhamChiTiet(@PathVariable Integer id, @RequestBody SanPhamChiTietRequest requestDTO) {
        SanPhamChiTiet updatedSanPhamChiTiet = sanPhamChiTietService.update(id, requestDTO);
        return updatedSanPhamChiTiet != null ? ResponseEntity.ok(updatedSanPhamChiTiet) : ResponseEntity.notFound().build();
    }

    // Xóa chi tiết sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPhamChiTiet(@PathVariable Integer id) {
        sanPhamChiTietService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/public/loc-chi-tiet-san-pham-list")
    public ResponseEntity<?> locSanPhamChiTiet(@RequestBody ChiTietSpSearch search) {
        SanPhamChiTietSpecification sp = new SanPhamChiTietSpecification(search.getThuongHieuId(), search.getChatLieuId(), search.getDeGiayId(), search.getMauSacId(), search.getKichThuocId());
        List<SanPhamChiTiet> sanPhamList = sanPhamChiTietRepository.findAll(sp);
        return ResponseEntity.ok(sanPhamList.stream().filter(i -> i.getTrangThai() == 1));
    }
}
