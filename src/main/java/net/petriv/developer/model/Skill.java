package main.java.net.petriv.developer.model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Skill {
    private int id;
    private String name;
    Set setSkill;

    public Skill() {
        setSkill = new HashSet();
    }

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void saveSkill(Skill skill) {
        setSkill.add(skill);
    }

    public boolean getSkillfromSet(Skill skill) {
        return setSkill.contains(skill);
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

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
