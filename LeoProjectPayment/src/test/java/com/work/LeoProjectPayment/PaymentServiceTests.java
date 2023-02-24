package com.work.LeoProjectPayment;

import com.work.LeoProjectPayment.entity.Transaction;
import com.work.LeoProjectPayment.repository.TransactionRepository;
import com.work.LeoProjectPayment.service.PaymentService;
import org.example.LeoProjectKafkaDTOS.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.work.LeoProjectPayment.util.Constants.TRANSACTION_TOPIC;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTests {

    TransactionDTO transactionDTO = new TransactionDTO(1,"deposit", 50000);

    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    PaymentService paymentService;

    @Captor
    ArgumentCaptor<Transaction> playerArgumentCaptor;

    @Test
    void should_save_transaction_and_send_topic() {

        when(kafkaTemplate.send(TRANSACTION_TOPIC, String.valueOf(transactionDTO.getTransactionId()), transactionDTO)).thenReturn(null);

        paymentService.paymentTransaction(transactionDTO);

        verify(kafkaTemplate, times(1)).send(TRANSACTION_TOPIC, String.valueOf(transactionDTO.getTransactionId()), transactionDTO);
        verify(transactionRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(1, playerArgumentCaptor.getValue().getTransactionId());
        Assertions.assertEquals("deposit", playerArgumentCaptor.getValue().getType());
        Assertions.assertEquals(50000, playerArgumentCaptor.getValue().getTransactionAmount());
    }
}
