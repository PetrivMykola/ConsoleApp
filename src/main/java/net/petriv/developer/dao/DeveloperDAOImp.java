package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.IdAlredyExistException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Developer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeveloperDAOImp implements DAO<Developer> {

    File file;
    String path = ".\\resources\\developers.txt";
    List<Developer> listDev;
    Set<String> setDev;

    public DeveloperDAOImp() {
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Developer v) {
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getFirstName() +
                    ", " + v.getLastName() + ", " + v.getSpecialty() + ", " +
                    ", " + v.getSalary() + ", " + "Skills: " + v.getSkills());

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
        return null; //createDevFromStr(readLine);
    }


    @Override
    public List<Developer> getAll() {
        String line;
        listDev = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checkFile(file);

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
            checkFile(file);

            if (isExistDevInFileById(id)) {

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
            if(isExistDevInFileById(v.getId())) {
                delete(v.getId());
                Developer newDev = v;
                save(newDev);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkId(int id) {
        String line;
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            if (file.length() != 0  ) {
                while ((line = read.readLine()) != null) {
                    if (((Character.getNumericValue(line.charAt(0)) == id)))
                        throw new IdAlredyExistException("Id should be unique");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkFile(File file) {
        if (file.length() == 0) throw new EmptyFileException("File is empty");
    }

    public Developer createDevFromStr(String str) {
        String[] arrayWords = str.split(", ");
        Developer dev = new Developer(Integer.valueOf(arrayWords[0]), arrayWords[1], arrayWords[2],
                arrayWords[3], Integer.valueOf(arrayWords[4]));//, setDev.add(arrayWords[5]));
        return dev;
    }

    public boolean isExistDevInFileById(int id ) {
        boolean result = false;
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checkFile(file);

            while ((line = reader.readLine()) != null) {
                if (Character.getNumericValue(line.charAt(0)) == id) {
                    result = true;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
