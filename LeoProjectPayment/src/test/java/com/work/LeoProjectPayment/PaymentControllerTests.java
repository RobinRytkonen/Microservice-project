package com.work.LeoProjectPayment;

import com.work.LeoProjectPayment.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-payment-test.yml")
@ActiveProfiles("payment-test")
@Sql(value = "classpath:reset-data.sql", executionPhase = BEFORE_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@AutoConfigureMockMvc
class PaymentControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void should_send_topic_and_save_transaction() throws Exception {

        String dto = "{\"transactionId\":2,\"type\":\"deposit\",\"transactionAmount\":50000.0}";

        this.mvc
                .perform(post("/api/transaction").contentType(MediaType.APPLICATION_JSON_VALUE).content(dto))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(1, transactionRepository.findAll().size());

    }
}
