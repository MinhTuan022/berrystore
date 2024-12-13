package com.example.berryshoes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponsePayment {

    private String linkThanhToan;

    private String vnpOrderInfo;
}
