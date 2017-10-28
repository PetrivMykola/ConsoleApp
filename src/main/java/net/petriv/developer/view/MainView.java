package main.java.net.petriv.developer.view;

import main.java.net.petriv.developer.dao.SkillDAO;

import java.util.Scanner;

public class MainView {
    DeveloperView developerViewv;
    SkillView skillViewv;
    TeamView teamViewv;
    ProjectView projectViewv;
    CompanyView companyViewv;
    CustomerView customerViewv;

    public MainView() {
        developerViewv = new DeveloperView();
        skillViewv = new SkillView();
        teamViewv = new TeamView();
        projectViewv = new ProjectView();
        companyViewv = new CompanyView();
        customerViewv = new CustomerView();
}

    Scanner in = new Scanner(System.in);
    int mainChoise;

    public void mainMenu() {
        System.out.println("****************************************");
        System.out.println("1. Work with 'Developer' ");
        System.out.println("2. Work with 'Skill' ");
        System.out.println("3. Work with 'Team' ");
        System.out.println("4. Work with 'Project' ");
        System.out.println("5. Work with 'Company' ");
        System.out.println("6. Work with 'Customer' ");
        System.out.println("Enter number of your choise: ");
        mainChoise = in.nextInt();
        action(mainChoise);

    }

    public void action(int choise) {
        switch (choise) {
            case 1:
                developerViewv.menu();
                break;
            case 2:
                skillViewv.menu();
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.mainMenu();

    }

}

