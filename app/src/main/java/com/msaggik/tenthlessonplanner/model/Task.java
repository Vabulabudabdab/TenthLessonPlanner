package com.msaggik.tenthlessonplanner.model;

public class Task {
    // поля сущности
    private String id; // поле идентификатора записи в планера
    private String name; // поле названия записи в планере
    private String description; // поле описания записи в планере

    // конструктор
    public Task(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
