package com.example.berryshoes.controller;

import com.example.berryshoes.dto.response.NhanVienResponse;
import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.repository.NhanVienRepository;
import com.example.berryshoes.service.NhanVienService;
import com.example.berryshoes.dto.request.NhanVienRequest;
import com.example.berryshoes.entity.NhanVien;
import com.example.berryshoes.utils.UserUltis;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nhan-vien")
@RequiredArgsConstructor
public class NhanVienController {

    private final NhanVienService nhanVienService;
    @Autowired
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    private UserUltis userUltis;

    // Lấy tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<NhanVien>> getAllNhanVien() {
        List<NhanVien> nhanVienList = nhanVienService.getAllNhanVien();
        return ResponseEntity.ok(nhanVienList);
    }

    // Lấy nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable Integer id) {
        Optional<NhanVien> nhanVien = nhanVienService.getNhanVienById(id);
        return nhanVien.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới nhân viên
    @PostMapping
    public ResponseEntity<NhanVien> createNhanVien(@RequestBody NhanVienRequest requestDTO) {
        NhanVien createdNhanVien = nhanVienService.createNhanVien(requestDTO);
        return ResponseEntity.ok(createdNhanVien);
    }

    // Cập nhật nhân viên
    @PostMapping("/{id}")
    public ResponseEntity<NhanVien> updateNhanVien(@PathVariable Integer id, @RequestBody NhanVienRequest requestDTO) {
        NhanVien updatedNhanVien = nhanVienService.updateNhanVien(id, requestDTO);
        return updatedNhanVien != null ? ResponseEntity.ok(updatedNhanVien) : ResponseEntity.notFound().build();
    }

    // Xóa nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Integer id) {
        nhanVienService.deleteNhanVien(id);
        return ResponseEntity.noContent().build();
    }


    // Tìm nhân viên theo email
    @GetMapping("/email/{email}")
    public ResponseEntity<NhanVien> getNhanVienByEmail(@PathVariable String email) {
        NhanVien nhanVien = nhanVienService.findNhanVienByEmail(email);
        return nhanVien != null ? ResponseEntity.ok(nhanVien) : ResponseEntity.notFound().build();
    }

    // Tìm nhân viên theo mã nhân viên
    @GetMapping("/ma-nhan-vien/{maNhanVien}")
    public ResponseEntity<List<NhanVien>> findNhanVienByMaNhanVien(@PathVariable String maNhanVien) {
        List<NhanVien> nhanVienList = nhanVienService.findNhanVienByMa(maNhanVien);
        return ResponseEntity.ok(nhanVienList);
    }

    // Lấy tất cả nhân viên sắp xếp theo lần cập nhật cuối giảm dần
    @GetMapping("/order-by-update-time")
    public ResponseEntity<List<NhanVien>> getAllNhanVienOrderByUpdateTime() {
        List<NhanVien> nhanVienList = nhanVienService.getAllOrderByLanCapNhatCuoiDesc();
        return ResponseEntity.ok(nhanVienList);
    }


    // Lấy tất cả nhân viên ngoại trừ một ID, sắp xếp theo lần cập nhật cuối
    @GetMapping("/exclude-id/{id}")
    public ResponseEntity<List<NhanVien>> getAllNhanVienExceptId(@PathVariable Integer id) {
        List<NhanVien> nhanVienList = nhanVienService.getAllExceptId(id);
        return ResponseEntity.ok(nhanVienList);
    }
    // Phân trang
    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<NhanVien> page = null;
        if(trangthai == null){
            page = nhanVienRepository.findAll(pageable);
        }
        else{
            page = nhanVienRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    // Tìm kiếm nhân viên theo tên hoặc số điện thoại
    @GetMapping("/search")
    public ResponseEntity<List<NhanVien>> findNhanVienByHoVaTenOrSoDienThoai(@RequestParam String hoVaTen, @RequestParam String soDienThoai) {
        List<NhanVien> khachHangList = nhanVienService.findByHoVaTenOrSoDienThoai(hoVaTen, soDienThoai);
        return ResponseEntity.ok(khachHangList);
    }

    // Cập nhật mật khẩu cho nhân viên
    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestParam String taiKhoan, @RequestParam String newPassword) {
        nhanVienService.updatePassword(taiKhoan, newPassword);
        return ResponseEntity.ok().build();
    }

    // Kiểm tra tồn tại số điện thoại
    @GetMapping("/exists/soDienThoai")
    public ResponseEntity<Boolean> existsBySoDienThoai(@RequestParam String soDienThoai) {
        boolean exists = nhanVienService.existsBySoDienThoai(soDienThoai);
        return ResponseEntity.ok(exists);
    }

    // Kiểm tra tồn tại email
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        boolean exists = nhanVienService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    @PostMapping("/dang-dang-nhap")
    public ResponseEntity<?> nhanVienDangDangNhap(HttpServletRequest request) {
        NhanVien nhanVien = userUltis.getLoggedInNhanVien(request);
        NhanVienResponse nhanVienResponse = new NhanVienResponse();
        nhanVienResponse.setMaNhanVien(nhanVien.getMaNhanVien());
        nhanVienResponse.setEmail(nhanVien.getEmail());
        nhanVienResponse.setAnh(nhanVien.getAnh());
        nhanVienResponse.setHoVaTen(nhanVienResponse.getHoVaTen());
        nhanVienResponse.setId(nhanVien.getId());
        return new ResponseEntity<>(nhanVienResponse, HttpStatus.OK);
    }
}
