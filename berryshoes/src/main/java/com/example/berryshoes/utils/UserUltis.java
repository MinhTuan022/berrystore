package com.example.berryshoes.utils;

import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.NhanVien;
import com.example.berryshoes.jwt.JwtUtils;
import com.example.berryshoes.repository.KhachHangRepository;
import com.example.berryshoes.repository.NhanVienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserUltis {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public String getLoggedInUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        return null;
    }

    public KhachHang getLoggedInKhachHang(HttpServletRequest request){
        String jwt = getJwtFromRequest(request);
        String username = jwtUtils.extractUsername(jwt);
        System.out.println("username: " + username);
        if(username == null){
            return null;
        }
        return khachHangRepository.findByEmail(username);
    }

    public NhanVien getLoggedInNhanVien(HttpServletRequest request){
        String jwt = getJwtFromRequest(request);
        String username = jwtUtils.extractUsername(jwt);
        System.out.println("username: " + username);
        if(username == null){
            return null;
        }
        return nhanVienRepository.findNhanVienByEmail(username);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
