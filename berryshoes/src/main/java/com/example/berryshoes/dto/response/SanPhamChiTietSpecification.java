package com.example.berryshoes.dto.response;

import com.example.berryshoes.entity.SanPhamChiTiet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietSpecification implements Specification<SanPhamChiTiet> {

    private Integer thuongHieuId;
    private Integer chatLieuId;
    private Integer deGiayId;
    private Integer mauSacId;
    private Integer kichThuocId;

    @Override
    public Predicate toPredicate(Root<SanPhamChiTiet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (thuongHieuId != null) {
            predicate = cb.and(predicate, cb.equal(root.get("sanPham").get("thuongHieu").get("id"), thuongHieuId));
        }
        if (chatLieuId != null) {
            predicate = cb.and(predicate, cb.equal(root.get("sanPham").get("chatLieu").get("id"), chatLieuId));
        }
        if (deGiayId != null) {
            predicate = cb.and(predicate, cb.equal(root.get("sanPham").get("deGiay").get("id"), deGiayId));
        }
        if (mauSacId != null) {
            predicate = cb.and(predicate, cb.equal(root.get("mauSac").get("id"), mauSacId));
        }
        if (kichThuocId != null) {
            predicate = cb.and(predicate, cb.equal(root.get("kichCo").get("id"), kichThuocId));
        }
        predicate = cb.and(predicate, cb.isNotNull(root.get("sanPham")));

        return predicate;
    }
}
