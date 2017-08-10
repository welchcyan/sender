package com.ceb.poc.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by chenw13 on 09/08/2017.
 */
@RestController
public class DepositController {

    private DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @RequestMapping(path = "/send", method = RequestMethod.GET)
    public ResponseEntity sendTran() throws Exception {
        return Optional.ofNullable(depositService.deposit())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }
}
