package com.work.LeoProjectPayment.controller;

import com.work.LeoProjectPayment.entity.Transaction;
import com.work.LeoProjectPayment.service.PaymentService;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/transaction")
    public Transaction paymentTransaction(@RequestBody TransactionDTO transactionDTO) {
        return paymentService.paymentTransaction(transactionDTO);
    }

}
