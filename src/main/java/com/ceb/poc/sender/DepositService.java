package com.ceb.poc.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by chenw13 on 09/08/2017.
 */
@Component
@EnableBinding(TranSource.class)
public class DepositService {

    private MessageChannel output;

    @Autowired
    public DepositService(@Qualifier("account_balance") MessageChannel output) {
        this.output = output;
    }


    public Transaction deposit() {
        Random r = new Random(System.currentTimeMillis());
        Transaction tran = new Transaction(r.nextInt(8000000),"will", r.nextLong());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(tran);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonStr);

        this.output.send(MessageBuilder.withPayload(jsonStr).build());
        return tran;
    }
}
