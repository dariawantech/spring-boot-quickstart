package com.dariawan.todolist.controller;

import com.dariawan.todolist.domain.Todo;
import com.dariawan.todolist.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoRestController {
    
    @Autowired
    private TodoService todoService;
    
    @GetMapping(value = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Todo> now() {
        return todoService.findAll();
    }    
}
