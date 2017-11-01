package main.java.net.petriv.developer.controller;

import main.java.net.petriv.developer.dao.TeamDAO;
import main.java.net.petriv.developer.model.Team;

import java.util.Scanner;

public class TeamController {

    Scanner in = new Scanner(System.in);
    Team team;
    TeamDAO dao;

    public TeamController() {
        dao = new TeamDAO();
    }

    public void saveTeam() {
        dao.save(createNewTeam());
        System.out.println("Create and save Team was successful");
        System.out.println("##############################");
    }

    public void showListTeam() {
        System.out.println(dao.getAll());
        System.out.println("##############################");
    }

    public void updateTeam() {
        System.out.println("Enter id Team for update: ");
        int id = in.nextInt();
        System.out.println("Team for udate: " + dao.getById(id));
        Team newTeam = enterFieldsForTeam(dao.getById(id));
        dao.update(newTeam);
        System.out.println("###################################");
    }

    public void deleteTeam() {
        System.out.println("Enter id Team for delete: ");
        int id = in.nextInt();
        dao.delete(id);
        System.out.println("##############################");
    }

    public Team createNewTeam() {
        team = new Team();
        System.out.println("Enter id:");
        team.setId(in.nextInt());
        System.out.println("Enter name:");
        team.setName(in.next());
        System.out.println("Enter id developers for team: ");
        team.setDevelopers(dao.getDevelopersForTeam(in.next()));
        return team;
    }

    public Team enterFieldsForTeam(Team team) {
        System.out.println("Enter id:");
        team.setId(in.nextInt());
        System.out.println("Enter name:");
        team.setName(in.next());
        System.out.println("Enter id developers for team");
        team.setDevelopers(dao.getDevelopersForTeam(in.next()));
        return team;
    }
}
