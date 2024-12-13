package com.example.berryshoes.service.impl;//package com.example.berryshoes.service.impl;
//
//import com.example.berryshoes.dto.request.LoginRequest;
//import com.example.berryshoes.dto.response.LoginResponse;
//import com.example.berryshoes.entity.KhachHang;
//import com.example.berryshoes.entity.NhanVien;
//import com.example.berryshoes.repository.KhachHangRepository;
//import com.example.berryshoes.repository.NhanVienRepository;
//import com.example.berryshoes.service.AuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    @Autowired
//    private KhachHangRepository khachHangRepository;
//
//    @Autowired
//    private NhanVienRepository nhanVienRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        // Kiểm tra khách hàng
//        KhachHang khachHang = khachHangRepository.findByTaiKhoan(loginRequest.getTaiKhoan());
//        if (khachHang != null && passwordEncoder.matches(loginRequest.getMatKhau(), khachHang.getMatKhau())) {
//            String accessToken = jwtService.generateToken(khachHang);
//            String refreshToken = jwtService.generateRefreshToken(khachHang);
//            return new LoginResponse(accessToken, refreshToken, 0); // 0: Khách hàng
//        }
//
//        // Kiểm tra nhân viên
//        NhanVien nhanVien = nhanVienRepository.findByTaiKhoan(loginRequest.getTaiKhoan());
//        if (nhanVien != null && passwordEncoder.matches(loginRequest.getMatKhau(), nhanVien.getMatKhau())) {
//            String accessToken = jwtService.generateToken(nhanVien);
//            String refreshToken = jwtService.generateRefreshToken(nhanVien);
//            return new LoginResponse(accessToken, refreshToken, 1); // 1: Nhân viên
//        }
//
//        throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
//    }
//}
