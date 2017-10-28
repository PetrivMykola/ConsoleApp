package main.java.net.petriv.developer.dao;


import main.java.net.petriv.developer.exceptions.*;
import main.java.net.petriv.developer.model.Skill;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkillDAO implements DAO<Skill> {

    File file;
    String path = ".\\resources\\skills.txt";
    List<Skill> listSkill;
    Checker checker;

    public SkillDAO() {
        file = new File(path);
        checker = new Checker(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Skill v) {
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getName() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Skill getById(int id) {
        String line = "";
        String readLine = "";
        checker.checkId(id);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                if (Character.getNumericValue(line.charAt(0)) == id) {
                    readLine = line;
                } else throw new NotFoundIdException("Id does not exist in file");
            }
        } catch (IOException | NullPointerException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
        return createSkillFromStr(readLine);
    }


    @Override
    public List<Skill> getAll() {
        String line;
        listSkill = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listSkill.add(createSkillFromStr(line));
            }
        } catch (IOException | EmptyFileException e) {
            System.out.println(e.getMessage());
        }
        return listSkill;
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
    public void update(Skill v) {
        try {
            if(checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Skill newDev = v;
                save(newDev);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public Skill createSkillFromStr(String str) {
        String[] arrayWords = str.split(", ");
        Skill newSkill = new Skill(Integer.valueOf(arrayWords[0]), arrayWords[1]);
        return newSkill;
    }

}