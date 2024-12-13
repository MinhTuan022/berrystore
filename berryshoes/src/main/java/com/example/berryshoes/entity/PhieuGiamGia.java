package com.example.berryshoes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PhieuGiamGia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MaCode")
    private String maCode;

    @Column(name = "TenPhieu", nullable = false)
    private String tenPhieu;

    @Column(name = "GiaTriGiamToiDa")
    private Double giaTriGiamToiDa; // Sử dụng Double cho tiền

    @Column(name = "GiaTriGiam", nullable = false)
    private Integer giaTriGiam;

    @Column(name = "DonToiThieu")
    private Double donToiThieu; // Sử dụng Double cho tiền

    @Column(name = "SoLuong")
    private Short soLuong;

    @Column(name = "LoaiPhieu", columnDefinition = "bit default 0")
    private Boolean loaiPhieu = false;

    @Column(name = "KieuPhieu", columnDefinition = "bit default 0")
    private Boolean kieuPhieu = false;

    @Column(name = "NgayBatDau", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayBatDau;

    @Column(name = "NgayKetThuc", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ngayKetThuc;

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

    @Column(name = "TrangThai", nullable = false)
    private Integer trangThai;

}
