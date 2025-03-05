package model;

import java.util.UUID;

public class Reader  {
    private UUID id;
    private String name;
    private int age;
    private String passportNumber;

    public Reader(UUID id, String name, int age, String passportNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.passportNumber = passportNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
}
