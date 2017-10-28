package main.java.net.petriv.developer.test;

import main.java.net.petriv.developer.dao.*;
import main.java.net.petriv.developer.model.*;

public class Test {
    public static void main(String[] args) {
TeamDAO teamDAO = new TeamDAO();
Team team = new Team(1, "Team" ,teamDAO.getDevelopersForTeam("1"));
        CompanyDAO companyDAO = new CompanyDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        ProjectDAO  projectDAO = new ProjectDAO();

        //Project project = new Project(1, "facebook", projectDAO.getTeamsForProject("1"));
        //Company company = new Company(1, "SoftServe",companyDAO.getProjectsForCompany("1"));

        Customer customer = new Customer(1, "Mike", "Jackon", "newyor", customerDAO.getCompanyForCustomer("1"));

       //companyDAO.save(company);
        //customerDAO.save(customer);
      //  projectDAO.save(project);
      //  customerDAO.save(customer);

         System.out.println(customerDAO.getById(1));

    }
}

