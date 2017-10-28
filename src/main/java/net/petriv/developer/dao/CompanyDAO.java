package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Company;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Project;
import main.java.net.petriv.developer.model.Team;

import java.io.*;
import java.util.*;

public class CompanyDAO implements DAO<Company> {

    File file;
    String path = ".\\resources\\companys.txt";
    List<Company> listCompanys;
    Checker checker;

    public CompanyDAO() {
        checker = new Checker(path);
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Company v) {
        checker.checkId(v.getId());

        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getName() + ", " +
                    "Projects: " + listProjecs(v.getProjects()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Company getById(int id) {
        String line = "";
        String readLine = "";
        String arrWords[];

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            checker.checkId(id);

            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                arrWords = line.split("  ");

                if (Integer.valueOf(arrWords[0]) == id) {
                    readLine = line;
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
        return createCompanyFromStr(readLine);
    }

    @Override
    public List<Company> getAll() {
        String line;
        listCompanys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listCompanys.add(createCompanyFromStr(line));
            }
        } catch (IOException | EmptyFileException e) {
            System.out.println(e.getMessage());
        }
        return listCompanys;
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
    public void update(Company v) {
        try {
            if(checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Company newCompany = v;
                save(newCompany);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Project> getProjectsForCompany(String projectId) {
        projectId.trim();
        ProjectDAO projectDAO = new ProjectDAO();
        projectId = projectId.replaceAll("[^a-zA-Z0-9]", " ");
        String[] arrayIdTeam = projectId.split(" ");
        String line = "";
        Set<Project> projectSet = new HashSet<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\projects.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line == null) break;

                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                String arrReadLine[] = line.split("  ");

                for (String s : arrayIdTeam) {

                    if (s.equalsIgnoreCase(arrReadLine[0]))
                        projectSet.add(projectDAO.createProjectFromStr(line));
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return projectSet;
    }

    public String listProjecs(Set<Project> projectSet) {
        String project = "";
        for (Project s : projectSet)
            project += s.getId() + ", ".toString();
        return project;
    }

    public Company createCompanyFromStr(String str) {
        String devId = "";
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        str.trim();
        String[] arrayWords = str.split("  ");

        if (arrayWords.length >=4) {

            for (int i = 3; i <= arrayWords.length - 1; i++)
                devId += arrayWords[i] + " ";
        }

        Company company = new Company(Integer.valueOf(arrayWords[0]), arrayWords[1]);
        company.setProjects(getProjectsForCompany(devId.trim()));

        return company;
    }
}
