package springbook.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        System.out.println(simpleMailMessage.getTo());
        System.out.println(simpleMailMessage.getFrom());
        System.out.println(simpleMailMessage.getSubject());
        System.out.println(simpleMailMessage.getText());
    }

    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

    }
}
