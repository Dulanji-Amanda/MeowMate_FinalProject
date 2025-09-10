











package lk.ijse.gdse.meowmate_backend.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceController {

    private final JavaMailSender mailSender;

    public EmailSenderServiceController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSightingEmail(String toEmail, Long catId, String location) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("matemeow36@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("🐱 New Lost Cat Sighting Reported!");

            // HTML email content
            String body = "<div style=\"font-family:Arial,sans-serif; color:#333; line-height:1.5;\">"
                    + "<h2 style=\"color:#1E90FF;\">New Lost Cat Sighting!</h2>"
                    + "<p>Hello,</p>"
                    + "<p>A new lost cat sighting has been reported.</p>"
                    + "<table style=\"border-collapse: collapse; width: 100%; max-width: 400px;\">"
                    + "<tr><td style=\"padding:8px; border:1px solid #ddd;\"><strong>Cat ID:</strong></td>"
                    + "<td style=\"padding:8px; border:1px solid #ddd;\">" + catId + "</td></tr>"
                    + "<tr><td style=\"padding:8px; border:1px solid #ddd;\"><strong>Location:</strong></td>"
                    + "<td style=\"padding:8px; border:1px solid #ddd;\">" + location + "</td></tr>"
                    + "</table>"
                    + "<p>Thank you,<br><strong>MeowMate Team</strong></p>"
                    + "</div>";

            helper.setText(body, true); // true = HTML

            mailSender.send(message);
            System.out.println("Email sent to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }












    // New: send an adoption request email to the pet owner

    public void sendAdoptionRequestEmail(String ownerEmail, Long petId, String messageText, String adopterEmail){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setFrom("matemeow36@gmail.com");
            helper.setTo(ownerEmail);
            helper.setSubject("💛 New Adoption Request for Pet #" + petId);

            String body = "<div style='font-family:Arial,sans-serif; color:#333;'>"
                    + "<h2 style='color:#1E90FF;'>You have a new adoption request!</h2>"
                    + "<p>Your pet with ID <strong>" + petId + "</strong> has received an adoption request.</p>"
                    + "<p><strong>Message from adopter (" + escapeHtml(adopterEmail) + "):</strong></p>"
                    + "<div style='padding:12px; border-left:4px solid #1E90FF; background:#f8f9ff;'>"
                    + "<p>" + (messageText==null||messageText.isBlank()? "<em>(no message)</em>": escapeHtml(messageText)) + "</p>"
                    + "</div>"
                    + "<p>You can contact the adopter directly at: <strong>" + escapeHtml(adopterEmail) + "</strong></p>"
                    + "<p>Thank you,<br><strong>MeowMate Team</strong></p>"
                    + "</div>";

            helper.setText(body,true);
            mailSender.send(message);
        } catch(MessagingException e){
            e.printStackTrace();
        }
    }

    private String escapeHtml(String s){
        if(s==null) return "";
        return s.replaceAll("&","&amp;")
                .replaceAll("<","&lt;")
                .replaceAll(">","&gt;")
                .replaceAll("\"","&quot;")
                .replaceAll("'","&#39;");
    }

}
























//
//
//package lk.ijse.gdse.meowmate_backend.controller;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailSenderServiceController {
//
//    private final JavaMailSender mailSender;
//
//    public EmailSenderServiceController(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void sendSightingEmail(String toEmail, Long catId, String location) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setFrom("matemeow36@gmail.com");
//            helper.setTo(toEmail);
//            helper.setSubject("🐱 New Lost Cat Sighting Reported!");
//
//            String body = "<div style=\"font-family:Arial,sans-serif; color:#333;\">"
//                    + "<h2>New Lost Cat Sighting!</h2>"
//                    + "<p>A lost cat you reported has a new sighting.</p>"
//                    + "<p><strong>Cat ID:</strong> " + catId + "</p>"
//                    + "<p><strong>Location:</strong> " + location + "</p>"
//                    + "<p>Thank you,<br>MeowMate Team</p></div>";
//
//            helper.setText(body, true);
//            mailSender.send(message);
//
//            System.out.println("Email sent to " + toEmail);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            System.out.println("Failed to send email to " + toEmail);
//        }
//    }
//}
