package com.example.berryshoes.controller;

import com.example.berryshoes.dto.request.AnhRequest;
import com.example.berryshoes.dto.response.AnhResponse;
import com.example.berryshoes.entity.Anh;
import com.example.berryshoes.entity.SanPhamChiTiet;
import com.example.berryshoes.exception.MessageException;
import com.example.berryshoes.repository.AnhRepository;
import com.example.berryshoes.repository.SanPhamChiTietRepository;
import com.example.berryshoes.service.AnhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anh")
@RequiredArgsConstructor
public class AnhController {

    private final AnhService anhService;
    private final AnhRepository anhRepository;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    // Lấy tất cả ảnh
    @GetMapping
    public ResponseEntity<List<AnhResponse>> getAllAnh() {
        List<Anh> anhList = anhService.getAll();
        List<AnhResponse> responseList = anhList.stream().map(anh -> {
            AnhResponse response = new AnhResponse();
            response.setId(anh.getId());
            response.setTenAnh(anh.getTenAnh());
            response.setIdSanPhamChiTiet(anh.getSanPhamChiTiet().getId()); // Lấy ID của sản phẩm chi tiết
            response.setNgayTao(anh.getNgayTao());
            response.setNguoiTao(anh.getNguoiTao());
            response.setLanCapNhatCuoi(anh.getLanCapNhatCuoi());
            response.setNguoiCapNhat(anh.getNguoiCapNhat());
            response.setTrangThai(anh.getTrangThai());
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("")
    public void addAnh(@RequestBody List<String> listLink, @RequestParam Integer chiTietSanPhamId){
        Optional<SanPhamChiTiet> sanPhamChiTiet = sanPhamChiTietRepository.findById(chiTietSanPhamId);
        if(sanPhamChiTiet.isEmpty()){
            throw new MessageException("Không tồn tại sản phẩm chi tiết");
        }
        for(String s : listLink){
            Anh anh = new Anh();
            anh.setTenAnh(s);
            anh.setSanPhamChiTiet(sanPhamChiTiet.get());
            anh.setNgayTao(new Timestamp(System.currentTimeMillis()));
            anh.setTrangThai(true);
            anhRepository.save(anh);
        }
    }
}