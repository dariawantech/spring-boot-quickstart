package com.dariawan.todolist.controller;

import com.dariawan.todolist.domain.Todo;
import com.dariawan.todolist.exception.BadResourceException;
import com.dariawan.todolist.exception.ResourceNotFoundException;
import com.dariawan.todolist.service.TodoService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoRestController {
   
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TodoService todoService;
    
    @GetMapping(value = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Todo>> now() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/api/todos/{todoId}")
    public ResponseEntity<Todo> findTodoById(@PathVariable long todoId) {
        try {
            Todo book = todoService.findById(todoId);
            return ResponseEntity.ok(book);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }
    
    @PostMapping(value = "/api/todos")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) throws URISyntaxException {
        try {
            Todo newTodo = todoService.save(todo);
            return ResponseEntity.created(new URI("/rest/v1/books/" + newTodo.getTodoId()))
                    .body(todo);
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
