package com.dariawan.todolist.service;

import com.dariawan.todolist.domain.Todo;
import com.dariawan.todolist.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }
    
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        return todos;
    }
}
