package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Project;
import main.java.net.petriv.developer.model.Team;

import java.io.*;
import java.util.*;

public class ProjectDAO implements DAO<Project> {

    File file;
    String path = ".\\resources\\projects.txt";
    List<Project> listProject;
    Checker checker;

    public ProjectDAO() {
        checker = new Checker(path);
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Project v) {
        checker.checkId(v.getId());

        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getName() + ", " +
                    "Teams: " + listTeams(v.getTeams()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Project getById(int id) {

        String line = "";
        String readLine = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            checker.checkId(id);

            while ((line = reader.readLine()) != null) {
                if (Character.getNumericValue(line.charAt(0)) == id) {
                    readLine = line;
                } else throw new NotFoundIdException("Id does not exist in file");
            }
        } catch (IOException | NullPointerException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
        return createProjectFromStr(readLine);
    }

    @Override
    public List<Project> getAll() {

        String line;
        listProject = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listProject.add(createProjectFromStr(line));
            }
        } catch (IOException | EmptyFileException e) {
            System.out.println(e.getMessage());
        }
        return listProject;
    }

    @Override
    public void delete(int id) {
        String line;
        File tempFile = new File(file.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             FileWriter newFileWriter = new FileWriter(tempFile)) {
            checker.checkFile(file);

            if (checker.isExistEntityInFileById(id)) {

                while ((line = reader.readLine()) != null) {

                    if (!(Character.getNumericValue(line.charAt(0)) == id)) {
                        newFileWriter.write(line + System.lineSeparator());
                        newFileWriter.flush();
                    }
                }
            } else throw new NotFoundIdException("Cannot find id in file for delete Developer");
        } catch (IOException | EmptyFileException  | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }

        if (!file.delete()) System.out.println("Cannot delete file");
        if (!tempFile.renameTo(file)) System.out.println("Cannot rename file");


    }

    @Override
    public void update(Project v) {
        try {
            if(checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Project newProject = v;
                save(newProject);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }

    }

    public Set<Team> getTeamsForProject(String teamId) {
        teamId.trim();
        TeamDAO teamDAO = new TeamDAO();
        teamId = teamId.replaceAll("[^a-zA-Z0-9]", " ");
        String[] arrayIdTeam = teamId.split(" ");
        String line = "";
        Set<Team> teamSet = new HashSet<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\teams.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line == null) break;

                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                String arrReadLine[] = line.split("  ");

                for (String s : arrayIdTeam) {

                    if (s.equalsIgnoreCase(arrReadLine[0]))
                        teamSet.add(teamDAO.createTeamFromStr(line));
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return teamSet;
    }

    public String listTeams(Set<Team> teamSet) {
        String project = "";
        for (Team s : teamSet)
            project += s.getId() + ", ".toString();
        return project;
    }

    public Project createProjectFromStr(String str) {
        String devId = "";
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        str.trim();
        String[] arrayWords = str.split("  ");

        if (arrayWords.length >=4) {

            for (int i = 3; i <= arrayWords.length - 1; i++)
                devId += arrayWords[i] + " ";
        }

        Project project = new Project(Integer.valueOf(arrayWords[0]), arrayWords[1]);
        project.setTeams(getTeamsForProject(devId.trim()));

        return project;
    }
}