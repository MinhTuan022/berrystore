package com.example.berryshoes.repository;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.entity.KichCo;
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
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {


    @Query("SELECT kc FROM KichCo kc WHERE kc.tenKichCo LIKE %:ten% AND (:trangThai IS NULL OR kc.trangThai = :trangThai)")
    List<KichCo> findByTenAndTrangthai(@Param("ten") String ten, @Param("trangThai") Integer trangThai);

    boolean existsByTenKichCo(String tenKichCo);

    List<KichCo> getKichCoByTenKichCoOrTrangThai(String tenKichCo, Integer trangThai);

    List<KichCo> findAllByOrderByNgayTaoDesc();

    KichCo findByTenKichCo(String tenKichCo);

    @Query("SELECT kc FROM KichCo kc WHERE kc.tenKichCo = :ten AND kc.trangThai = 1")
    List<KichCo> findKichCoByTenAndTrangThaiTrue(@Param("ten") String tenKichCo);

    @Query(nativeQuery = true, value = """
            SELECT * FROM KichCo WHERE TrangThai = 1
            ORDER BY NgayTao DESC
            """)
    List<KichCo> getAll();

    @Modifying
    @Transactional
    @Query("UPDATE KichCo kc SET kc.trangThai = 0 WHERE kc.id = :id")
    void updateTrangThaiToFalseById(@Param("id") Integer id);

    @Query("select kc from KichCo kc where kc.trangThai = ?1")
    Page<KichCo> findByTrangThai(Integer trangthai, Pageable pageable);
}
