package org.example.LeoProjectKafkaDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private int transactionId;

    private String type;

    private int transactionAmount;



}
