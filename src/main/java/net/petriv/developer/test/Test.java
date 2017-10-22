package main.java.net.petriv.developer.test;

import main.java.net.petriv.developer.build.developers.ConcreteDeveloper;
import main.java.net.petriv.developer.build.developers.DirectorDeveloper;
import main.java.net.petriv.developer.dao.DeveloperDAOImp;
import main.java.net.petriv.developer.model.Developer;

public class Test {
    public static void main(String[] args) {
        DirectorDeveloper director = new DirectorDeveloper();
        director.setBuilder(new ConcreteDeveloper());
        Developer developer = director.buildDeveloper();
        System.out.println(developer);

        DeveloperDAOImp daoImp = new DeveloperDAOImp();
        daoImp.save(developer);
    }
}
