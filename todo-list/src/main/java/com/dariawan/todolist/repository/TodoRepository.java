package com.dariawan.todolist.repository;

import com.dariawan.todolist.domain.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    
}
