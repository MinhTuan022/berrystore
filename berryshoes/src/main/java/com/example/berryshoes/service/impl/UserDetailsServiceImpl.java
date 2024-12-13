package com.example.berryshoes.service.impl;//package com.example.berryshoes.service.impl;
//
//import com.example.berryshoes.entity.KhachHang;
//import com.example.berryshoes.entity.NhanVien;
//import com.example.berryshoes.repository.KhachHangRepository;
//import com.example.berryshoes.repository.NhanVienRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private KhachHangRepository khachHangRepository;
//
//    @Autowired
//    private NhanVienRepository nhanVienRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Kiểm tra trong bảng KhachHang
//        KhachHang khachHang = khachHangRepository.findByTaiKhoan(username);
//        if (khachHang != null) {
//            return new CustomUserDetails(khachHang.getTaiKhoan(), khachHang.getMatKhau(), null); // Thêm authorities
//        }
//
//        // Kiểm tra trong bảng NhanVien
//        NhanVien nhanVien = nhanVienRepository.findByTaiKhoan(username);
//        if (nhanVien != null) {
//            return new CustomUserDetails(nhanVien.getTaiKhoan(), nhanVien.getMatKhau(), null); // Thêm authorities
//        }
//
//        throw new UsernameNotFoundException("User not found");
//    }
//}
