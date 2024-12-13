package com.example.berryshoes.repository;

import com.example.berryshoes.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer>, JpaSpecificationExecutor<SanPhamChiTiet> {

    @Query("SELECT spct FROM SanPhamChiTiet spct " +
            "LEFT JOIN spct.kichCo kc " +
            "LEFT JOIN spct.mauSac ms " +
            "LEFT JOIN spct.dotGiamGia dgg " +
            "LEFT JOIN spct.sanPham sp " +
            "LEFT JOIN sp.thuongHieu th " +
            "LEFT JOIN sp.chatLieu cl " +
            "LEFT JOIN sp.deGiay dg " +
            "WHERE spct.id = :id")
    Optional<SanPhamChiTiet> findSanPhamChiTietByIdWithDetails(@Param("id") Integer id);

    @Query(value = """
            SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :id AND s.mauSac.tenMauSac LIKE %:ten%
            """)
    List<SanPhamChiTiet> listSizeColor(@Param("id") Integer id, @Param("ten") String ten);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :id ")
    List<SanPhamChiTiet> listAllSize(@Param("id") Integer id);

    // search theo biến thể sản phẩm
    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.sanPham = :sanPham " +
            "AND (:key IS NULL OR spct.sanPham.tenSanPham LIKE :key OR spct.maSanPhamChiTiet LIKE :key) " +
            "AND (:idKichCo IS NULL OR spct.kichCo.id = :idKichCo) " +
            "AND (:idMauSac IS NULL OR spct.mauSac.id = :idMauSac) " +
            "AND (:trangThai IS NULL OR spct.sanPham.trangThai = :trangThai)")
    List<SanPhamChiTiet> searchBySanPham(
            @Param("sanPham") SanPham sanPham,
            @Param("key") String key,
            @Param("idKichCo") Integer idKichCo,
            @Param("idMauSac") Integer idMauSac,
            @Param("trangThai") Integer trangThai
    );
    @Query(" SELECT s FROM SanPhamChiTiet s WHERE  s.mauSac=?1 AND s.kichCo=?2 AND s.sanPham=?3")
    SanPhamChiTiet findSPCT(MauSac mauSac, KichCo kichCo, SanPham sanPham);

    // check trùng màu sắc và kích cỡ
    boolean existsByMauSacAndKichCo(MauSac mauSac, KichCo kichCo);
    // tìm theo sản phẩm
    List<SanPhamChiTiet> findBysanPham(SanPham sanPham);
    Boolean existsByMaSanPhamChiTiet(String ma);
    @Query("SELECT c FROM SanPhamChiTiet c WHERE c.maSanPhamChiTiet like %?1%")
    List<SanPhamChiTiet> searchSPCTtheoMa(String maSP);
    // dùng cho giỏ hàng
    @Query(value = """
            SELECT s FROM SanPhamChiTiet s WHERE  s.id=:Id
            """)
    SanPhamChiTiet findByIdSPCT(Integer Id);
    @Query("SELECT c FROM SanPhamChiTiet c WHERE c.sanPham.tenSanPham like %?1% and c.sanPham.chatLieu.tenChatLieu like %?2% and c.sanPham.thuongHieu.tenThuongHieu like %?3% and c.sanPham.deGiay.tenDeGiay like %?4% and c.kichCo.tenKichCo like %?5% and c.mauSac.tenMauSac like %?6% and c.giaTien <= ?7 and c.soLuong >0")
    List<SanPhamChiTiet> searchSPCT(String tenSP, String chatLieu,
                                    String thuongHieu, String deGiay,
                                    String kichCo, String mauSac,
                                    BigDecimal giaTien);

    // dùng để lấy id cao nhất
    @Query(value = "SELECT MAX(spct.id) FROM SanPhamChiTiet spct")
    Integer findMaxIdSPCT();
    //dùng cho search các thuộc tính sản phẩm chi tiết
    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE (spct.sanPham.tenSanPham LIKE ?1 OR spct.maSanPhamChiTiet LIKE ?2) AND (?3 IS NULL OR spct.sanPham.thuongHieu.id=?3) " +
            "AND (?4 IS NULL OR " + " spct.sanPham.deGiay.id=?4) AND (?5 IS NULL OR spct.kichCo.id=?5) AND (?6 IS NULL OR spct.mauSac.id=?6)" +
            "AND (?7 IS NULL OR spct.sanPham.chatLieu.id=?7) AND (?8 IS NULL OR spct.sanPham.trangThai=?8)")
    List<SanPhamChiTiet> search(String key, String maSPCT, Integer idThuongHieu, Integer idDeGiay, Integer idKichCo, Integer idMauSac, Integer idChatLieu, Integer trangThai);
    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :Id")
    List<SanPhamChiTiet> findBySanPhamId(@Param("Id") Integer Id);
    //update số lượng và giá tiền
    @Transactional
    @Modifying
    @Query(value = "UPDATE SanPhamChiTiet SET soLuong = :soLuong, giaTien = :giaTien WHERE id = :id", nativeQuery = true)
    void updateSoLuongVaGiaTienById(@Param("id") Integer id, @Param("soLuong") Integer soLuong, @Param("giaTien") BigDecimal giaTien);
    // lấy id by sản phẩm
    @Query("SELECT s.sanPham.id FROM SanPhamChiTiet s WHERE s.id = :spctId")
    Integer findIdBySanpham(Integer spctId);
    // dùng để lấy giá tiền của spct
    @Query("SELECT spct.giaTien FROM SanPhamChiTiet spct WHERE spct.id = :productId")
    BigDecimal findPriceByProductId(@Param("productId") Integer id);
    // tìm sản phẩm chi tiết theo kích cỡ và màu sắc
    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :sanPhamId AND spct.mauSac.tenMauSac = :color AND spct.kichCo.tenKichCo = :size")
    SanPhamChiTiet findBySanPhamIdAndColorAndSize(@Param("sanPhamId") Integer sanPhamId, @Param("color") String color, @Param("size") String size);
    //   //Lấy tat ca sp co sl lon
//   Page<SanPhamChiTiet> findAllBySoluongLon(Integer soluong, Pageable p);
    @Query("SELECT d FROM DotGiamGia d WHERE d.id =:IdDot")
    List<SanPhamChiTiet> findSanPhamDotGiamByIdDotgiamgia(Integer IdDot);
//    List<DotGiamGia> findBySanPhamChiTiet(SanPhamChiTiet spct);
    /// Đợt giảm của spct
    @Query("""
        SELECT d FROM DotGiamGia d 
        WHERE d.trangThai = 1 
        AND d.id = :id
       """)
    SanPhamChiTiet findDotGiamSPCT(@Param("id") Integer id);
    @Query("""
            SELECT s FROM SanPhamChiTiet s WHERE s.dotGiamGia.trangThai=1
            """)
    List<SanPhamChiTiet> ListDotGiamDangHD();
}
