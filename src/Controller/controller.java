package Controller;

import Model.Branches;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class controller {



    // 调用读取文件的数据然后 验证邮箱密码正确与否，先验证admin然后验证pation
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

    //验证用户输入的选项 只能输入0 1 2 返回int
    public static int getChoice(){
        System.out.println("Please make a choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-2]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-2.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }

    public static int getChoice2(){
        System.out.println("Please make a choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-1]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-2.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }


    //通过调用文件读取方法然后获得诊所的list,并且把数据根据逗号处理好生成一个arraylist
    public static ArrayList<Branches> getBranceList(){
        ArrayList<Branches> branches = new ArrayList<Branches>();
        try{
            ArrayList<String> bList = readFileToString("branche.txt");
            for (String str:bList){
                str = str.trim();
                String [] splitBList = str.split(",");
                Branches br = new Branches();
                br.setId(Integer.parseInt(splitBList[0]));
                br.setName(splitBList[1]);
                br.setPostcode(Integer.parseInt(splitBList[2]));
                br.setOpeningHour(splitBList[3]);
                br.setPhone(Integer.parseInt(splitBList[4]));
                br.setStreetName(splitBList[5]);
                br.setSuburb(splitBList[6]);
                branches.add(br);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return branches;

    }


    //验证用户选择诊所时 输入的选项是不是数字
    public static int getBranchesChoice(){
        System.out.println("Please make a choice: ");
        Scanner sc=new Scanner(System.in);
        String branchesChoice = sc.nextLine();
        for(String regex = "^[0-9]+$"; !branchesChoice.matches(regex); branchesChoice = sc.next()) {
            System.out.print("Please enter correct number: ");
        }
        return Integer.valueOf(branchesChoice);

    }

    //验证用户选择诊所时 输入的是不是 在有效范围内
    public static Branches getBrancheByChoice2(int choice){
        ArrayList<Branches> bList = getBranceList();
        if (bList.size()<=choice || choice < 0){
            return null;
        }
        return bList.get(choice-1);
    }


}
