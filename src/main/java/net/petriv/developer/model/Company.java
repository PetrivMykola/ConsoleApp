package main.java.net.petriv.developer.model;

import java.util.Set;

public class Company {
    private Long id;
    private String name;
    private Set<Project> projects;

    public Company(Long id, String name, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projects=" + projects +
                '}';
    }
}
