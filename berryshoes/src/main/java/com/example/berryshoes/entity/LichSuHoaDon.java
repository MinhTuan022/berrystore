package com.example.berryshoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LichSuHoaDon")
public class LichSuHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdHoaDon", nullable = false)
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "NgayTao", updatable = false)
    private Timestamp ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @Column(name = "LanCapNhatCuoi")
    private Timestamp lanCapNhatCuoi;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
