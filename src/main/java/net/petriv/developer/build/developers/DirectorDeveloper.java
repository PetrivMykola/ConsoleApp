package main.java.net.petriv.developer.build.developers;

import main.java.net.petriv.developer.model.Developer;

public class DirectorDeveloper {
    DeveloperBuilder builder;

    public void setBuilder(DeveloperBuilder builder) {
        this.builder = builder;
    }

   public Developer buildDeveloper() {
        builder.createDeveloper();
        builder.buildId();
        builder.buildFirstName();
        builder.buildLastName();
        builder.buildSpecialty();
        builder.buildSalary();
        builder.builSkills();
        Developer developer = builder.getDeveloper();
        return developer;
    }
}
