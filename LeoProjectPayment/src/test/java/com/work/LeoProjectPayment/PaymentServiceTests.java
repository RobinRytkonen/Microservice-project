package com.work.LeoProjectPayment;

import com.work.LeoProjectPayment.entity.Transaction;
import com.work.LeoProjectPayment.repository.TransactionRepository;
import com.work.LeoProjectPayment.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.work.LeoProjectPayment.TestConstants.TRANSACTION_DTO;
import static com.work.LeoProjectPayment.util.Constants.TRANSACTION_TOPIC;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTests {

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

        when(kafkaTemplate.send(TRANSACTION_TOPIC, String.valueOf(TRANSACTION_DTO.getTransactionId()), TRANSACTION_DTO)).thenReturn(null);

        paymentService.paymentTransaction(TRANSACTION_DTO);

        verify(kafkaTemplate, times(1)).send(TRANSACTION_TOPIC, String.valueOf(TRANSACTION_DTO.getTransactionId()), TRANSACTION_DTO);
        verify(transactionRepository, times(1)).save(playerArgumentCaptor.capture());
        Assertions.assertEquals(TRANSACTION_DTO.getTransactionId(), playerArgumentCaptor.getValue().getTransactionId());
        Assertions.assertEquals(TRANSACTION_DTO.getType(), playerArgumentCaptor.getValue().getType());
        Assertions.assertEquals(TRANSACTION_DTO.getTransactionAmount(), playerArgumentCaptor.getValue().getTransactionAmount());
    }
}
