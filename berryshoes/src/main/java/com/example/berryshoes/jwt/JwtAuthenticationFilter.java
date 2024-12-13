package com.example.berryshoes.jwt;


import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.NhanVien;
import com.example.berryshoes.entity.Role;
import com.example.berryshoes.entity.User;
import com.example.berryshoes.exception.MessageException;
import com.example.berryshoes.repository.KhachHangRepository;
import com.example.berryshoes.repository.NhanVienRepository;
import com.example.berryshoes.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest((HttpServletRequest) request);
            String email = jwtUtils.extractUsername(jwt);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt, email)) {
                String role = "";
                Optional<KhachHang> khachHang = khachHangRepository.findKhByEmail(email);
                Optional<NhanVien> nhanVien = nhanVienRepository.findByEmail(email);
                if (khachHang.isPresent()) {
                    role = "ROLE_CUSTOMER";
                }
                if (nhanVien.isPresent()) {
                    if (nhanVien.get().getVaiTro() == 1) {
                        role = "ROLE_ADMIN";
                    }
                    if (nhanVien.get().getVaiTro() == 0) {
                        role = "ROLE_EMPLOYEE";
                    }
                }
                System.out.println("role: "+role);
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            printErrorDetails(ex);
        }
        filterChain.doFilter(request, response);
    }
    private void printErrorDetails(Exception ex) {
        //        System.out.println("Error Message: " + ex.getMessage());
        //        System.out.println("Error Class: " + ex.getClass().getName());
        for (StackTraceElement element : ex.getStackTrace()) {
            System.out.println("at " + element);
        }
    }


}