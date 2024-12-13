package com.example.berryshoes.controller;

import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.repository.DeGiayRepository;
import com.example.berryshoes.repository.HoaDonRepository;
import com.example.berryshoes.service.DeGiayService;
import com.example.berryshoes.dto.request.DeGiayRequest;
import com.example.berryshoes.dto.response.DeGiayResponse;
import com.example.berryshoes.entity.DeGiay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/de-giay")
@RequiredArgsConstructor
public class DeGiayController {
    @Autowired
    private DeGiayService deGiayService;
    @Autowired
    private DeGiayRepository deGiayRepository;

    // Kiểm tra tính hợp lệ của tên đế giày
    public void validateDeGiay(DeGiayRequest requestDTO) {
        if (requestDTO.getTenDeGiay() == null || requestDTO.getTenDeGiay().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đế giày không được trống");
        }

        if (requestDTO.getTenDeGiay().length() > 255) {
            throw new IllegalArgumentException("Tên đế giày quá dài");
        }

        // Kiểm tra trùng lặp (nếu cần)
        if (deGiayService.existsByTenDeGiay(requestDTO.getTenDeGiay())) {
            throw new IllegalArgumentException("Tên đế giày đã tồn tại");
        }
    }
    // Lấy tất cả đế giày
    @GetMapping
    public ResponseEntity<List<DeGiayResponse>> getAllDeGiay() {
        List<DeGiay> deGiayList = deGiayService.getAllDeGiay();
        List<DeGiayResponse> responseList = deGiayList.stream().map(deGiay -> {
            DeGiayResponse response = new DeGiayResponse();
            response.setId(deGiay.getId());
            response.setTenDeGiay(deGiay.getTenDeGiay());
            response.setNgayTao(deGiay.getNgayTao());
            response.setNguoiTao(deGiay.getNguoiTao());
            response.setLanCapNhatCuoi(deGiay.getLanCapNhatCuoi());
            response.setNguoiCapNhat(deGiay.getNguoiCapNhat());
            response.setTrangThai(deGiay.getTrangThai());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<DeGiay> page = null;
        if(trangthai == null){
            page = deGiayRepository.findAll(pageable);
        }
        else{
            page = deGiayRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Lấy đế giày theo ID
    @GetMapping("/{id}")
    public ResponseEntity<DeGiayResponse> getDeGiayById(@PathVariable Integer id) {
        Optional<DeGiay> deGiay = deGiayService.getDeGiayById(id);
        return deGiay.map(dg -> {
            DeGiayResponse response = new DeGiayResponse();
            response.setId(dg.getId());
            response.setTenDeGiay(dg.getTenDeGiay());
            response.setNgayTao(dg.getNgayTao());
            response.setNguoiTao(dg.getNguoiTao());
            response.setLanCapNhatCuoi(dg.getLanCapNhatCuoi());
            response.setNguoiCapNhat(dg.getNguoiCapNhat());
            response.setTrangThai(dg.getTrangThai());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới đế giày
    @PostMapping
    public ResponseEntity<DeGiayResponse> createDeGiay(@RequestBody DeGiayRequest requestDTO) {
        validateDeGiay(requestDTO);
        DeGiay createdDeGiay = deGiayService.createDeGiay(requestDTO);
        DeGiayResponse response = new DeGiayResponse();
        response.setId(createdDeGiay.getId());
        response.setTenDeGiay(createdDeGiay.getTenDeGiay());
        response.setNgayTao(createdDeGiay.getNgayTao());
        response.setNguoiTao(createdDeGiay.getNguoiTao());
        response.setLanCapNhatCuoi(createdDeGiay.getLanCapNhatCuoi());
        response.setNguoiCapNhat(createdDeGiay.getNguoiCapNhat());
        response.setTrangThai(createdDeGiay.getTrangThai());
        return ResponseEntity.ok(response);
    }

    // Cập nhật đế giày
    @PostMapping("/{id}")
    public ResponseEntity<DeGiayResponse> updateDeGiay(@PathVariable Integer id, @RequestBody DeGiayRequest requestDTO) {
        if (requestDTO.getTenDeGiay() == null || requestDTO.getTenDeGiay().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đế giày không được trống");
        }

        if (requestDTO.getTenDeGiay().length() > 255) {
            throw new IllegalArgumentException("Tên đế giày quá dài");
        }
        DeGiay updatedDeGiay = deGiayService.updateDeGiay(id, requestDTO);
        return updatedDeGiay != null ? ResponseEntity.ok(convertToResponse(updatedDeGiay)) : ResponseEntity.notFound().build();
    }

    // Xóa đế giày
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeGiay(@PathVariable Integer id) {
        deGiayService.deleteDeGiay(id);
        return ResponseEntity.noContent().build();
    }

    private DeGiayResponse convertToResponse(DeGiay deGiay) {
        DeGiayResponse response = new DeGiayResponse();
        response.setId(deGiay.getId());
        response.setTenDeGiay(deGiay.getTenDeGiay());
        response.setNgayTao(deGiay.getNgayTao());
        response.setNguoiTao(deGiay.getNguoiTao());
        response.setLanCapNhatCuoi(deGiay.getLanCapNhatCuoi());
        response.setNguoiCapNhat(deGiay.getNguoiCapNhat());
        response.setTrangThai(deGiay.getTrangThai());
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeGiay>> searchDeGiay(@RequestParam String ten, @RequestParam(required = false) Integer trangThai) {
        List<DeGiay> deGiays = deGiayService.findByTenAndTrangThai(ten, trangThai);
        return ResponseEntity.ok(deGiays);
    }

    @PutMapping("/{id}/changeTrangThai")
    public ResponseEntity<Void> deactivateDeGiay(@PathVariable Integer id) {
        deGiayService.updateTrangThaiToFalseById(id);
        return ResponseEntity.noContent().build();
    }
}
