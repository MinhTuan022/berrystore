package com.example.berryshoes.repository;

import com.example.berryshoes.entity.KhachHang;
import com.example.berryshoes.entity.KhachHangPhieuGiam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangPhieuGiamRepository extends JpaRepository<KhachHangPhieuGiam, Integer> {
    @Query("SELECT s FROM KhachHangPhieuGiam s WHERE s.phieuGiamGia.id =:IdPhieu")
    List<KhachHangPhieuGiam> findKhachHangPhieuGiamByIdPhieugiamgia(Integer IdPhieu);
    @Query("SELECT s FROM KhachHangPhieuGiam s WHERE s.khachHang.id =:IdKH")
    List<KhachHangPhieuGiam> findKhachHangPhieuGiamByIdKhachHang(Integer IdKH);

    List<KhachHangPhieuGiam> findAllByKhachHang(KhachHang kh);

    @Query("select p from KhachHangPhieuGiam p where p.phieuGiamGia.maCode = ?1 and p.khachHang.id = ?2")
    Optional<KhachHangPhieuGiam> findByMaGGAndKhId(String maGG, Integer khId);
}
