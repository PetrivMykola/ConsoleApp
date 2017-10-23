package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.*;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Skill;

import java.io.*;
import java.util.*;

public class DeveloperDAOImp implements DAO<Developer> {

    static File file;
    String path = ".\\resources\\developers.txt";
    List<Developer> listDev;
    Checker checker;

    public DeveloperDAOImp() {
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

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                if (Character.getNumericValue(line.charAt(0)) == id) {
                    readLine = line;
                } else throw new NotFoundIdException("Id does not exist in file");
            }
        } catch (IOException | NullPointerException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
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
        String[] arrayWords = str.split(", ");
        Developer dev = new Developer(Integer.valueOf(arrayWords[0]), arrayWords[1], arrayWords[2],
                arrayWords[3], Integer.valueOf(arrayWords[4]));   // setDev.add(arrayWords[5]));
        return dev;
    }

    public Set<Skill> getSkillsForDeveloper(String devSkills) {
        String[] arrayIdSkills = devSkills.split(", ");
        String line = "";
        String readLine = "";
        Set<Skill> skillSet = new HashSet<>();
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\skils.txt"))) {

                while ((line = reader.readLine()) != null & (i < arrayIdSkills.length) ) {

                        if (Character.getNumericValue(line.charAt(0)) ==  Integer.valueOf(arrayIdSkills[i])) {
                            readLine = line;
                            String[] arrayWords = readLine.split(", ");
                            skillSet.add(new Skill(Integer.valueOf(arrayWords[0]), arrayWords[1]));
                            i++;
                        }
                }
            } catch(IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        return skillSet;
    }

    public String listSkills(Set<Skill> skillSet) {
        String skill="";
        for (Iterator iter = skillSet.iterator(); iter.hasNext();) {
            skill += iter.next();
        }
        return skill;
    }
}

