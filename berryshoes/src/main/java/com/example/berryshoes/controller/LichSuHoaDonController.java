package com.example.berryshoes.controller;

import com.example.berryshoes.entity.HoaDonChiTiet;
import com.example.berryshoes.entity.LichSuHoaDon;
import com.example.berryshoes.repository.HoaDonChiTietRepository;
import com.example.berryshoes.repository.LichSuHoaDonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lich-su-hoa-don")
@RequiredArgsConstructor
public class LichSuHoaDonController {

    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;

    @GetMapping("/find-by-hoa-don")
    public ResponseEntity<?> findByHoaDon(@RequestParam Integer hoaDonId){
        List<LichSuHoaDon> list = lichSuHoaDonRepository.findLichSuHoaDonByIdHoaDon(hoaDonId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
