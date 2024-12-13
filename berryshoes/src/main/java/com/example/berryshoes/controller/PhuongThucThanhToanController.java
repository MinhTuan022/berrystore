package com.example.berryshoes.controller;

import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.service.PhuongThucThanhToanService;
import com.example.berryshoes.dto.request.PhuongThucThanhToanRequest;
import com.example.berryshoes.entity.PhuongThucThanhToan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phuong-thuc-thanh-toan")
@RequiredArgsConstructor
public class PhuongThucThanhToanController {
    private final PhuongThucThanhToanService phuongThucThanhToanService;

    @GetMapping
    public ResponseEntity<List<PhuongThucThanhToan>> getAllPhuongThucThanhToan() {
        List<PhuongThucThanhToan> phuongThucThanhToanList = phuongThucThanhToanService.getAll();
        return ResponseEntity.ok(phuongThucThanhToanList);
    }

    // Lấy phương thức thanh toán theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucThanhToan> getPhuongThucThanhToanById(@PathVariable Integer id) {
        Optional<PhuongThucThanhToan> phuongThucThanhToan = phuongThucThanhToanService.getById(id);
        return phuongThucThanhToan.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới phương thức thanh toán
    @PostMapping
    public ResponseEntity<PhuongThucThanhToan> createPhuongThucThanhToan(@RequestBody PhuongThucThanhToanRequest requestDTO) {
        PhuongThucThanhToan createdPhuongThucThanhToan = phuongThucThanhToanService.create(requestDTO);
        return ResponseEntity.ok(createdPhuongThucThanhToan);
    }

    // Cập nhật phương thức thanh toán
    @PutMapping("/{id}")
    public ResponseEntity<PhuongThucThanhToan> updatePhuongThucThanhToan(@PathVariable Integer id, @RequestBody PhuongThucThanhToanRequest requestDTO) {
        PhuongThucThanhToan updatedPhuongThucThanhToan = phuongThucThanhToanService.update(id, requestDTO);
        return updatedPhuongThucThanhToan != null ? ResponseEntity.ok(updatedPhuongThucThanhToan) : ResponseEntity.notFound().build();
    }

    // Xóa phương thức thanh toán
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucThanhToan(@PathVariable Integer id) {
        phuongThucThanhToanService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/hoa-don/{id}")
    public PhuongThucThanhToan getByIdHoaDon(@PathVariable Integer id) {
        return phuongThucThanhToanService.getByIdHoaDon(id);
    }

    @GetMapping("/hoa-don")
    public List<PhuongThucThanhToan> getAllByHoaDon(@RequestBody HoaDon hd) {
        return phuongThucThanhToanService.getAllByHoaDon(hd);
    }
}
