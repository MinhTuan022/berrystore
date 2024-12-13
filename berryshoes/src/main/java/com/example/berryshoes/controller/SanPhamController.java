package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.SearchDto;
import com.example.berryshoes.dto.response.SanPhamSpecification;
import com.example.berryshoes.exception.MessageException;
import com.example.berryshoes.repository.SanPhamRepository;
import com.example.berryshoes.service.SanPhamService;
import com.example.berryshoes.dto.request.SanPhamRequest;
import com.example.berryshoes.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/san-pham")
@RequiredArgsConstructor
public class SanPhamController {
    private final SanPhamService sanPhamService;
    private final SanPhamRepository sanPhamRepository;
    @GetMapping
    public ResponseEntity<List<SanPham>> getAllSanPham() {
        List<SanPham> sanPhamList = sanPhamService.getAll();
        return ResponseEntity.ok(sanPhamList);
    }

    @GetMapping("/san-pham-lien-quan")
    public ResponseEntity<List<SanPham>> getAllSanPhamLienQuan(@RequestParam Integer id) {
        Optional<SanPham> sanPham = sanPhamRepository.findById(id);
        if(sanPham.isEmpty()){
            throw new MessageException("not found");
        }
        List<SanPham> sanPhamList = new ArrayList<>();
        if(sanPham.get().getThuongHieu() != null){
            sanPhamList = sanPhamRepository.findByThuongHieu(sanPham.get().getThuongHieu().getId());
        }
        return ResponseEntity.ok(sanPhamList);
    }

    @GetMapping("/public/all")
    public ResponseEntity<?> getAllSanPhamPage(Pageable pageable) {
        Page<SanPham> sanPhamList = sanPhamRepository.findAll(pageable);
        return ResponseEntity.ok(sanPhamList);
    }

    @PostMapping("/public/loc-san-pham")
    public ResponseEntity<?> locSanPham(Pageable pageable, @RequestBody SearchDto searchDto) {
        SanPhamSpecification sanPhamSpecification = new SanPhamSpecification(searchDto.getIdThuongHieu() != null ? searchDto.getIdThuongHieu() : null, searchDto.getSmall(), searchDto.getLarge(), searchDto.getIdDeGiay(), searchDto.getIdChatLieu());
        Page<SanPham> sanPhamList = sanPhamRepository.findAll(sanPhamSpecification, pageable);
        return ResponseEntity.ok(sanPhamList);
    }

    @PostMapping("/public/loc-san-pham-list")
    public ResponseEntity<?> locSanPham(@RequestBody SearchDto searchDto) {
        SanPhamSpecification sanPhamSpecification = new SanPhamSpecification(searchDto.getIdThuongHieu(), searchDto.getSmall(), searchDto.getLarge(), searchDto.getIdDeGiay(), searchDto.getIdChatLieu());
        List<SanPham> sanPhamList = sanPhamRepository.findAll(sanPhamSpecification);
        return ResponseEntity.ok(sanPhamList);
    }

    @GetMapping("/public/tim-theo-ten")
    public ResponseEntity<?> locSanPham(Pageable pageable, @RequestParam String search) {
        Page<SanPham> sanPhamList = sanPhamRepository.findByParam("%"+search+"%", pageable);
        return ResponseEntity.ok(sanPhamList);
    }

    @GetMapping("/public/tim-theo-thuong-hieu")
    public ResponseEntity<?> timTheoThuongHieu(Pageable pageable, @RequestParam Integer thuongHieu) {
        Page<SanPham> sanPhamList = sanPhamRepository.findByThuongHieu(thuongHieu, pageable);
        return ResponseEntity.ok(sanPhamList);
    }


    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SanPham> getSanPhamById(@PathVariable Integer id) {
        Optional<SanPham> sanPham = sanPhamService.getById(id);
        System.out.println("id sp: "+sanPham.get().getId());
        return sanPham.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới sản phẩm
    @PostMapping
    public ResponseEntity<SanPham> createSanPham(@RequestBody SanPhamRequest requestDTO) {
        SanPham createdSanPham = sanPhamService.create(requestDTO);
        return ResponseEntity.ok(createdSanPham);
    }

    // Cập nhật sản phẩm
    @PostMapping("/{id}")
    public ResponseEntity<SanPham> updateSanPham(@PathVariable Integer id, @RequestBody SanPhamRequest requestDTO) {
        SanPham updatedSanPham = sanPhamService.update(id, requestDTO);
        return updatedSanPham != null ? ResponseEntity.ok(updatedSanPham) : ResponseEntity.notFound().build();
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
