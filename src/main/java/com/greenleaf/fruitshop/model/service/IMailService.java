package com.greenleaf.fruitshop.model.service;

public interface IMailService {
    void sendSimpleMail(String to, String subject, String content) throws Exception;
}
