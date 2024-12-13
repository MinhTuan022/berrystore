package com.example.berryshoes.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    @Async
    public void sendMailHtml(String to, String subject, String content) {
        // Tạo MimeMessage
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // Sử dụng MimeMessageHelper để cấu hình email HTML
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true để kích hoạt HTML
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        // Gửi email
        javaMailSender.send(mimeMessage);
    }
}
