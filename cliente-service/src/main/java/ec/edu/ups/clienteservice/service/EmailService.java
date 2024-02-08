package ec.edu.ups.clienteservice.service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.host}")
    private String primaryHost;

    @Value("${spring.mail.host-secondary}")
    private String secondaryHost;

    // Valores de timeout en milisegundos
    @Value("${spring.mail.properties.mail.smtp.timeout:10000}") // 10 segundos
    private int smtpTimeout;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout:5000}") // 5 segundos
    private int smtpConnectionTimeout;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@example.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        try {
            configureMailSender(primaryHost);
            emailSender.send(message);
        } catch (MailException mailException) {
            configureMailSender(secondaryHost);
            emailSender.send(message);
        }
    }

    private void configureMailSender(String host) {
        JavaMailSenderImpl senderImpl = (JavaMailSenderImpl) emailSender;
        senderImpl.setHost(host);
        
        // Configurar timeouts
        Properties props = senderImpl.getJavaMailProperties();
        props.put("mail.smtp.connectiontimeout", smtpConnectionTimeout);
        props.put("mail.smtp.timeout", smtpTimeout);
        props.put("mail.smtp.writetimeout", smtpTimeout); // Timeout para esperar la respuesta
    }
}
