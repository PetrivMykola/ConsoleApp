package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Team;

import java.io.*;
import java.util.*;

public class TeamDAO implements GeneralDAO<Team> {

    File file;
    String path = ".\\resources\\teams.txt";
    List<Team> listDev;
    Checker checker;

    public TeamDAO() {
        checker = new Checker(path);
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Team v) {
        checker.checkId(v.getId());

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getName() + ", " +
                    "Developers: " + listTeams(v.getDevelopers()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Team getById(int id) {
        String line = "";
        String readLine = "";
        String arrWords[];

        if (checker.isExistEntityInFileById(id)) {

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("[^a-zA-Z0-9]", " ");
                    line.trim();
                    arrWords = line.split("  ");

                    if (Integer.valueOf(arrWords[0]) == id) {
                        readLine = line;
                    }
                }
            } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        } else throw new NotFoundIdException("Can not find entity in file by id");
        return createTeamFromStr(readLine);
    }

    @Override
    public List<Team> getAll() {
        String line;
        listDev = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listDev.add(createTeamFromStr(line));
            }
        } catch (IOException | EmptyFileException e) {
            System.out.println(e.getMessage());
        }
        return listDev;
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
        } catch (IOException | EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }

        if (!file.delete()) System.out.println("Cannot delete file");
        if (!tempFile.renameTo(file)) System.out.println("Cannot rename file");


    }

    @Override
    public void update(Team v) {
        try {
            if (checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Team newTeam = v;
                save(newTeam);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Developer> getDevelopersForTeam(String devId) {
        devId.trim();
        DeveloperDAO developerDAO = new DeveloperDAO();
        devId = devId.replaceAll("[^a-zA-Z0-9]", " ");
        String[] arrayIdDev = devId.split(" ");
        String line = "";
        Set<Developer> devSet = new HashSet<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\developers.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line == null) break;

                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                String arrReadLine[] = line.split("  ");

                for (String s : arrayIdDev) {

                    if (s.equalsIgnoreCase(arrReadLine[0])) {
                        devSet.add(developerDAO.createDevFromStr(line));
                    }
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return devSet;
    }

    public String listTeams(Set<Developer> devSet) {
        String dev = "";
        for (Developer s : devSet)
            dev += s.getId() + ", ".toString();
        return dev;
    }

    public Team createTeamFromStr(String str) {
        String devId = "";
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        str.trim();
        String[] arrayWords = str.split("  ");

        if (arrayWords.length >= 4) {

            for (int i = 3; i <= arrayWords.length - 1; i++)
                devId += arrayWords[i] + " ";

        }

        Team team = new Team(Integer.valueOf(arrayWords[0]), arrayWords[1]);
        team.setDevelopers(getDevelopersForTeam(devId.trim()));

        return team;
    }
}
