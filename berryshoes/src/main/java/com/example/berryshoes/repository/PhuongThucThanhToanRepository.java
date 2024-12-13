package com.example.berryshoes.repository;

import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan, Integer> {
    @Query("SELECT p FROM PhuongThucThanhToan p WHERE p.hoaDon.id = ?1")
    PhuongThucThanhToan findByIdHoaDon(Integer idHoaDon);

    List<PhuongThucThanhToan> findAllByHoaDon(HoaDon hd);

    @Query("select count(p.id) from PhuongThucThanhToan p where p.maGiaoDichVnPay = ?1")
    Long findBymaGiaoDichVnPay(String maGiaoDichVnPay);

    @Query("select p from PhuongThucThanhToan p where p.maGiaoDichVnPay = ?1")
    Optional<PhuongThucThanhToan> findByMaGG(String maGiaoDichVnPay);
}
