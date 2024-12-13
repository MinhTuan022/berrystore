package com.example.berryshoes.controller;

import com.example.berryshoes.entity.DeGiay;
import com.example.berryshoes.repository.ChatLieuRepository;
import com.example.berryshoes.service.ChatLieuService;
import com.example.berryshoes.dto.request.ChatLieuRequest;
import com.example.berryshoes.dto.response.ChatLieuResponse;
import com.example.berryshoes.entity.ChatLieu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat-lieu")
@RequiredArgsConstructor
public class ChatLieuController {
    private final ChatLieuService chatLieuService;
    private final ChatLieuRepository chatLieuRepository;

    public void validateChatLieu(ChatLieuRequest requestDTO) {
        // Kiểm tra tên chất liệu không được trống
        if (requestDTO.getTenChatLieu() == null || requestDTO.getTenChatLieu().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chất liệu không được trống");
        }

        // Kiểm tra độ dài tên chất liệu không quá 255 ký tự
        if (requestDTO.getTenChatLieu().length() > 255) {
            throw new IllegalArgumentException("Tên chất liệu quá dài");
        }

        // Kiểm tra trùng lặp tên chất liệu
        if (chatLieuService.existsByTenChatLieu(requestDTO.getTenChatLieu())) {
            throw new IllegalArgumentException("Tên chất liệu đã tồn tại");
        }
    }
    // Lấy tất cả chất liệu
//    @GetMapping
//    public ResponseEntity<Page<ChatLieuResponse>> getAllChatLieu(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//
//        Page<ChatLieu> chatLieuPage = chatLieuService.getAllChatLieuPaginated(page, size);
//        Page<ChatLieuResponse> responsePage = chatLieuPage.map(chatLieu -> {
//            ChatLieuResponse response = new ChatLieuResponse();
//            response.setId(chatLieu.getId());
//            response.setTenChatLieu(chatLieu.getTenChatLieu());
//            response.setNgayTao(chatLieu.getNgayTao());
//            response.setNguoiTao(chatLieu.getNguoiTao());
//            response.setLanCapNhatCuoi(chatLieu.getLanCapNhatCuoi());
//            response.setNguoiCapNhat(chatLieu.getNguoiCapNhat());
//            response.setTrangThai(chatLieu.getTrangThai());
//            return response;
//        });
//
//        return ResponseEntity.ok(responsePage);
//    }

    @GetMapping
    public ResponseEntity<?> getAllChatLieu() {
        List<ChatLieu> chatLieuPage = chatLieuRepository.findAll();
        return ResponseEntity.ok(chatLieuPage);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) Integer trangthai){
        Page<ChatLieu> page = null;
        if(trangthai == null){
            page = chatLieuRepository.findAll(pageable);
        }
        else{
            page = chatLieuRepository.findByTrangThai(trangthai, pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Lấy chất liệu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ChatLieuResponse> getChatLieuById(@PathVariable Integer id) {
        Optional<ChatLieu> chatLieu = chatLieuService.getChatLieuById(id);
        return chatLieu.map(chat -> {
            ChatLieuResponse response = new ChatLieuResponse();
            response.setId(chat.getId());
            response.setTenChatLieu(chat.getTenChatLieu());
            response.setNgayTao(chat.getNgayTao());
            response.setNguoiTao(chat.getNguoiTao());
            response.setLanCapNhatCuoi(chat.getLanCapNhatCuoi());
            response.setNguoiCapNhat(chat.getNguoiCapNhat());
            response.setTrangThai(chat.getTrangThai());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới chất liệu
    @PostMapping
    public ResponseEntity<ChatLieuResponse> createChatLieu(@RequestBody ChatLieuRequest requestDTO) {
        validateChatLieu(requestDTO);
        ChatLieu createdChatLieu = chatLieuService.createChatLieu(requestDTO);
        ChatLieuResponse response = new ChatLieuResponse();
        response.setId(createdChatLieu.getId());
        response.setTenChatLieu(createdChatLieu.getTenChatLieu());
        response.setNgayTao(createdChatLieu.getNgayTao());
        response.setNguoiTao(createdChatLieu.getNguoiTao());
        response.setLanCapNhatCuoi(createdChatLieu.getLanCapNhatCuoi());
        response.setNguoiCapNhat(createdChatLieu.getNguoiCapNhat());
        response.setTrangThai(createdChatLieu.getTrangThai());
        return ResponseEntity.ok(response);
    }

    // Cập nhật chất liệu
    @PostMapping("/{id}")
    public ResponseEntity<ChatLieuResponse> updateChatLieu(@PathVariable Integer id, @RequestBody ChatLieuRequest requestDTO) {
        ChatLieu updatedChatLieu = chatLieuService.updateChatLieu(id, requestDTO);
        return updatedChatLieu != null ? ResponseEntity.ok(convertToResponse(updatedChatLieu)) : ResponseEntity.notFound().build();
    }

    // Xóa chất liệu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatLieu(@PathVariable Integer id) {
        chatLieuService.deleteChatLieu(id);
        return ResponseEntity.noContent().build();
    }

    private ChatLieuResponse convertToResponse(ChatLieu chatLieu) {
        ChatLieuResponse response = new ChatLieuResponse();
        response.setId(chatLieu.getId());
        response.setTenChatLieu(chatLieu.getTenChatLieu());
        response.setNgayTao(chatLieu.getNgayTao());
        response.setNguoiTao(chatLieu.getNguoiTao());
        response.setLanCapNhatCuoi(chatLieu.getLanCapNhatCuoi());
        response.setNguoiCapNhat(chatLieu.getNguoiCapNhat());
        response.setTrangThai(chatLieu.getTrangThai());
        return response;
    }
    @GetMapping("/search")
    public ResponseEntity<List<ChatLieu>> searchChatLieu(@RequestParam String ten, @RequestParam(required = false) Integer trangThai) {
        List<ChatLieu> result = chatLieuService.findByTenAndTrangThai(ten, trangThai);
        return ResponseEntity.ok(result);
    }
}
