package co.ke.auth.services;


import co.ke.auth.models.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author TMwaura on 06/02/2020
 * @Project admin-dashboard
 */

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendSimpleMessage(Mail mail) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.addAttachment("logo.png", new ClassPathResource("/static/assets/users/logo.png"));
            helper.addAttachment("seperator.png", new ClassPathResource("/static/assets/users/seperator.png"));
            helper.setFrom(emailFrom);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());

            Context context = new Context();
            context.setVariables(mail.getModel());
            logger.info("variables: {}", mail.getModel());
            logger.info("ctx: {}", context);
            String html = templateEngine.process("mail/email-template", context);
            helper.setText(html, true);
            mailSender.send(message);
        }catch (MessagingException e){
            logger.error(e.getMessage());
            logger.debug(Arrays.toString(e.getStackTrace()));
        }
    }

}

