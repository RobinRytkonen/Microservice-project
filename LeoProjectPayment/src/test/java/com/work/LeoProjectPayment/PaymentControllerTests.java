package com.work.LeoProjectPayment;

import com.google.gson.Gson;
import com.work.LeoProjectPayment.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.work.LeoProjectPayment.TestConstants.TRANSACTION_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("payment-test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@AutoConfigureMockMvc
class PaymentControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void should_send_topic_and_save_transaction() throws Exception {

        Gson gson = new Gson();
        String dtoRequest = gson.toJson(TRANSACTION_DTO);

        this.mvc
                .perform(post("/api/transaction").contentType(MediaType.APPLICATION_JSON_VALUE).content(dtoRequest))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(1, transactionRepository.findAll().size());

    }
}
