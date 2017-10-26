package main.java.net.petriv.developer.test;

import main.java.net.petriv.developer.dao.*;
import main.java.net.petriv.developer.model.*;

public class Test {
    public static void main(String[] args) {
TeamDAO teamDAO = new TeamDAO();
Team team = new Team(1, "Team" ,teamDAO.getDevelopersForTeam("1"));

//teamDAO.save(team);

        System.out.println(teamDAO.getById(1));

    }
}

