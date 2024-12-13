package com.example.berryshoes.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {

    private String title;

    private String content;

    private String to;
}
