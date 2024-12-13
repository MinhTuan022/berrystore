package com.example.berryshoes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrangThai {

    private Integer value;

    private String tenTrangThai;



    public static List<TrangThai> initTrangThais(){
        List<TrangThai> list = new ArrayList<TrangThai>();
        list.add(new TrangThai(1, "Đang chờ xác nhận"));
        list.add(new TrangThai(2, "Đã xác nhận đơn hàng"));
        list.add(new TrangThai(3, "Đang chờ đơn vị vận chuyển"));
        list.add(new TrangThai(4, "Đơn hàng đã được gửi đi"));
        list.add(new TrangThai(5, "Đã nhận"));
        list.add(new TrangThai(6, "Đã hủy"));
        list.add(new TrangThai(7, "Không nhận hàng"));
        list.add(new TrangThai(8, "Đã hoàn thành"));
        return list;
    }

    public static Boolean kiemTraTonTai(Integer value){
        List<TrangThai> list = initTrangThais();
        for(TrangThai t : list){
            if (t.getValue() == value) return true;
        }
        return false;
    }
}
