package com.sensor_data_receiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class NotificationService {

    @Autowired
    private TwilioFeignClient twilioFeignClient;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    @Value("${phone.number}")
    private String toPhoneNumber;

    public void sendSMS(String messageBody) {
        String credentials = accountSid + ":" + authToken;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        String authorizationHeader = "Basic " + base64Credentials;

        twilioFeignClient.sendSMS(
                authorizationHeader,
                accountSid,
                toPhoneNumber,
                fromPhoneNumber,
                messageBody
        );
    }
}
