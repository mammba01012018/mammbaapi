package src.main.java.mammba.core.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import src.main.java.mammba.model.EmailModel;

/**
 * This utility is meant for sending out emails.
 *
 * @author Mardolfh
 *
 */
@Component
public class EmailUtility {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    private static final Logger LOGGER = Logger.getLogger(EmailUtility.class);

    public void sendEmail(EmailModel emailModel){
        MimeMessage message = this.sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(emailModel.getToRecipient());
            helper.setText(emailModel.getBodyMessage());
            helper.setSubject(emailModel.getSubject());
            helper.setFrom(this.fromEmail);

            this.sender.send(message);
            LOGGER.info("email temp to " + emailModel.getToRecipient());

        } catch (MessagingException e) {
            LOGGER.error("Error sending email to: " + emailModel.getToRecipient(), e);
        }



    }

}
