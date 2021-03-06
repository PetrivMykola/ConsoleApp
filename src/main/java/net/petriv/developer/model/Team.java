package main.java.net.petriv.developer.model;

import java.util.Set;

public class Team {
    private int id;
    private String name;
    private Set<Developer> developers;

    public Team() {
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(int id, String name, Set<Developer> developers) {
        this.id = id;
        this.name = name;
        this.developers = developers;
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

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", developers: " + developers +
                '}';
    }
}
