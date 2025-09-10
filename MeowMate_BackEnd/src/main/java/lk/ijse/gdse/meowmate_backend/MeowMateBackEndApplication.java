//package lk.ijse.gdse.meowmate_backend;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class MeowMateBackEndApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(MeowMateBackEndApplication.class, args);
//    }
//
//
//}






package lk.ijse.gdse.meowmate_backend;

import lk.ijse.gdse.meowmate_backend.controller.EmailSenderServiceController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MeowMateBackEndApplication {

    @Autowired
    private EmailSenderServiceController emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(MeowMateBackEndApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void sendTestEmail() {
//        String recipient = "matemeow36@gmail.com";
//        String subject = "Hello from MeowMate!";
//        String body = "Hello! This is a test email from MeowMate backend.";
//        emailSenderService.sendSimpleEmail(recipient, subject, body);
//    }


}


