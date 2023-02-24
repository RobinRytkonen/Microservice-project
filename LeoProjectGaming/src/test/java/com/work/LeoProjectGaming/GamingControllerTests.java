package com.work.LeoProjectGaming;

import com.work.LeoProjectGaming.repository.BettingHistoryRepository;
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
@TestPropertySource("/application-gaming-test.yml")
@ActiveProfiles("gaming-test")
@Sql(value = "classpath:reset-data.sql", executionPhase = BEFORE_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@AutoConfigureMockMvc
class GamingControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    BettingHistoryRepository bettingHistoryRepository;

    @Test
    void should_send_topic_and_save_bet() throws Exception {

        String dto = "{\"playerId\":2,\"betAmount\":100}";

        this.mvc
                .perform(post("/api/bet").contentType(MediaType.APPLICATION_JSON_VALUE).content(dto))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(1, bettingHistoryRepository.findAll().size());
    }
}
