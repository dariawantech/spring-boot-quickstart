/**
 * Spring Boot Quickstart - To Do List (https://www.dariawan.com/tutorials/spring/spring-boot-quick-start/)
 * Copyright (C) 2019 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
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
    public ResponseEntity<List<Todo>> findAll() {
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
            return ResponseEntity.created(new URI("/api/todos/" + newTodo.getTodoId()))
                    .body(todo);
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
