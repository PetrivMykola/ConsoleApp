package main.java.net.petriv.developer.build.skills;

import java.util.Scanner;

public class ConcreteSkill extends SkillBuilder {
    Scanner in = new Scanner(System.in);

    @Override
    void buildId() {
        System.out.println("Enter id");
        int id = in.nextInt();
        skill.setId(id);
    }

    @Override
    void buildName() {
        System.out.println("Enter name of skill: " );
        String name =  in.next();
        skill.setName(name);

    }
}
