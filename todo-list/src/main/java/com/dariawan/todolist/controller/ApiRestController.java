package com.dariawan.todolist.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRestController {

    @Getter
    class JSONObject {

        private final String datetime;

        public JSONObject(String s) {
            this.datetime = s;
        }
    }

    @GetMapping(value = "/api/now", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject now() {
        return new JSONObject(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now()));
    }
}
