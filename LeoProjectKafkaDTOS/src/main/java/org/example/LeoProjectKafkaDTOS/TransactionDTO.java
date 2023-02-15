package org.example.LeoProjectKafkaDTOS;

import lombok.Data;

@Data
public class TransactionDTO {

    private int transactionId;

    private String type;

    private int transactionAmount;

}
