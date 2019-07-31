package com.adidas.microservices.emailservice.service;

import com.adidas.microservices.emailservice.dto.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderMail;

    private static Logger log = LoggerFactory.getLogger(EmailService.class.getName());

    @Override
    public void sendSimpleMessage(Subscription subscription) {

        log.info("Going to send newsletter email to " + subscription.getEmail());

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(senderMail);
            helper.setTo(subscription.getEmail());
            helper.setSubject("Email for " + subscription.getFirstName());
            helper.setText("As you were born on " + subscription.getDateOfBirth() + "\n" +
                    "here you have newsletter for campaign with id " + subscription.getCampaignId());

            javaMailSender.send(message);

            log.info("Mail sent to " + subscription.getEmail() + " successfully");

            //TODO connect to DB and set subscription as finished

        } catch (MessagingException e) {
            log.error("Something went wrong while sending email " + e.getLocalizedMessage());
        }
    }
}
