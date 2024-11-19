package br.dev.zancanela.quickcup_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuickCupApiApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        QuickCupApiApplication.main(new String[]{});
        assertNotNull(context);
    }

}