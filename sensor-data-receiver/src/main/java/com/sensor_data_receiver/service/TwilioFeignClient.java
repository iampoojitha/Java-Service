package com.sensor_data_receiver.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "twilio", url = "${twilio.url}")
public interface TwilioFeignClient {

    final String ACCOUNTS_ACCOUNT_SID_MESSAGES_JSON = "/Accounts/{AccountSid}/Messages.json";

    @PostMapping(ACCOUNTS_ACCOUNT_SID_MESSAGES_JSON)
    String sendSMS(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("AccountSid") String accountSid,
            @RequestParam("To") String to,
            @RequestParam("From") String from,
            @RequestParam("Body") String body
    );
}
