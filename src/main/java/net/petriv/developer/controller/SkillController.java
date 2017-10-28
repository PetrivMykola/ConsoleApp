package main.java.net.petriv.developer.controller;

import main.java.net.petriv.developer.dao.SkillDAO;
import main.java.net.petriv.developer.model.Skill;

import java.util.Scanner;

public class SkillController {

    Scanner in = new Scanner(System.in);
    Skill skill;
    SkillDAO dao;

    public SkillController() {
        dao = new SkillDAO();
    }

    public void saveSkill() {
        dao.save(createNewSkill());
        System.out.println("Create and save Skill was successful");
        System.out.println("##############################");
    }

    public void showListSkill() {
        System.out.println(dao.getAll());
        System.out.println("##############################");
    }

    public void updateSkill() {
        System.out.println("Enter id skill for update: ");
        int id = in.nextInt();
        System.out.println("Skill for udate: " + dao.getById(id));
        Skill newDev = enterFieldsForSkill(dao.getById(id));
        dao.update(newDev);
        System.out.println("###################################");
    }

    public void deleteSkill() {
        System.out.println("Enter id skill for delete: ");
        int id = in.nextInt();
        dao.delete(id);
        System.out.println("##############################");

    }

    public Skill createNewSkill() {
        skill = new Skill();
        System.out.println("Enter id:");
        skill.setId(in.nextInt());
        System.out.println("Enter name:");
        skill.setName(in.next());
        return skill;
    }

    public Skill enterFieldsForSkill(Skill skill) {
        skill = new Skill();
        System.out.println("Enter id:");
        skill.setId(in.nextInt());
        System.out.println("Enter name:");
        skill.setName(in.next());
        return skill;

    }
}
