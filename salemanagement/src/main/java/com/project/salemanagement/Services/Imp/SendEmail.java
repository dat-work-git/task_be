package com.project.salemanagement.Services.Imp;

import com.project.salemanagement.Services.ISendEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmail implements ISendEmail {

    private final JavaMailSender mailSender;
    //@Value("${spring.mail.from}")
    private String emailFrom = "lvtd2311@gmail.com";

    public String sendEmail(String recipient,
                            String subject,
                            String content,
                            MultipartFile[] files) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom, "SALE MANAGEMENT");
        if (recipient.contains(",")) {
            helper.setTo(InternetAddress.parse(recipient));
        } else {
            helper.setTo(recipient);
        }
        if (files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        log.info("Successfully!");
        return "sent";
    }
}
