package models;

public class Personne {
    private int id;
    private String name;
    private int age;
    private int numberFriends;

    public Personne() {

    }
    public Personne(int id, String name, int age, int numberFriends) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.numberFriends = numberFriends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumberFriends() {
        return numberFriends;
    }

    public void setNumberFriends(int numberFriends) {
        this.numberFriends = numberFriends;
    }
}
