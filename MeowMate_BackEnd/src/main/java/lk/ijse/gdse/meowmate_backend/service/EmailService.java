package lk.ijse.gdse.meowmate_backend.service;

public interface EmailService {
    void sendAdoptionRequestEmail(String ownerEmail, Long catId, String message, String adopterEmail, String catName);
}
