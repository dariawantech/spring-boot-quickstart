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
package com.dariawan.todolist.service;

import com.dariawan.todolist.domain.Todo;
import com.dariawan.todolist.exception.BadResourceException;
import com.dariawan.todolist.exception.ResourceNotFoundException;
import com.dariawan.todolist.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    
    public Todo findById(Long id) throws ResourceNotFoundException {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo==null) {
            throw new ResourceNotFoundException("Cannot find Todo with id: " + id);
        }
        else return todo;
    }
    
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        return todos;
    }
    
    public Todo save(Todo todo) throws BadResourceException {
        if (!StringUtils.isEmpty(todo.getDescription())) {
            return todoRepository.save(todo);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save todo");
            exc.addErrorMessage("Title is null or empty");
            throw exc;
        }
    }
}
