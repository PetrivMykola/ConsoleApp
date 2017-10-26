package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.*;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Skill;
import main.java.net.petriv.developer.model.Team;

import java.io.*;
import java.util.*;

public class DeveloperDAO implements DAO<Developer> {

    static File file;
    String path = ".\\resources\\developers.txt";
    List<Developer> listDev;
    Checker checker;

    public DeveloperDAO() {
        checker = new Checker(path);
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Developer v) {
        checker.checkId(v.getId());
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getFirstName() +
                    ", " + v.getLastName() + ", " + v.getSpecialty() + ", " +
                    + v.getSalary() + ", " + "Skills: " + listSkills(v.getSkills()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Developer getById(int id) {
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
        return createDevFromStr(readLine);
    }


    @Override
    public List<Developer> getAll() {
        String line;
        listDev = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listDev.add(createDevFromStr(line));
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
        } catch (IOException | EmptyFileException  | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }

        if (!file.delete()) System.out.println("Cannot delete file");
        if (!tempFile.renameTo(file)) System.out.println("Cannot rename file");

    }

    @Override
    public void update(Developer v) {
        try {
            if(checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Developer newDev = v;
                save(newDev);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public Developer createDevFromStr(String str) {
        String devId = "";
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        str.trim();
        String[] arrayWords = str.split("  ");
        if (arrayWords.length >=7) {
            for (int i = 6; i <= arrayWords.length - 1; i++)
                devId += arrayWords[i] + " ";

        }

        Developer developer = new Developer(Integer.valueOf(arrayWords[0]), arrayWords[1],
                                            arrayWords[2], arrayWords[3], Integer.valueOf(arrayWords[4]));
        developer.setSkills(getSkillsForDeveloper(devId.trim()));

        return developer;
    }


    public Set<Skill> getSkillsForDeveloper(String devSkills) {
        devSkills.trim();
        DeveloperDAO developerDAO = new DeveloperDAO();
        devSkills = devSkills.replaceAll("[^a-zA-Z0-9]", " ");
        String[] arrayIdDev = devSkills.split(" ");
        String line = "";
        Set<Skill> skillSet = new HashSet<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\skills.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line == null) break;

                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                String arrReadLine[] = line.split("  ");

                for (String s : arrayIdDev) {

                    if (s.equalsIgnoreCase(arrReadLine[0])) {
                        skillSet.add(new Skill(Integer.valueOf(arrReadLine[0]), arrReadLine[1]));
                    }
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return skillSet;
    }


    public String listSkills(Set<Skill> skillSet) {
        String skill = "";
        for (Skill s : skillSet)
            skill += s.getId() + ", ".toString();
        return skill;

    }
}