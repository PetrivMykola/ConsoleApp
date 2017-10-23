package main.java.net.petriv.developer.test;

import main.java.net.petriv.developer.dao.DeveloperDAOImp;
import main.java.net.petriv.developer.dao.SkillDAO;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Skill;

public class Test {
    public static void main(String[] args) {

        //Skill skill = new Skill(1,"JAVA");
        //Skill skill2 = new Skill(2,"SQL");
        DeveloperDAOImp daoImp = new DeveloperDAOImp();
        Developer developer = new Developer(1, "Myola", "Petriv", "Java", 85,daoImp.getSkillsForDeveloper("1, 2") );

        daoImp.save(developer);

SkillDAO skillDAO = new SkillDAO();
//skillDAO.save(skill);
//skillDAO.save(skill2);

        System.out.println(daoImp.listSkills(daoImp.getSkillsForDeveloper("1, 2")));



    }
}
