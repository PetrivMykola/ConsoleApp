package main.java.net.petriv.developer.dao;

import main.java.net.petriv.developer.exceptions.EmptyFileException;
import main.java.net.petriv.developer.exceptions.NotFoundIdException;
import main.java.net.petriv.developer.model.Company;
import main.java.net.petriv.developer.model.Customer;

import java.io.*;
import java.util.*;

public class CustomerDAO implements GeneralDAO<Customer> {

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
                    ", " + "Companies: " + listCompanies(v.getCompanies()) + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Customer getById(int id) {
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
            } catch (IOException | NullPointerException | NotFoundIdException e) {
                System.out.println(e.getMessage());
            }
        } else throw new NotFoundIdException("Can not find entity in file by id");
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

    public String listCompanies(Set<Company> customerSet) {
        String companies = "";
        for (Company s : customerSet)
            companies += s.getId() + ", ".toString();
        return companies;
    }

    public Customer createCustomerFromStr(String str) {
        String customerId = "";
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        str.trim();
        String[] arrayWords = str.split("  ");

        if (arrayWords.length >= 6) {

            for (int i = 5; i <= arrayWords.length - 1; i++)
                customerId += arrayWords[i] + " ";
        }

        Customer customer = new Customer(Integer.valueOf(arrayWords[0]), arrayWords[1], arrayWords[2], arrayWords[3]);
        customer.setCompanies(getCompanyForCustomer(customerId.trim()));

        return customer;
    }

    public Set<Company> getCompanyForCustomer(String companyId) {
        companyId.trim();
        CompanyDAO companyDAO = new CompanyDAO();
        companyId = companyId.replaceAll("[^a-zA-Z0-9]", " ");
        String[] arrayIdTeam = companyId.split(" ");
        String line = "";
        Set<Company> companySet = new HashSet<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(".\\resources\\companys.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line == null) break;

                line = line.replaceAll("[^a-zA-Z0-9]", " ");
                line.trim();
                String arrReadLine[] = line.split("  ");

                for (String s : arrayIdTeam) {

                    if (s.equalsIgnoreCase(arrReadLine[0]))
                        companySet.add(companyDAO.createCompanyFromStr(line));
                }
            }
        } catch (IOException | NullPointerException | NotFoundIdException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return companySet;
    }


}

