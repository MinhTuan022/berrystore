package com.example.berryshoes.controller;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.repository.MauSacRepository;
import com.example.berryshoes.service.MauSacService;
import com.example.berryshoes.dto.request.MauSacRequest;
import com.example.berryshoes.dto.response.MauSacResponse;
import com.example.berryshoes.entity.MauSac;
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
@RequestMapping("/api/mau-sac")
@RequiredArgsConstructor
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private MauSacRepository mauSacRepository;

    // Lấy tất cả màu sắc
    @GetMapping
    public ResponseEntity<List<MauSacResponse>> getAllMauSac() {
        List<MauSac> mauSacList = mauSacService.getAllMauSac();
        List<MauSacResponse> responseList = mauSacList.stream().map(ms -> {
            MauSacResponse response = new MauSacResponse();
            response.setId(ms.getId());
            response.setMaMauSac(ms.getMaMauSac());
            response.setTenMauSac(ms.getTenMauSac());
            response.setNgayTao(ms.getNgayTao());
            response.setNguoiTao(ms.getNguoiTao());
            response.setLanCapNhatCuoi(ms.getLanCapNhatCuoi());
            response.setNguoiCapNhat(ms.getNguoiCapNhat());
            response.setTrangThai(ms.getTrangThai());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<MauSac> page = null;
        if(trangthai == null){
            page = mauSacRepository.findAll(pageable);
        }
        else{
            page = mauSacRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Lấy màu sắc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<MauSacResponse> getMauSacById(@PathVariable Integer id) {
        Optional<MauSac> mauSac = mauSacService.getMauSacById(id);
        return mauSac.map(ms -> {
            MauSacResponse response = new MauSacResponse();
            response.setId(ms.getId());
            response.setMaMauSac(ms.getMaMauSac());
            response.setTenMauSac(ms.getTenMauSac());
            response.setNgayTao(ms.getNgayTao());
            response.setNguoiTao(ms.getNguoiTao());
            response.setLanCapNhatCuoi(ms.getLanCapNhatCuoi());
            response.setNguoiCapNhat(ms.getNguoiCapNhat());
            response.setTrangThai(ms.getTrangThai());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới màu sắc
    @PostMapping
    public ResponseEntity<MauSacResponse> createMauSac(@RequestBody MauSacRequest requestDTO) {
        MauSac createdMauSac = mauSacService.createMauSac(requestDTO);
        MauSacResponse response = new MauSacResponse();
        response.setId(createdMauSac.getId());
        response.setMaMauSac(createdMauSac.getMaMauSac());
        response.setTenMauSac(createdMauSac.getTenMauSac());
        response.setNgayTao(createdMauSac.getNgayTao());
        response.setNguoiTao(createdMauSac.getNguoiTao());
        response.setLanCapNhatCuoi(createdMauSac.getLanCapNhatCuoi());
        response.setNguoiCapNhat(createdMauSac.getNguoiCapNhat());
        response.setTrangThai(createdMauSac.getTrangThai());
        return ResponseEntity.ok(response);
    }

    // Cập nhật màu sắc
    @PostMapping("/{id}")
    public ResponseEntity<MauSacResponse> updateMauSac(@PathVariable Integer id, @RequestBody MauSacRequest requestDTO) {
        MauSac updatedMauSac = mauSacService.updateMauSac(id, requestDTO);
        return updatedMauSac != null ? ResponseEntity.ok(convertToResponse(updatedMauSac)) : ResponseEntity.notFound().build();
    }

    // Xóa màu sắc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMauSac(@PathVariable Integer id) {
        mauSacService.deleteMauSac(id);
        return ResponseEntity.noContent().build();
    }

    private MauSacResponse convertToResponse(MauSac mauSac) {
        MauSacResponse response = new MauSacResponse();
        response.setId(mauSac.getId());
        response.setMaMauSac(mauSac.getMaMauSac());
        response.setTenMauSac(mauSac.getTenMauSac());
        response.setNgayTao(mauSac.getNgayTao());
        response.setNguoiTao(mauSac.getNguoiTao());
        response.setLanCapNhatCuoi(mauSac.getLanCapNhatCuoi());
        response.setNguoiCapNhat(mauSac.getNguoiCapNhat());
        response.setTrangThai(mauSac.getTrangThai());
        return response;
    }
    //    //Thay doi trang thai
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> changeMauSac(@PathVariable Integer id) {
//        mauSacService.deleteByTrangThai(id);
//        return ResponseEntity.noContent().build();
//    }
    @GetMapping("/search")
    public ResponseEntity<List<MauSac>> searchMauSac(@RequestParam String ten, @RequestParam(required = false) Integer trangThai) {
        List<MauSac> mauSacList = mauSacService.findByTenAndTrangThai(ten, trangThai);
        return ResponseEntity.ok(mauSacList);
    }
}
