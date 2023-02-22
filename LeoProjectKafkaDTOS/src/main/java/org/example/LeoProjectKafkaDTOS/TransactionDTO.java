package org.example.LeoProjectKafkaDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private int transactionId;

    private String type;

    private int transactionAmount;



}
