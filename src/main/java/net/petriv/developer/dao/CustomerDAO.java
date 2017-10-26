package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Company;
import main.java.net.petriv.developer.model.Customer;
import main.java.net.petriv.developer.model.Developer;
import main.java.net.petriv.developer.model.Project;

import java.io.*;
import java.util.*;

public class CustomerDAO implements DAO<Customer> {

    File file;
    String path = ".\\resources\\customers.txt";
    List<Customer> listCustomer;
    Checker checker;

    public CustomerDAO() {
        checker = new Checker(path);
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Customer v) {
        checker.checkId(v.getId());

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(v.getId() + ", " + v.getFirstName() + ", " + v.getLastName() + ", " + v.getAddress() +
                  ", " + "Projects: " + listCustomers(v.getProjects()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Customer getById(int id) {
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
        return createCustomerFromStr(readLine);
    }

    @Override
    public List<Customer> getAll() {
        String line;
        listCustomer = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            checker.checkFile(file);

            while ((line = reader.readLine()) != null) {
                listCustomer.add(createCustomerFromStr(line));
            }
        } catch (IOException | EmptyFileException e) {
            System.out.println(e.getMessage());
        }
        return listCustomer;
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
    public void update(Customer v) {
        try {
            if (checker.isExistEntityInFileById(v.getId())) {
                delete(v.getId());
                Customer newCustomer = v;
                save(newCustomer);

            } else throw new NotFoundIdException("Cannot find developers in file for update");
        } catch (EmptyFileException | NotFoundIdException e) {
            System.out.println(e.getMessage());
        }
    }




    public String listCustomers(Set<Project> projectSet) {
        String customers="";
        for (Iterator iter = projectSet.iterator(); iter.hasNext();) {
            customers += iter.next();
        }
        return customers;
    }

    public Customer createCustomerFromStr(String str) {
        String[] arrayWords = str.split(", ");
        Customer newCustomer = new Customer(Integer.valueOf(arrayWords[0]), arrayWords[1], arrayWords[2], arrayWords[3]);
        return newCustomer;
    }

    public Set<Project> getProjectsForCustomer(String dev) {
        String[] arrayIdSkills = dev.split(", ");
        String line = "";
        String readLine = "";
        Set<Project> projectSet = new HashSet<>();
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\projects.txt"))) {

            while ((line = reader.readLine()) != null & (i < arrayIdSkills.length) ) {

                if (Character.getNumericValue(line.charAt(0)) ==  Integer.valueOf(arrayIdSkills[i])) {
                    readLine = line;
                    String[] arrayWords = readLine.split(", ");
                    projectSet.add(new Project(Integer.valueOf(arrayWords[0]), arrayWords[1]));
                    i++;
                }
            }
        } catch(IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return projectSet;
    }


}

