package com.example.berryshoes.controller;

import com.example.berryshoes.entity.GioHang;
import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.SanPhamChiTiet;
import com.example.berryshoes.exception.MessageException;
import com.example.berryshoes.repository.GioHangRepository;
import com.example.berryshoes.repository.SanPhamChiTietRepository;
import com.example.berryshoes.service.GioHangService;
import com.example.berryshoes.utils.UserUltis;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gio-hang")
@RequiredArgsConstructor
public class GioHangController {

    private final GioHangService gioHangService;
    private final GioHangRepository gioHangRepository;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private UserUltis userUltis;

    @GetMapping("/gio-hang-cua-toi")
    public ResponseEntity<List<GioHang>> getAllGioHang(HttpServletRequest request) {
        KhachHang khachHang = userUltis.getLoggedInKhachHang(request);
        List<GioHang> gioHangList = gioHangRepository.findByKhachHangId(khachHang.getId());
        return ResponseEntity.ok(gioHangList);
    }

    @GetMapping("/count")
    public ResponseEntity<?> demGioHang(HttpServletRequest request) {
        KhachHang khachHang = userUltis.getLoggedInKhachHang(request);
        Long gioHangList = gioHangRepository.demGioHang(khachHang.getId());
        return ResponseEntity.ok(gioHangList);
    }

    @PostMapping("/add")
    public void addGioHang(@RequestParam Integer idChiTietSanPham,
                           @RequestParam Short soLuong,
                           HttpServletRequest request) {
        // Lấy thông tin khách hàng đăng nhập
        KhachHang khachHang = userUltis.getLoggedInKhachHang(request);

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa
        if (gioHangRepository.findByKhachHangAndSanPhamChiTiet(khachHang.getId(), idChiTietSanPham) != null) {
            throw new MessageException("Sản phẩm đã có trong giỏ hàng");
        }

        // Lấy thông tin chi tiết sản phẩm
        Optional<SanPhamChiTiet> sanPhamChiTiet = sanPhamChiTietRepository.findById(idChiTietSanPham);
        if (sanPhamChiTiet.isEmpty()) {
            throw new MessageException("Không tồn tại spct");
        }

        // Tạo mới đối tượng giỏ hàng và lưu vào cơ sở dữ liệu
        GioHang gioHang = new GioHang();
        gioHang.setKhachHang(khachHang);
        gioHang.setSoLuong(soLuong); // Gán số lượng từ tham số
        gioHang.setNgayTao(new Timestamp(System.currentTimeMillis()));
        gioHang.setSanPhamChiTiet(sanPhamChiTiet.get());
        gioHangRepository.save(gioHang);
    }


    @PostMapping("/cap-nhat-so-luong")
    public void upDownSoluong(@RequestParam Integer idGioHang, @RequestParam Integer soLuong){
        GioHang cart = gioHangRepository.findById(idGioHang).get();
        cart.setSoLuong((short) (cart.getSoLuong() + soLuong));
        if(cart.getSoLuong() == 0){
            gioHangRepository.deleteById(idGioHang);
            return;
        }
        gioHangRepository.save(cart);
    }

    // Xóa giỏ hàng theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGioHang(@PathVariable Integer id) {
        gioHangService.deleteGioHang(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tinh-tong")
    public ResponseEntity<?> kiemTraPhieu(@RequestBody List<Integer> idGioHang) {
        List<GioHang> list = gioHangRepository.findAllById(idGioHang);
        Double tong = 0D;
        for(GioHang g : list){
            tong += g.getSoLuong() * g.getSanPhamChiTiet().getGiaTien();
        }
        return new ResponseEntity<>(tong, HttpStatus.OK);
    }
}
