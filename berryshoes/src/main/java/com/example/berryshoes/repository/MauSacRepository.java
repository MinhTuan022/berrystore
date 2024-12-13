package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.entity.MauSac;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {

    //    @Query("SELECT m FROM MauSac m WHERE m.tenMauSac = :tenMauSac")
    //    MauSac findByTenMauSac(String tenMauSac);
    // Tìm màu sắc theo tên và trạng thái
    @Query("SELECT ms FROM MauSac ms WHERE (ms.tenMauSac LIKE %:ten%) AND (:trangThai IS NULL OR ms.trangThai = :trangThai)")
    List<MauSac> findByTenAndTrangThai(@Param("ten") String ten, @Param("trangThai") Integer trangThai);

    // Kiểm tra xem màu sắc có tồn tại hay không theo tên
    boolean existsByTenMauSac(String tenMauSac);

    // Lấy tất cả màu sắc theo thứ tự ngày tạo giảm dần
    List<MauSac> findAllByOrderByNgayTaoDesc();

    // Lấy màu sắc theo tên và trạng thái là true
    @Query("SELECT ms FROM MauSac ms WHERE ms.tenMauSac = :ten AND ms.trangThai = 1")
    List<MauSac> findMauSacByTenAndTrangThaiTrue(@Param("ten") String ten);

    // Lấy tất cả màu sắc có trạng thái = 1
    @Query(nativeQuery = true, value = "SELECT * FROM MauSac WHERE TrangThai = 1 ORDER BY NgayTao DESC")
    List<MauSac> getAll();

    // Cập nhật trạng thái màu sắc thành false theo ID
    @Modifying
    @Transactional
    @Query("UPDATE MauSac ms SET ms.trangThai = 0 WHERE ms.id = :id")
    void updateTrangThaiToFalseById(@Param("id") Integer id);

    @Query("select ms from MauSac ms where ms.trangThai = ?1")
    Page<MauSac> findByTrangThai(Integer trangthai, Pageable pageable);
}
