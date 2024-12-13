package com.example.berryshoes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IdPhieuGiamGia")
    private PhieuGiamGia phieuGiamGia;

    @Column(name = "Mahoadon", unique = true)
    private String maHoaDon;

    @Column(name = "TenKhachHang")
    private String tenKhachHang;

    @Column(name = "Email")
    private String email;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "TienGiam")
    private BigDecimal tienGiam;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "LoaiHoaDon", columnDefinition = "bit default 0")
    private Boolean loaiHoaDon = false;

    @Column(name = "PhiVanChuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "NgayXacNhan")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayXacNhan;

    @Column(name = "NgayVanChuyen")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayVanChuyen;

    @Column(name = "NgayNhanHang")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayNhanHang;

    @Column(name = "NgayHoanThanh")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayHoanThanh;

    @Column(name = "GhiChu")
    private String ghiChu;

    @CreationTimestamp
    @Column(name = "NgayTao", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @UpdateTimestamp
    @Column(name = "LanCapNhatCuoi")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lanCapNhatCuoi;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = {"hoaDon"})
    private List<HoaDonChiTiet> hoaDonChiTiets;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = {"hoaDon"})
    private List<PhuongThucThanhToan> phuongThucThanhToans;
}
