package com.example.berryshoes.dto.response;

import com.example.berryshoes.entity.SanPham;
import com.example.berryshoes.entity.SanPhamChiTiet;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class SanPhamSpecification implements Specification<SanPham> {

    private List<Integer> thuongHieuIds;
    private Integer minPrice;
    private Integer maxPrice;
    private List<Integer> deGiayIds;
    private List<Integer> chatLieuIds;

    @Override
    public Predicate toPredicate(Root<SanPham> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (thuongHieuIds != null && !thuongHieuIds.isEmpty()) {
            predicate = cb.and(predicate, root.get("thuongHieu").get("id").in(thuongHieuIds));
        }

        if (deGiayIds != null && !deGiayIds.isEmpty()) {
            predicate = cb.and(predicate, root.get("deGiay").get("id").in(deGiayIds));
        }

        if (chatLieuIds != null && !chatLieuIds.isEmpty()) {
            predicate = cb.and(predicate, root.get("chatLieu").get("id").in(chatLieuIds));
        }

        Join<SanPham, SanPhamChiTiet> chiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);

// Lọc theo giá (nếu có)
        if (minPrice != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(chiTietJoin.get("giaTien"), minPrice));
        }
        if (maxPrice != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(chiTietJoin.get("giaTien"), maxPrice));
        }
        query.distinct(true);

        System.out.println("Predicate: " + predicate);

        return predicate;
    }
}
