package com.example.berryshoes.controller;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.repository.ThuongHieuRepository;
import com.example.berryshoes.service.ThuongHieuService;
import com.example.berryshoes.dto.request.ThuongHieuRequest;
import com.example.berryshoes.dto.response.ThuongHieuResponse;
import com.example.berryshoes.entity.ThuongHieu;
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
@RequestMapping("/api/thuong-hieu")
@RequiredArgsConstructor
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    // Lấy tất cả thương hiệu
    @GetMapping
    public ResponseEntity<List<ThuongHieuResponse>> getAllThuongHieu() {
        List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
        List<ThuongHieuResponse> responseList = thuongHieuList.stream().map(th -> {
            ThuongHieuResponse response = new ThuongHieuResponse();
            response.setId(th.getId());
            response.setTenThuongHieu(th.getTenThuongHieu());
            response.setNgayTao(th.getNgayTao());
            response.setNguoiTao(th.getNguoiTao());
            response.setLanCapNhatCuoi(th.getLanCapNhatCuoi());
            response.setNguoiCapNhat(th.getNguoiCapNhat());
            response.setTrangThai(th.getTrangThai());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<ThuongHieu> page = null;
        if(trangthai == null){
            page = thuongHieuRepository.findAll(pageable);
        }
        else{
            page = thuongHieuRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Lấy thương hiệu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuResponse> getThuongHieuById(@PathVariable Integer id) {
        Optional<ThuongHieu> thuongHieu = thuongHieuService.getThuongHieuById(id);
        return thuongHieu.map(th -> {
            ThuongHieuResponse response = new ThuongHieuResponse();
            response.setId(th.getId());
            response.setTenThuongHieu(th.getTenThuongHieu());
            response.setNgayTao(th.getNgayTao());
            response.setNguoiTao(th.getNguoiTao());
            response.setLanCapNhatCuoi(th.getLanCapNhatCuoi());
            response.setNguoiCapNhat(th.getNguoiCapNhat());
            response.setTrangThai(th.getTrangThai());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới thương hiệu
    @PostMapping
    public ResponseEntity<ThuongHieuResponse> createThuongHieu(@RequestBody ThuongHieuRequest requestDTO) {
        validateThuongHieu(requestDTO);
        ThuongHieu createdThuongHieu = thuongHieuService.createThuongHieu(requestDTO);
        ThuongHieuResponse response = new ThuongHieuResponse();
        response.setId(createdThuongHieu.getId());
        response.setTenThuongHieu(createdThuongHieu.getTenThuongHieu());
        response.setNgayTao(createdThuongHieu.getNgayTao());
        response.setNguoiTao(createdThuongHieu.getNguoiTao());
        response.setLanCapNhatCuoi(createdThuongHieu.getLanCapNhatCuoi());
        response.setNguoiCapNhat(createdThuongHieu.getNguoiCapNhat());
        response.setTrangThai(createdThuongHieu.getTrangThai());
        return ResponseEntity.ok(response);
    }

    private void validateThuongHieu(ThuongHieuRequest requestDTO) {
        if (requestDTO.getTenThuongHieu() == null || requestDTO.getTenThuongHieu().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thương hiệu không được trống");
        }

        // Kiểm tra độ dài tên chất liệu không quá 255 ký tự
        if (requestDTO.getTenThuongHieu().length() > 255) {
            throw new IllegalArgumentException("Tên thương hiệu quá dài");
        }

        // Kiểm tra trùng lặp tên chất liệu
        if (thuongHieuService.existsByTenThuongHieu(requestDTO.getTenThuongHieu())) {
            throw new IllegalArgumentException("Tên thương hiệu đã tồn tại");
        }
    }

    // Cập nhật thương hiệu
    @PostMapping("/{id}")
    public ResponseEntity<ThuongHieuResponse> updateThuongHieu(@PathVariable Integer id, @RequestBody ThuongHieuRequest requestDTO) {
        ThuongHieu updatedThuongHieu = thuongHieuService.updateThuongHieu(id, requestDTO);
        return updatedThuongHieu != null ? ResponseEntity.ok(convertToResponse(updatedThuongHieu)) : ResponseEntity.notFound().build();
    }

    // Xóa thương hiệu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuongHieu(@PathVariable Integer id) {
        thuongHieuService.deleteThuongHieu(id);
        return ResponseEntity.noContent().build();
    }

    private ThuongHieuResponse convertToResponse(ThuongHieu thuongHieu) {
        ThuongHieuResponse response = new ThuongHieuResponse();
        response.setId(thuongHieu.getId());
        response.setTenThuongHieu(thuongHieu.getTenThuongHieu());
        response.setNgayTao(thuongHieu.getNgayTao());
        response.setNguoiTao(thuongHieu.getNguoiTao());
        response.setLanCapNhatCuoi(thuongHieu.getLanCapNhatCuoi());
        response.setNguoiCapNhat(thuongHieu.getNguoiCapNhat());
        response.setTrangThai(thuongHieu.getTrangThai());
        return response;
    }
    @GetMapping("/search")
    public ResponseEntity<List<ThuongHieu>> searchThuongHieu(@RequestParam String ten, @RequestParam(required = false) Integer trangThai) {
        List<ThuongHieu> thuongHieus = thuongHieuService.getThuongHieuByTenThuongHieuOrTrangThai(ten, trangThai);
        return ResponseEntity.ok(thuongHieus);
    }

    @PutMapping("/{id}/change")
    public ResponseEntity<Void> changeTrangThai(@PathVariable Integer id) {
        thuongHieuService.updateTrangThaiToFalseById(id);
        return ResponseEntity.noContent().build();
    }
}
