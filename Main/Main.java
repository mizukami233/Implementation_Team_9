package Main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        logIn();

    }

    private static void logIn() {
        System.out.println("=========================================");
        System.out.println("     Monash Pation Management System     ");
        System.out.println("=========================================");
        System.out.println("   ***Please enter user credentials***   ");
        System.out.println("      ");
        System.out.println("   -Enter your Email:   ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        System.out.println("      ");
        System.out.println("   -Enter your Password:   ");
        Scanner sc2 = new Scanner(System.in);
        String password = sc2.nextLine();
    }

}