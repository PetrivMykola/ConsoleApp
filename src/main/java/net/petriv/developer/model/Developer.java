package main.java.net.petriv.developer.model;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private String specialty;
    private Set<Skill> skills;
    private int salary;

    public Developer() {
        skills = new TreeSet<>();
    }

    public Developer(int id, String firstName, String lastName, String specialty, int salary){//, Set<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.skills = skills;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Skill skills) {

        this.skills.add(skills);
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", skills=" + skills +
                ", salary=" + salary +
                '}';
    }
}
