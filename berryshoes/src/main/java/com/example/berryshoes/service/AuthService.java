package com.example.berryshoes.service;//package com.example.berryshoes.service;
//
//import com.example.berryshoes.dto.request.AuthRequest;
//import com.example.berryshoes.dto.response.AuthResponse;
//import com.example.berryshoes.entity.NhanVien;
//import com.example.berryshoes.entity.Role;
//import com.example.berryshoes.repository.NhanVienRepository;
//import com.example.berryshoes.security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final NhanVienRepository nhanVienRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider tokenProvider;
//
//    public AuthResponse login(AuthRequest request) {
//        Optional<NhanVien> nhanVien = nhanVienRepository.findByTaiKhoan(request.getTaiKhoan());
//        if (nhanVien.isPresent() && passwordEncoder.matches(request.getMatKhau(), nhanVien.get().getMatKhau())) {
//            String role = nhanVien.get().getVaiTro() == Role.QUANLY ? "ROLE_QUANLY" : "ROLE_NHANVIEN";
//            String token = tokenProvider.generateToken(nhanVien.get().getTaiKhoan(), role);
//            return new AuthResponse(token);
//        }
//        throw new RuntimeException("Invalid username or password");
//    }
//}
