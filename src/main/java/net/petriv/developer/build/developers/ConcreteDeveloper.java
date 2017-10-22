package main.java.net.petriv.developer.build.developers;

import main.java.net.petriv.developer.build.skills.ConcreteSkill;
import main.java.net.petriv.developer.build.skills.DirectorSkill;
import main.java.net.petriv.developer.model.Skill;

import java.util.Scanner;

public class ConcreteDeveloper extends DeveloperBuilder {
    Scanner in = new Scanner(System.in);

    @Override
    void buildId() {
        System.out.println("Enter id: ");
        int id = in.nextInt();
        developer.setId(id);
    }

    @Override
    void buildFirstName() {
        System.out.println("Enter firsName: ");
        String first = in.next();
        developer.setFirstName(first);

    }

    @Override
    void buildLastName() {
        System.out.println("Enter lastName: ");
        String last = in.next();
        developer.setLastName(last);

    }

    @Override
    void buildSpecialty() {
        System.out.println("Enter specialty: ");
        String specialty = in.next();
        developer.setSpecialty(specialty);

    }

    @Override
    void builSkills() {
        System.out.println("Enter skills");
        DirectorSkill directorSkill = new DirectorSkill();
        directorSkill.setBuilder(new ConcreteSkill());
        Skill skill = directorSkill.buildSkill();
        developer.setSkills(skill);
    }

    @Override
    void buildSalary() {
        System.out.println("Enter salary: ");
        int salary = in.nextInt();
        developer.setSalary(salary);

    }
}
