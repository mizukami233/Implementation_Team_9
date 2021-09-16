package View;

import Controller.controller;
import Model.Branches;

import java.util.ArrayList;
import java.util.Scanner;

public class view {

    public static void main(String[] args) {

        boolean flag = true;
        while (flag) {
            logInSc();
            String email = askEmail();
            String password = askPassword();
            boolean isValid = controller.checkIsValid(email, password);
            if (isValid == false) {
                //验证失败 登陆失败 提醒失败 再次要求登陆账户密码
                System.out.println(" -Incorrect account password!");
            } else {
                //如果正确进入主程序，提醒成功
                flag = false;
                System.out.println(" -Login Success!");
            }
        }

        //邮箱密码正确后跳出登陆的循环进入主界面的循环
        mainScreen();
        int choice = controller.getChoice();
        switch (choice){
            case 1:
                brancheLiTitle();
                ArrayList<Branches> bList = controller.getBranceList();
                int numberBranch = brList(bList);
                int brancheChoice = controller.getBranchesChoice(numberBranch);



                break;

            case 2:
                //这里留着给你插入 Manage Appointment的方法
                break;

            case 0:
                //退出程序
                break;
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
        System.out.println("0. Log out");
        System.out.println();
    }

    //branches 界面的标题
    private static void brancheLiTitle(){
        System.out.println("=========================================");
        System.out.println("               Branches List             ");
        System.out.println("=========================================");
        System.out.println();
    }


    //循环 打印出所有诊所的名字 并且返回数量 可以用来检测用户一会儿的选择
    private static int brList(ArrayList<Branches> bList) {
        int i = 0;
        while (i < bList.size()) {
            System.out.println((i + 1) + "." + bList.get(i).getName());
            i++;
        }
        return i;
    }




}
