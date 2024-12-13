package com.example.berryshoes.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchDto {

    private List<Integer> idThuongHieu = new ArrayList<>();

    private List<Integer> idDeGiay = new ArrayList<>();

    private List<Integer> idChatLieu = new ArrayList<>();

    private Integer small;

    private Integer large;


}
