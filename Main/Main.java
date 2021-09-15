package Main;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;





public class Main {
    public static void main(String[] args) {
        while(true){
            logIn();

        }


    }

    // 显示登陆界面
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

        boolean isValid = checkIsValid(email, password);
        if(isValid == false){
            System.out.println(" -Incorrect account password!");
        }


        //验证邮箱密码是不是正确的 需要开一个方法写验证
        //       验证失败 登陆失败 提醒失败 再次要求登陆账户密码，要写个循环
        //       验证成功 登陆成功进入 提醒成功 并进入主界面
        //                 显示主界面
    }



    //主界面的选项 1 显示所有的诊所
    //主界面的选项 2 Manage appointment
    //主界面的选项 3 退出登录
    //读取文件 读取诊所txt信息 和 用户名密码的txt的数据
    //诊所详细信息界面
    //选项1 Make appointment
    //选项2 返回上级菜单

    // 验证邮箱密码正确与否，先验证admin然后验证pation
    public static boolean checkIsValid(String email, String password){

        boolean isValid = false;
        try{
            ArrayList<String> aList = readFileToString("admin.txt");
            for(String str:aList){
                String [] split = str.split(",");
                if (split[0].equals(email) && split[1].equals(password)){
                    isValid = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isValid == false){
            ArrayList<String> uList = readFileToString("pation.txt");
            for(String st:uList){
                String [] split = st.split(",");
                if(split[0].equals(email) && split[1].equals(password)){
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }


    // 文件读取方法，读取之后处理一下解码，不是空的啥的（就是先进行一下简单的通用处理）
    public static ArrayList<String> readFileToString(String fileName){
        File inputFile = new File(fileName);
        ArrayList<String> result = new ArrayList<String>();
        try{
            if (inputFile.isFile() && inputFile.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(inputFile), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = "";
                while ((lineText = bufferedReader.readLine()) != null){
                    if(!lineText.trim().equals("")){
                        result.add(lineText);
                    }
                }
                read.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}


