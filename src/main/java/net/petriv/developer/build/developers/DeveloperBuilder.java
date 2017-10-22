package main.java.net.petriv.developer.build.developers;

import main.java.net.petriv.developer.model.Developer;

public abstract class DeveloperBuilder {
    Developer developer;

    public void createDeveloper() {
        developer = new Developer();
    }

    abstract void buildId();

    abstract void buildFirstName();

    abstract void buildLastName();

    abstract void buildSpecialty();

    abstract void builSkills();

    abstract void buildSalary();

    Developer getDeveloper() {
        return developer;
    }



}
