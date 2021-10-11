package Controller;

import Model.*;
import connector.DbConnector;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;


public class controller {
    private static ArrayList<Appointment> appointments;
    private ArrayList<Reason> reasons;
    private DbConnector dbConnector;

    public controller(){
        appointments = new ArrayList<>();
        reasons = new ArrayList<>();
        dbConnector = new DbConnector();

    }


    public controller(ArrayList<Appointment> appointments, ArrayList<Reason> reasons,DbConnector dbConnector) {
        this.appointments = appointments;
        this.reasons = reasons;
        this.dbConnector = dbConnector;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(ArrayList<Reason> reasons) {
        this.reasons = reasons;
    }

    public DbConnector getDbConnector() {
        return dbConnector;
    }

    public void setDbConnector(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }


    public static int setNewAppId(){
        int lastId = 0;
        for (Appointment appointment : appointments)
        {
            if (appointment.getAppId() > lastId){
                lastId = appointment.getAppId();
            }
        }
        return lastId;
    }


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
        for(String regex = "^[0-3]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-3.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }

    public static int getChoice2(){
        System.out.println("Please make a choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-1]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-1.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }

    public static int getChoice3(){
        System.out.println("Please input a GP id to choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-99]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.println("Please input again: (you can't choose 100 GP because he is a spare GP)");
        }
        return Integer.valueOf(choice);
    }

    //通过调用文件读取方法然后获得诊所的list,并且把数据根据逗号处理好生成一个arraylist
    public static ArrayList<Branches> getBranchesList(){
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


    //Sort according to the name of the branches
    public static ArrayList<Branches> sortBranchesList(ArrayList<Branches> bList){
        Collections.sort(bList, new Comparator<Branches>() {
            @Override
            public int compare(Branches o1, Branches o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return bList;
    }




    //验证用户选择诊所时 输入的选项是不是数字
    public static int getBranchesChoice(){
        System.out.println("Please make a choice: ");
        Scanner sc=new Scanner(System.in);
        String branchesChoice = sc.nextLine();
        for(String regex = "^[1-9]+$"; !branchesChoice.matches(regex); branchesChoice = sc.next()) {
            System.out.print("Please enter correct number: ");
        }
        return Integer.valueOf(branchesChoice);

    }

    //验证用户选择诊所时 输入的是不是 在有效范围内
    public static Branches getBrancheByChoice2(int choice){
        ArrayList<Branches> bList = getBranchesList();
        if (bList.size()<choice || choice <= 0){
            return null;
        }
        return bList.get(choice-1);
    }

    public void loadApp(){  //get the data from the file
        dbConnector.setFileName("appointment.txt");
        dbConnector.setHasHeader(false);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            //1,1,1,1,1,2021/09/22,10:00
            String[] lineArray = line.split(",");
            Appointment appointment = new Appointment();
            appointment.setAppId(Integer.parseInt(lineArray[0]));
            appointment.setPatId(Integer.parseInt(lineArray[1]));
            appointment.setBranchId(Integer.parseInt(lineArray[2]));
            appointment.setGpId(Integer.parseInt(lineArray[3]));
            appointment.setReasonId(Integer.parseInt(lineArray[4]));
            //Date
            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd").parse(lineArray[5]);
                appointment.setAppDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointments.add(appointment);

        }
    }

    public void loadReason(){
        dbConnector.setFileName("reason.txt");
        dbConnector.setHasHeader(false);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            String[] lineArray = line.split(",");
            Reason reason = new Reason(Integer.parseInt(lineArray[0]),lineArray[1],lineArray[2]);
            reasons.add(reason);
        }
    }

    public static int[] generateReason(Date startDate, Date endDate){
        int reason1Count = 0;
        int reason2Count = 0;
        int reason3Count = 0;

        for(Appointment appointment:appointments)
        {
            Date date = appointment.getAppDate();
            //input date >= startDate and input date <= endDate
            if((date.after(startDate)|| date.equals(startDate))&&(date.before(endDate)||date.equals(endDate)))
            {
                int reasonID = appointment.getReasonId();
                if(reasonID == 1)
                    reason1Count++;
                else if(reasonID ==2)
                    reason2Count++;
                else
                    reason3Count++;

            }
        }
        return new int[]{reason1Count,reason2Count,reason3Count};
    }

//    public void readApp(){ //get the data from the file
//        dbConnector.setFileName("appointment.txt");
//        dbConnector.setHasHeader(false);
//        ArrayList<String> lines = dbConnector.readDataFromFile();
//        for(String line:lines){
//            String[] lineArray = line.split(",");
//            //int appId, int reasonId, int patId, int branchId, int gpId, Date appDate, String appBeginTime,String appEndTime
//            appointments.add(new Appointment(Integer.parseInt(lineArray[0]),Integer.parseInt(lineArray[1]),
//                    Integer.parseInt(lineArray[2]),Integer.parseInt(lineArray[3]),
//                    Integer.parseInt(lineArray[4]),stringToDate(lineArray[5]) ,lineArray[6],lineArray[7]));
//        }
//    }

//    public static boolean findGpTimeFrames(Date date, LocalTime beginTime, LocalTime endTime, int gId) {
//        boolean gpEmpty = true;
//        boolean gpNone = true;
//        for (Appointment appointment: appointments) { //这个人没任何预约，不出现在预约表
//            if (appointment.getGpId() == gId) {
//                gpNone = false;
//            }
//        }
//        if (!gpNone) { //这个人有预约，但是日期不冲突
//            for (Appointment appointment : appointments) {
//                if (appointment.getGpId() == gId && appointment.getAppDate().equals(date)) {
//                    gpEmpty = false;
//                }
//            }
//            if (!gpEmpty) {  //这个人有预约，但是日期冲突，所以才比对预约时间
//                for (Appointment appointment : appointments) {
//                    if (appointment.getGpId() == gId) { //医生有预约
//                        if (appointment.getAppDate().equals(date)) {
//                            //且当天预约时间不冲突
//                            LocalTime appLocalStartTime = LocalTime.parse(appointment.getAppBeginTime());
//                            LocalTime appLocalEndTime = LocalTime.parse(appointment.getAppEndTime());
//                            if (appLocalEndTime.isBefore(beginTime) || appLocalStartTime.isAfter(endTime)) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }

    public Date stringToDate(String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            return simpleDateFormat.parse(dateStr);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    //public void
}
