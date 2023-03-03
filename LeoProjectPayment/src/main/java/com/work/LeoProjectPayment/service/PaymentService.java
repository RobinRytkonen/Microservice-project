package com.work.LeoProjectPayment.service;

import com.work.LeoProjectPayment.entity.Transaction;
import com.work.LeoProjectPayment.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.work.LeoProjectPayment.util.Constants.TRANSACTION_TOPIC;

@Service
public class PaymentService {

    private static final Logger log = LogManager.getLogger(PaymentService.class);

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

    public Transaction getTransactionHistoryById(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            log.info("No registered player with that id!");
            return null;
        }
        return transaction.get();
    }
}
