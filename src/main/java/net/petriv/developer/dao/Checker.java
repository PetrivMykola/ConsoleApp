package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.model.Skill;

import main.java.net.petriv.developer.exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Checker{

     File file;

   public Checker (String path) {
       file = new File(path);
   }

    public  void checkId(int id) {
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

    public  void checkFile(File file) {
        if (file.length() == 0) throw new EmptyFileException("File is empty");
    }

    public  boolean isExistEntityInFileById(int id ) {
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

