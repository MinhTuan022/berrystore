package com.example.berryshoes.repository;

import com.example.berryshoes.entity.ChatLieu;
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
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {

    // Kiểm tra xem chất liệu có tồn tại theo tên
    boolean existsByTenChatLieu(String tenChatLieu);

    // Tìm chất liệu theo tên và trạng thái
    @Query(value = """
            SELECT cl FROM ChatLieu cl WHERE (cl.tenChatLieu LIKE %?1%) AND (?2 IS NULL OR cl.trangThai = ?2)
            """)
    List<ChatLieu> findByTenAndTrangThai(String ten, Integer trangThai);

    // Tìm chất liệu theo tên hoặc trạng thái
    @Query("SELECT c FROM ChatLieu c WHERE c.tenChatLieu LIKE %?1% OR c.trangThai = ?2")
    List<ChatLieu> findByTenVaTrangThai(String ten, Integer trangThai);

    // Tìm tất cả chất liệu theo thứ tự ngày tạo giảm dần
    List<ChatLieu> findAllByOrderByNgayTaoDesc();

    // Tìm chất liệu theo tên và trạng thái là false
    @Query("SELECT cl FROM ChatLieu cl WHERE cl.tenChatLieu = :ten AND cl.trangThai = 0")
    List<ChatLieu> findChatLieuByTenAndTrangThaiFalse(@Param("ten") String ten);

    // Lấy tất cả chất liệu có trạng thái là 1
    @Query(nativeQuery = true, value = """
            SELECT * FROM ChatLieu WHERE TrangThai = 1
            ORDER BY NgayTao DESC
            """)
    List<ChatLieu> getAll();

    // Cập nhật trạng thái chất liệu thành false theo ID
    @Modifying
    @Transactional
    @Query("UPDATE ChatLieu cl SET cl.trangThai = 0 WHERE cl.id = :id")
    void updateTrangThaiToFalseById(@Param("id") Integer id);

    @Query("select cl from ChatLieu cl where cl.trangThai = ?1")
    Page<ChatLieu> findByTrangThai(Integer trangthai, Pageable pageable);
}
