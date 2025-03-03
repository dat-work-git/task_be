package com.project.salemanagement.Services;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

public interface ISendEmail {
    String sendEmail(String recipient,
                     String subject,
                     String content,
                     MultipartFile[] files) throws MessagingException, UnsupportedEncodingException;
}
