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
