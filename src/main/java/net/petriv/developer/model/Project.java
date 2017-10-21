package main.java.net.petriv.developer.model;

import java.util.Set;

public class Project {
    private Long id;
    private String name;
    private Set<Team> teams;

    public Project(Long id, String name, Set<Team> teams) {
        this.id = id;
        this.name = name;
        this.teams = teams;
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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teams=" + teams +
                '}';
    }
}
