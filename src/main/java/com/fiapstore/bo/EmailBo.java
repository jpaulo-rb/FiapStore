package com.fiapstore.bo;

import com.fiapstore.exception.EmailException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailBo {

    private static final String EMAIL_FROM = "joaopaulo@datac.com.br";
    private static final String APP_PASSWORD = "JpDataC#123";

    public void enviarEmail(String destinatario, String assunto, String mensagem) throws EmailException {

        boolean enviarEmail = false;

        if (enviarEmail) {
            // Dados de configuração do email
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            // Autenticação na conta de email do remetente
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
                }
            });

            // Enviar a mensagem
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL_FROM));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                message.setSubject(assunto);
                message.setText(mensagem);
                Transport.send(message);
            } catch (MessagingException e) {
                throw new EmailException("Erro ao enviar e-mail: " + e.getMessage());
            }
        }

    }
}
