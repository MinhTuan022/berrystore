package com.example.berryshoes.service;

import com.example.berryshoes.dto.request.DotGiamGiaRequest;
import com.example.berryshoes.dto.response.DotGiamGiaResponse;
import com.example.berryshoes.entity.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DotGiamGiaService {
    List<DotGiamGiaResponse> getAllDotGiamGia();

    Optional<DotGiamGiaResponse> getDotGiamGiaById(Integer id);

    DotGiamGiaResponse createDotGiamGia(DotGiamGiaRequest requestDTO);

    DotGiamGiaResponse updateDotGiamGia(Integer id, DotGiamGiaRequest requestDTO);

    void deleteDotGiamGia(Integer id);

    // Tìm danh sách DotGiamGia với các điều kiện tìm kiếm và phân trang
    Page<DotGiamGia> findAllOrderByNgayTaoDESC(String keySearch, Timestamp tungaySearch, Timestamp denngaySearch, Integer ttSearch, Pageable pageable);

    // Tìm DotGiamGia theo ID
    DotGiamGia findDotGiamGiaById(Integer id);

    // Tìm DotGiamGia mới nhất
    DotGiamGia findFirstByOrderByNgayTaoDesc();
}
