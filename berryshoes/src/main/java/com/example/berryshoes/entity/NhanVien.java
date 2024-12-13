package com.example.berryshoes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MaNhanVien", length = 50)
    private String maNhanVien;

    @Column(name = "Anh", length = 300)
    private String anh;

    @Column(name = "HoVaTen", nullable = false, length = 100)
    private String hoVaTen;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "GioiTinh", columnDefinition = "bit default 0")
    private Boolean gioiTinh = false;

    @Column(name = "QueQuan", nullable = false, length = 200)
    private String queQuan;

    @Column(name = "Cccd", length = 20)
    private String cccd;

    @Column(name = "SoDienThoai", length = 20)
    private String soDienThoai;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "TaiKhoan", length = 50)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 300)
    private String matKhau;

    //    @Enumerated(EnumType.STRING)
//    @Column(name = "VaiTro", columnDefinition = "int default 0")
//    private Role vaiTro;
    @Column(name = "VaiTro", columnDefinition = "int default 0")
    private Integer vaiTro;

    @CreationTimestamp
    @Column(name = "NgayTao", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayTao;

    @Column(name = "NguoiTao", length = 100)
    private String nguoiTao;

    @UpdateTimestamp
    @Column(name = "LanCapNhatCuoi")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lanCapNhatCuoi;

    @Column(name = "NguoiCapNhat", length = 100)
    private String nguoiCapNhat;

    @Column(name = "TrangThai", columnDefinition = "int default 0")
    private Integer trangThai = 0;
}
