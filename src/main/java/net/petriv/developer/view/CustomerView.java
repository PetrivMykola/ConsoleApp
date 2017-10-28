package main.java.net.petriv.developer.view;

import main.java.net.petriv.developer.controller.CompanyController;
import main.java.net.petriv.developer.controller.CustomerController;

import java.util.Scanner;

public class CustomerView {

    Scanner in = new Scanner(System.in);
    CustomerController customerController;
    int choice;

    public CompanyView() {
        customerController = new CompanyController();
    }

    public void action(int choise) {
        switch (choise) {
            case 1:
                customerController.saveCustomer();
                break;
            case 2:
                customerController.showListCustomer();
                break;
            case 3:
                customerController.updateCustomer();
                break;
            case 4:
                customerController.deleteCustomer();
                break;
        }
        menu();
    }

    public void menu(){
        try {
            System.out.println("*************************************");
            System.out.println(" 1 - Create New Company And Save: ");
            System.out.println(" 2 - Show List Companies in File: ");
            System.out.println(" 3 - Update Company: ");
            System.out.println(" 4 - Delete Company: ");
            System.out.println("***Please Enter Number Of Your Choise:***");
            choice = in.nextInt();
            action(choice);

        } catch(Exception e){
            System.out.println(e.getMessage());
            menu();
        }
    }
}
