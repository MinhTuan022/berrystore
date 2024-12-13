//package com.example.berryshoes.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.sql.Timestamp;
//
//@Builder
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "DiaChi")
//public class DiaChi {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "IdKhachHang", nullable = false)
//    private KhachHang khachHang;
//
//    @Column(name = "TenDuong", nullable = false)
//    private String tenDuong;
//
//    @Column(name = "XaPhuong", nullable = false)
//    private String xaPhuong;
//
//    @Column(name = "QuanHuyen", nullable = false)
//    private String quanHuyen;
//
//    @Column(name = "TinhThanhPho", nullable = false)
//    private String tinhThanhPho;
//
//    @Column(name = "TenNguoiNhan")
//    private String tenNguoiNhan;
//
//    @Column(name = "SdtNguoiNhan")
//    private String sdtNguoiNhan;
//
//    @CreationTimestamp
//    @Column(name = "NgayTao", updatable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private Timestamp ngayTao;
//
//    @Column(name = "NguoiTao")
//    private String nguoiTao;
//
//    @UpdateTimestamp
//    @Column(name = "LanCapNhatCuoi")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private Timestamp lanCapNhatCuoi;
//
//    @Column(name = "NguoiCapNhat")
//    private String nguoiCapNhat;
//
//    @Column(name = "ProvinceId")
//    private Integer ProvinceId;
//
//    @Column(name = "DistrictId")
//    private Integer DistrictId;
//
//    @Column(name = "WardCode")
//    private String WardCode;
//
//    @Column(name = "TrangThai", columnDefinition = "int default 0")
//    private Integer trangThai = 0;
//}
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
@Table(name = "DiaChi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(name = "TenDuong", nullable = false)
    private String tenDuong;

    @Column(name = "XaPhuong", nullable = false)
    private String xaPhuong;

    @Column(name = "QuanHuyen", nullable = false)
    private String quanHuyen;

    @Column(name = "TinhThanhPho", nullable = false)
    private String tinhThanhPho;

    @Column(name = "TenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "SdtNguoiNhan")
    private String sdtNguoiNhan;

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

    @Column(name = "TrangThai", columnDefinition = "int default 0")
    private Integer trangThai = 0;
}
