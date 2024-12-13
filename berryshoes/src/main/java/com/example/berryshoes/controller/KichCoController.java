package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.DeGiayRequest;
import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.repository.KichCoRepository;
import com.example.berryshoes.service.KichCoService;
import com.example.berryshoes.dto.request.KichCoRequest;
import com.example.berryshoes.dto.response.KichCoResponse;
import com.example.berryshoes.entity.KichCo;
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
@RequestMapping("/api/kich-co")
@RequiredArgsConstructor
public class KichCoController {
    @Autowired
    private KichCoService kichCoService;
    @Autowired
    private KichCoRepository kichCoRepository;

    public void validateKichCo(KichCoRequest requestDTO) {
        if (requestDTO.getTenKichCo() == null || requestDTO.getTenKichCo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đế giày không được trống");
        }

        try {
            // Chuyển đổi chuỗi sang số nguyên
            int kichCo = Integer.parseInt(requestDTO.getTenKichCo().trim());

            // Kiểm tra phạm vi hợp lệ
            if (kichCo < 35 || kichCo > 45) {
                throw new IllegalArgumentException("Kích cỡ phải nằm trong khoảng từ 35 đến 45");
            }
        } catch (NumberFormatException e) {
            // Xử lý trường hợp không phải là số nguyên
            throw new IllegalArgumentException("Kích cỡ phải là số nguyên");
        }

        // Kiểm tra trùng lặp (nếu cần)
        if (kichCoService.existsByTenKichCo(requestDTO.getTenKichCo())) {
            throw new IllegalArgumentException("Tên kích cỡ đã tồn tại");
        }
    }
    // Lấy tất cả kích cỡ
    @GetMapping
    public ResponseEntity<List<KichCoResponse>> getAllKichCo() {
        List<KichCo> kichCoList = kichCoService.getAllKichCo();
        List<KichCoResponse> responseList = kichCoList.stream().map(kc -> {
            KichCoResponse response = new KichCoResponse();
            response.setId(kc.getId());
            response.setTenKichCo(kc.getTenKichCo());
            response.setNgayTao(kc.getNgayTao());
            response.setNguoiTao(kc.getNguoiTao());
            response.setLanCapNhatCuoi(kc.getLanCapNhatCuoi());
            response.setNguoiCapNhat(kc.getNguoiCapNhat());
            response.setTrangThai(kc.getTrangThai());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    // Lấy kích cỡ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<KichCoResponse> getKichCoById(@PathVariable Integer id) {
        Optional<KichCo> kichCo = kichCoService.getKichCoById(id);
        return kichCo.map(kc -> {
            KichCoResponse response = new KichCoResponse();
            response.setId(kc.getId());
            response.setTenKichCo(kc.getTenKichCo());
            response.setNgayTao(kc.getNgayTao());
            response.setNguoiTao(kc.getNguoiTao());
            response.setLanCapNhatCuoi(kc.getLanCapNhatCuoi());
            response.setNguoiCapNhat(kc.getNguoiCapNhat());
            response.setTrangThai(kc.getTrangThai());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<KichCo> page = null;
        if(trangthai == null){
            page = kichCoRepository.findAll(pageable);
        }
        else{
            page = kichCoRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    // Tạo mới kích cỡ
    @PostMapping
    public ResponseEntity<KichCoResponse> createKichCo(@RequestBody KichCoRequest requestDTO) {
        validateKichCo(requestDTO);
        KichCo createdKichCo = kichCoService.createKichCo(requestDTO);
        KichCoResponse response = new KichCoResponse();
        response.setId(createdKichCo.getId());
        response.setTenKichCo(createdKichCo.getTenKichCo());
        response.setNgayTao(createdKichCo.getNgayTao());
        response.setNguoiTao(createdKichCo.getNguoiTao());
        response.setLanCapNhatCuoi(createdKichCo.getLanCapNhatCuoi());
        response.setNguoiCapNhat(createdKichCo.getNguoiCapNhat());
        response.setTrangThai(createdKichCo.getTrangThai());
        return ResponseEntity.ok(response);
    }

    // Cập nhật kích cỡ
    @PostMapping("/{id}")
    public ResponseEntity<KichCoResponse> updateKichCo(@PathVariable Integer id, @RequestBody KichCoRequest requestDTO) {
        KichCo updatedKichCo = kichCoService.updateKichCo(id, requestDTO);
        return updatedKichCo != null ? ResponseEntity.ok(convertToResponse(updatedKichCo)) : ResponseEntity.notFound().build();
    }

    // Xóa kích cỡ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKichCo(@PathVariable Integer id) {
        kichCoService.deleteKichCo(id);
        return ResponseEntity.noContent().build();
    }

    private KichCoResponse convertToResponse(KichCo kichCo) {
        KichCoResponse response = new KichCoResponse();
        response.setId(kichCo.getId());
        response.setTenKichCo(kichCo.getTenKichCo());
        response.setNgayTao(kichCo.getNgayTao());
        response.setNguoiTao(kichCo.getNguoiTao());
        response.setLanCapNhatCuoi(kichCo.getLanCapNhatCuoi());
        response.setNguoiCapNhat(kichCo.getNguoiCapNhat());
        response.setTrangThai(kichCo.getTrangThai());
        return response;
    }
    @GetMapping("/search")
    public List<KichCo> searchKichCo(@RequestParam String ten, @RequestParam(required = false) Integer trangThai) {
        return kichCoService.searchKichCoByTenAndTrangthai(ten, trangThai);
    }
    @PutMapping("/{id}/change")
    public ResponseEntity<Void> changeTrangThai(@PathVariable Integer id) {
        kichCoService.updateTrangThaiToFalseById(id);
        return ResponseEntity.ok().build();
    }
}
