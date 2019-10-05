package com.dariawan.todolist.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "todo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Todo implements Serializable, Comparable<Todo> {

    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    private Date creationDate;

    private String description;

    private int priority;

    private boolean completed;

    private Date completionDate;

    private Date dueDate;

    @Basic(fetch = FetchType.LAZY)
    @Column(length = 4000)
    private String notes;

    private boolean hasNotes;

    @Override
    public int compareTo(Todo that) {
        int order = that.getPriority() - this.getPriority();
        if (this.isCompleted()) {
            order += 10000;
        }
        if (that.isCompleted()) {
            order -= 10000;
        }
        if (order == 0) {
            order = (this.getDescription() + this.getTodoId()).compareTo(that
                    .getDescription()
                    + that.getTodoId());
        }
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Todo todo = (Todo) o;

        return !(todoId != null ? !todoId.equals(todo.todoId) : todo.todoId != null);
    }

    @Override
    public int hashCode() {
        return todoId != null ? todoId.hashCode() : 0;
    }
}
