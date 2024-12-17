package com.example.berryshoes.controller;

import com.example.berryshoes.dto.response.TrangThai;
import com.example.berryshoes.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeController {

    @Autowired
    private HoaDonRepository hoaDonRepository;


    @GetMapping("/admin/thong-ke-tong-quat")
    public ResponseEntity<?> thongKeTongQuatAdmin(){
        Map<String, String> map = new HashMap<>();

        Long soDonTrongNgay = hoaDonRepository.soDonHomNay(new Date(System.currentTimeMillis()));
        map.put("soDonTrongNgay", soDonTrongNgay.toString());

        Long soDonTuanNay = hoaDonRepository.soDonTuanNay();
        map.put("soDonTuanNay", soDonTuanNay.toString());

        Long soDonThangNay = hoaDonRepository.soDonThangNay();
        map.put("soDonThangNay", soDonThangNay.toString());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/admin/thong-ke-trang-thai")
    public ResponseEntity<?> thongKeTheoTrangThai(){
        List<TrangThai> list = TrangThai.initTrangThais();
        Map<String, Long> map = new HashMap<>();
        for(TrangThai t : list){
            Long count = hoaDonRepository.demHoaDonTrangThai(t.getValue());
            map.put(t.getTenTrangThai(), count);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/admin/doanhthuall")
    public List<Double> doanhthuall(@RequestParam("nam") Integer nam){
        List<Double> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Double tong = hoaDonRepository.tinhDoanhThuAdmin(i, nam);
            if (tong == null){
                tong = 0D;
            }
            list.add(tong);
        }
        return list;
    }

    @GetMapping("/admin/doanhthungay")
    public List<Object[]> getDoanhThuTheoGioTrongNgay() {
        return hoaDonRepository.tinhDoanhThuTheoGioTrongNgay();
    }

    @GetMapping("/admin/doanhthuthutrongtuan")
    public List<Object[]> getDoanhThuTheoThuTrongTuan() {
        return hoaDonRepository.tinhDoanhThuTheoThuTrongTuan();
    }

}
