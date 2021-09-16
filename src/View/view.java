package View;

import Controller.controller;

import java.util.Scanner;

public class view {

    public static void main(String[] args) {

        logInSc();
        String email = askEmail();
        String password = askPassword();
        boolean isValid = controller.checkIsValid(email, password);
        if(isValid == false) {
            //验证失败 登陆失败 提醒失败 再次要求登陆账户密码
            System.out.println(" -Incorrect account password!");
        }
        else {
            //如果正确进入主程序，提醒成功
            System.out.println(" -Login Success!");
            System.out.println();
            mainScreen();
        }


    }


    //打印登录界面
    private static void logInSc() {
        System.out.println("=========================================");
        System.out.println("     Monash Patient Management System     ");
        System.out.println("=========================================");
        System.out.println("   ***Please enter user credentials***   ");
        System.out.println("      ");
    }

    //打印请求输入邮箱
    private static String askEmail() {
        System.out.println("   -Enter your Email:   ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        return email;
    }

    //打印请求输入密码
    private static String askPassword() {
        System.out.println("      ");
        System.out.println("   -Enter your Password:   ");
        Scanner sc2 = new Scanner(System.in);
        String password = sc2.nextLine();
        return password;
    }


    //打印显示主界面
    private static void mainScreen() {
        System.out.println("=========================================");
        System.out.println("          Appointment Management         ");
        System.out.println("=========================================");
        System.out.println();
        System.out.println();
        System.out.println("       ***Please Make a Choice***        ");
        System.out.println("1. Display Branches");
        System.out.println();
        System.out.println("2. Manage Appointment");
        System.out.println();
        System.out.println("3. Log out");
        System.out.println();
        System.out.println("Please make a choice: ");
    }

}
