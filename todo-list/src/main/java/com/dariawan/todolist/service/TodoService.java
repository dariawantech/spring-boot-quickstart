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
