package com.example.berryshoes.service.impl;

import com.example.berryshoes.dto.request.ChatLieuRequest;
import com.example.berryshoes.entity.ChatLieu;
import com.example.berryshoes.repository.ChatLieuRepository;
import com.example.berryshoes.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    @Override
    public Optional<ChatLieu> getChatLieuById(Integer id) {
        return chatLieuRepository.findById(id);
    }

    @Override
    public ChatLieu createChatLieu(ChatLieuRequest requestDTO) {
        ChatLieu chatLieu = ChatLieu.builder()
                .tenChatLieu(requestDTO.getTenChatLieu())
                .nguoiTao(requestDTO.getNguoiTao())
                .trangThai(requestDTO.getTrangThai())
                .build();
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu updateChatLieu(Integer id, ChatLieuRequest requestDTO) {
        Optional<ChatLieu> optionalChatLieu = chatLieuRepository.findById(id);
        if (optionalChatLieu.isPresent()) {
            ChatLieu chatLieu = optionalChatLieu.get();
            chatLieu.setTenChatLieu(requestDTO.getTenChatLieu());
            chatLieu.setNguoiCapNhat(requestDTO.getNguoiCapNhat());
            chatLieu.setTrangThai(requestDTO.getTrangThai());
            return chatLieuRepository.save(chatLieu);
        }
        return null;
    }

    @Override
    public void deleteChatLieu(Integer id) {
        if (chatLieuRepository.existsById(id)) {
            chatLieuRepository.deleteById(id);
        } else {
            throw new RuntimeException("Chất liệu không tồn tại");
        }
    }
    @Override
    public List<ChatLieu> findByTenAndTrangThai(String ten, Integer trangThai) {
        return chatLieuRepository.findByTenAndTrangThai(ten, trangThai);
    }

    @Override
    public boolean existsByTenChatLieu(String tenChatLieu) {
        return chatLieuRepository.existsByTenChatLieu(tenChatLieu);
    }

    // Hàm lấy danh sách chất liệu với phân trang
    public Page<ChatLieu> getAllChatLieuPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return chatLieuRepository.findAll(pageable);
    }
}
