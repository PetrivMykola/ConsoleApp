package main.java.net.petriv.developer.model;

import java.util.Set;

public class Company {
    private int id;
    private String name;
    private Set<Project> projects;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(int id, String name, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return name + ", ";
    }
}
