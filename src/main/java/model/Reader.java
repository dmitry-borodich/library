package model;

public class Reader {
    private int id;
    private String name;
    private int age;
    private String passportNumber;

    public Reader(int id, String name, int age, String passportNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.passportNumber = passportNumber;
    }

    public int getId() {
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
