package com.work.LeoProjectPayment.service;

import com.work.LeoProjectPayment.entity.Transaction;
import com.work.LeoProjectPayment.repository.TransactionRepository;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.work.LeoProjectPayment.util.Constants.TRANSACTION_TOPIC;

@Service
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionRepository transactionRepository;

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate, TransactionRepository transactionRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionRepository = transactionRepository;
    }

    public Transaction paymentTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction(transactionDTO.getTransactionId(), transactionDTO.getType(), transactionDTO.getTransactionAmount(), new Date());
        transactionRepository.save(transaction);
        kafkaTemplate.send(TRANSACTION_TOPIC, String.valueOf(transactionDTO.getTransactionId()), transactionDTO);
        return transaction;
    }
}
