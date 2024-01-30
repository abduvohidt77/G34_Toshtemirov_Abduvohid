package org.example;

import org.example.domain.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static UserService userService = UserServiceImpl.getInstance();
    static Scanner scStr = new Scanner(System.in);
    static Scanner scNum = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        printHello();
        while (true) {
            printLoginMenu();
            System.out.print("What you gonna do?: ");
            int put = scNum.nextInt();
            if (put == 0) break;
            switch (put) {
                case 1 -> {
                    System.out.print("Enter your full name :");
                    String fullName = scStr.nextLine();
                    System.out.print("Enter your age :");
                    int age = scNum.nextInt();
                    System.out.print("Enter your email :");
                    String email = scStr.nextLine();
                    String confirm = userService.confirm(email);
                    System.out.print("We have sent confirmation code, put it here :");
                    String conCode = scStr.nextLine();
                    boolean onFlag = true;
                    while (onFlag) {
                        if (conCode.equals(confirm)) {
                            System.out.println("Success !");
                            onFlag = false;
                        }
                    }
                    System.out.print("Create new username :");
                    String username = scStr.nextLine();
                    System.out.print("Create a new password :");
                    String password = scStr.nextLine();
                    User user = new User(fullName, email, age, username, password);
                    var register = userService.register(user);
                    if (register != null) {
                        System.out.println("Registered successfully !" + '\n');
                        printMainMenu();
                    }
                    System.out.println("There is something went wrong !" + '\n');
                } case 2 -> {
                    System.out.print("Enter your username :");
                    String username = scStr.nextLine();
                    System.out.print("Enter your password :");
                    String password = scStr.nextLine();
                    var login = userService.login(username, password);
                    if (login != null) {
                        System.out.println("Successfully !" + '\n');
                        printMainMenu();
                    }
                    System.out.println("Something went wrong !" + '\n');
                }
                default -> System.out.println("Enter only numbers that given in the menu !");
            }
        }
    }

    private static void printMainMenu() throws IOException, InterruptedException {
        while (true) {
            printUses();
            System.out.print("What you gonna do?: ");
            int put = scNum.nextInt();
            if (put == 0) break;
            switch (put) {
                case 1 -> {
                    System.out.print("Enter your email to get new password :");
                    String email = scStr.nextLine();
                    String confirm = userService.confirm(email);
                    System.out.print("We have sent confirmation code, put it here :");
                    String conCode = scStr.nextLine();
                    boolean onFlag = true;
                    while (onFlag) {
                        if (conCode.equals(confirm)) {
                            System.out.println("Success !");
                            onFlag = false;
                        }
                    }
                    System.out.print("Enter your username (to verify it's you) :");
                    String username = scStr.nextLine();
                    System.out.print("Enter your new password here :");
                    String password = scStr.nextLine();
                    userService.changePassword(username, password);
                } case 2 -> {
                    System.out.print("Enter a month number (above 1-12) :");
                    int month = scNum.nextInt();
                    System.out.print("Enter a day (above 1-31) :");
                    int day = scNum.nextInt();
                    userService.getInformationAboutDate(month, day);
                }
                default -> System.out.println("Enter only numbers that given in the menu !");
            }
        }
    }

    private static void printUses() {
        System.out.println("""
                [-- Welcome to Main Menu --]
                
                1.Change password
                2.Get information from data
                0.Back to log menu
                [--------------------------]
                """);
    }

    private static void printLoginMenu() {
        System.out.println("""
                [-- Register Menu --]
                
                1.Register
                2.Login (if you have an account)
                0.Leave Application
                [-------------------]
                """);
    }

    private static void printHello() {
        System.out.println('\n' + """
                WELCOME TO MY EXAM PROJECT !!!
                """);
    }
}