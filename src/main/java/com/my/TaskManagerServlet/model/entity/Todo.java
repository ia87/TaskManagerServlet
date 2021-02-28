package com.my.TaskManagerServlet.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


public class Todo {

    private long id;
    private String description;
    private LocalDate targetDate;
    private LocalDateTime started;
    private LocalDateTime finished;
    private User user;
    private LocalDateTime createdAt;

    public Todo() {
    }

    @Override
    public String toString() {
        return "TODO [" +
                "id=" + id +
                ", description=" + description +
                ", started=" + started +
                ", finished=" + finished +
                ", createdAt=" + createdAt +
                ", targetDate=" + targetDate +
                "]";
    }

    public Todo(String description, LocalDate targetDate, User user) {
        this.description = description;
        this.targetDate = targetDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDateTime getStarted() {
        return started;
    }
    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }
    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (int) (31 * result + Optional.of(id).orElse(0L));
        Optional<String> description = Optional.ofNullable(this.description);
        Optional<LocalDateTime> createdAt = Optional.ofNullable(this.createdAt);
        result = 31*result + description.hashCode();
        result = 31*result + createdAt.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (this == o) return true;
        if (o instanceof Todo) return this.id==((Todo) o).getId();
        return false;
    }

    public static final class TodoBuilder {
        private long id;
        private String description;
        private LocalDate targetDate;
        private LocalDateTime started;
        private LocalDateTime finished;
        private User user;
        private LocalDateTime createdAt;

        private TodoBuilder() {
        }

        public static TodoBuilder aTodo() {
            return new TodoBuilder();
        }

        public TodoBuilder withId(long id) {
            this.id = id;
            return this;
        }
        public TodoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
        public TodoBuilder withTargetDate(LocalDate targetDate) {
            this.targetDate = targetDate;
            return this;
        }
        public TodoBuilder withStarted(LocalDateTime started) {
            this.started = started;
            return this;
        }
        public TodoBuilder withFinished(LocalDateTime finished) {
            this.finished = finished;
            return this;
        }
        public TodoBuilder withUser(User user) {
            this.user = user;
            return this;
        }
        public TodoBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Todo build() {
            Todo todo = new Todo();
            todo.setId(id);
            todo.setDescription(description);
            todo.setTargetDate(targetDate);
            todo.setStarted(started);
            todo.setFinished(finished);
            todo.setUser(user);
            todo.setCreatedAt(createdAt);
            return todo;
        }
    }
}