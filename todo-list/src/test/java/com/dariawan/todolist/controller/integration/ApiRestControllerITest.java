package com.dariawan.todolist.controller.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiRestControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void getNow() throws Exception {
        String sdate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(LocalDate.now());
        ResponseEntity<String> response = template.getForEntity(createURLWithPort("/api/now"),
                String.class);
        assertThat(response.getBody(), containsString(sdate));
    }
}
