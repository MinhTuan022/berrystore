package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.entity.ThuongHieu;
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
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

    // Tìm thương hiệu theo tên và trạng thái
    @Query(value = """
            SELECT th FROM ThuongHieu th WHERE (th.tenThuongHieu LIKE %?1%) AND (?2 IS NULL OR th.trangThai = ?2)
            """)
    List<ThuongHieu> findByTenAndTrangThai(String ten, Integer trangThai);
//    @Query("SELECT th FROM ThuongHieu th WHERE th.tenThuongHieu LIKE %:ten% AND (:trangThai IS NULL OR th.trangThai = :trangThai)")
//    List<ThuongHieu> findByTenAndTrangthai(@Param("ten") String ten, @Param("trangThai") Integer trangThai);

    // Tìm thương hiệu theo tên hoặc trạng thái
    List<ThuongHieu> getThuongHieuByTenThuongHieuOrTrangThai(String tenThuongHieu, Integer trangThai);

    // Tìm tất cả thương hiệu theo thứ tự ngày tạo giảm dần
    List<ThuongHieu> findAllByOrderByNgayTaoDesc();

    // Kiểm tra xem thương hiệu có tồn tại theo tên
    boolean existsByTenThuongHieu(String tenThuongHieu);

    // Tìm thương hiệu theo tên và trạng thái là false
    @Query("SELECT th FROM ThuongHieu th WHERE th.tenThuongHieu = :ten AND th.trangThai = 0")
    List<ThuongHieu> findThuongHieuByTenAndTrangThaiFalse(@Param("ten") String ten);

    // Hiển thị tất cả thương hiệu có trạng thái là 1
    @Query(nativeQuery = true, value = """
            SELECT * FROM ThuongHieu WHERE TrangThai = 1
            ORDER BY NgayTao DESC
            """)
    List<ThuongHieu> getAll();

    // Cập nhật trạng thái thương hiệu thành 0
    @Modifying
    @Transactional
    @Query("UPDATE ThuongHieu th SET th.trangThai = 0 WHERE th.id = :id")
    void updateTrangThaiToFalseById(@Param("id") Integer id);

    @Query("select th from DeGiay th where th.trangThai = ?1")
    Page<ThuongHieu> findByTrangThai(Integer trangthai, Pageable pageable);
}
