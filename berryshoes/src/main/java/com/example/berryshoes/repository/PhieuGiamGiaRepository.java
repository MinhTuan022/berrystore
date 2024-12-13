package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.entity.HoaDon;
import com.example.berryshoes.entity.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {
    PhieuGiamGia findFirstByOrderByNgayTaoDesc();

//    List<PhieuGiamGia> findAllByHoaDon(HoaDon hd);

    @Query("SELECT p FROM PhieuGiamGia p where (:keySearch is null or p.tenPhieu like %:keySearch% or p.maCode like %:keySearch%)" +
            " and (:tungaySearch is null or p.ngayBatDau >= :tungaySearch)" +
            " and (:denngaySearch is null or p.ngayKetThuc <= :denngaySearch)" +
            " and (:kieuSearch is null or p.kieuPhieu = :kieuSearch)" +
            " and (:loaiSearch is null or p.loaiPhieu = :loaiSearch)" +
            " and (:ttSearch is null or p.trangThai = :ttSearch) ORDER BY p.ngayTao DESC ")
    Page<PhieuGiamGia> findAllOrderByNgayTaoDESC(@Param("keySearch") String keySearch,
                                                 @Param("tungaySearch") Timestamp tungaySearch,
                                                 @Param("denngaySearch") Timestamp denngaySearch,
                                                 @Param("kieuSearch") Boolean kieuSearch,
                                                 @Param("loaiSearch") Boolean loaiSearch,
                                                 @Param("ttSearch") Integer ttSearch,
                                                 Pageable pageable);

    @Query("SELECT p FROM PhieuGiamGia p WHERE p.id=?1")
    PhieuGiamGia findPhieuGiamGiaById(Integer Id);

    @Query("SELECT p FROM PhieuGiamGia p WHERE p.maCode=?1")
    PhieuGiamGia findPhieuGiamGiaByMaCode(String maCode);

    @Query("SELECT p FROM PhieuGiamGia p where p.kieuPhieu=?1 and p.trangThai=?2")
    List<PhieuGiamGia> findAllByKieuphieuaAndTrangthais(Boolean kieuphieu, Integer trangthai);

    @Query("SELECT p FROM PhieuGiamGia p where current_date between p.ngayBatDau and p.ngayKetThuc and p.soLuong > 0")
    List<PhieuGiamGia> khaDung();

    @Query("select p from PhieuGiamGia p where p.trangThai = ?1")
    Page<PhieuGiamGia> findByTrangThai(Integer trangthai, Pageable pageable);
}
