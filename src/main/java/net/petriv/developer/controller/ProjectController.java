package main.java.net.petriv.developer.controller;

import main.java.net.petriv.developer.dao.ProjectDAO;
import main.java.net.petriv.developer.model.Project;

import java.util.Scanner;

public class ProjectController {
    
    Scanner in = new Scanner(System.in);
    Project project;
    ProjectDAO dao;

    public ProjectController() {
        dao = new ProjectDAO();
    }

    public void saveProject() {
        dao.save(createNewProject());
        System.out.println("Create and save Project was successful");
        System.out.println("##############################");
    }

    public void showListProject() {
        System.out.println(dao.getAll());
        System.out.println("##############################");
    }

    public void updateProject() {
        System.out.println("Enter id Project for update: ");
        int id = in.nextInt();
        System.out.println("Project for udate: " + dao.getById(id));
        Project newProject = enterFieldsForTeam(dao.getById(id));
        dao.update(newProject);
        System.out.println("###################################");
    }

    public void deleteProject() {
        System.out.println("Enter id Project for delete: ");
        int id = in.nextInt();
        dao.delete(id);
        System.out.println("##############################");
    }

    public Project createNewProject() {
        project = new Project();
        System.out.println("Enter id:");
        project.setId(in.nextInt());
        System.out.println("Enter name:");
        project.setName(in.next());
        System.out.println("Enter id teams for company: ");
        project.setTeams(dao.getTeamsForProject(in.next()));
        return project;
    }

    public Project enterFieldsForTeam(Project project) {
        System.out.println("Enter id:");
        project.setId(in.nextInt());
        System.out.println("Enter name:");
        project.setName(in.next());
        System.out.println("Enter id teams for company: ");
        project.setTeams(dao.getTeamsForProject(in.next()));
        return project;
    }
}
