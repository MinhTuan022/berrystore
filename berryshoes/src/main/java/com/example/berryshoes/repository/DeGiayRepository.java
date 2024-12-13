package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.entity.HoaDon;
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
public interface DeGiayRepository extends JpaRepository<DeGiay, Integer> {
    // Tìm đế giày theo tên và trạng thái
    @Query(value = """
            SELECT dg FROM DeGiay dg WHERE (dg.tenDeGiay LIKE %?1%) AND (?2 IS NULL OR dg.trangThai = ?2)
            """)
    List<DeGiay> findByTenAndTrangThai(String ten, Integer trangThai);

    // Tìm tất cả đế giày theo thứ tự ngày tạo giảm dần
    List<DeGiay> findAllByOrderByNgayTaoDesc();

    // Tìm đế giày theo tên
    @Query("SELECT dg FROM DeGiay dg WHERE dg.tenDeGiay = :ten")
    List<DeGiay> findDeGiayByTen(@Param("ten") String ten);

    // Tìm đế giày theo tên và trạng thái là false
    @Query("SELECT dg FROM DeGiay dg WHERE dg.tenDeGiay = :ten AND dg.trangThai = 0")
    List<DeGiay> findDeGiayByTenAndTrangThaiFalse(@Param("ten") String ten);

    // Hiển thị tất cả đế giày có trạng thái là 1
    @Query(nativeQuery = true, value = """
            SELECT * FROM DeGiay WHERE TrangThai = 1
            ORDER BY NgayTao DESC
            """)
    List<DeGiay> getAll();

    // Cập nhật trạng thái đế giày thành 0
    @Modifying
    @Transactional
    @Query("UPDATE DeGiay d SET d.trangThai = 0 WHERE d.id = :id")
    void updateTrangThaiToFalseById(@Param("id") Integer id);

    // Kiểm tra xem đế giày có tồn tại theo tên
    boolean existsByTenDeGiay(String ten);

    @Query("select d from DeGiay d where d.trangThai = ?1")
    Page<DeGiay> findByTrangThai(Integer trangthai, Pageable pageable);
}
