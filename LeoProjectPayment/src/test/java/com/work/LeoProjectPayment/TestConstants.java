package com.work.LeoProjectPayment;

import org.example.LeoProjectKafkaDTOS.TransactionDTO;

public class TestConstants {

    private TestConstants() {
    }

    public static final TransactionDTO TRANSACTION_DTO = new TransactionDTO(1,"deposit", 50000.0);

}
