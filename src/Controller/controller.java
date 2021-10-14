package Controller;

import Model.*;
import connector.ReadFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class controller {
    private static ArrayList<Appointment> appointments;
    private ArrayList<Reason> reasons;
    private ReadFile readfile;

    public controller(){
        appointments = new ArrayList<>();
        reasons = new ArrayList<>();
        readfile = new ReadFile();

    }

    public controller(ArrayList<Appointment> appointments, ArrayList<Reason> reasons, ReadFile readfile) {
        controller.appointments = appointments;
        this.reasons = reasons;
        this.readfile = readfile;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        controller.appointments = appointments;
    }

    public ArrayList<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(ArrayList<Reason> reasons) {
        this.reasons = reasons;
    }

    public ReadFile getDbConnector() {
        return readfile;
    }

    public void setDbConnector(ReadFile readfile) {
        this.readfile = readfile;
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


    // Call the data of the read file and verify whether the mailbox password is correct or not, first verify admin and then verify pation
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

    public static int isCheckinTime(int appid, Date checkinTime, ArrayList<Appointment> appointments, PatientQueue patientQueue){
        List<Appointment> result = null;
        result = appointments.stream().filter(appointment -> appid == appointment.getAppId()).collect(Collectors.toList());
        if (result.size() == 0){
            return 0;
        }
        
        int minutes = (int) ((result.get(0).getAppTime().getTime() - checkinTime.getTime()) / (1000 * 60)); 
        if (minutes <= 10) {
            result.get(0).setCheckInTime(checkinTime);
            patientQueue.enQueue(result.get(0));
            patientQueue.updateSeq(checkinTime);
            return 2;
        } else {
            return 1;
        }
    }



    // File reading method, after reading, process the decoding, is not empty (for simple general processing)
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

    //To verify the options entered by the user, can only enter 0 1 2 3 4 return int
    public static int getChoice(){
        System.out.println("Please make a choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-4]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-4.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }

    //To verify the options entered by the user
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

    //To verify the entered by the user
    public static int getChoice3(){
        System.out.println("Please input a GP id to choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^(0?[1-9]|[1-9][0-9])+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.println("Please input again: (you can't choose 100 GP because he is a spare GP)");
        }
        return Integer.valueOf(choice);
    }

    //To verify the options entered by the user
    public static int getChoice4(){
        System.out.println("Please make a choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        for(String regex = "^[0-1]+$"; !choice.matches(regex); choice = sc.next()) {
            System.out.print("Input can only be number 0-1.");
            System.out.print("Please input again: ");
        }
        return Integer.valueOf(choice);
    }

    //By calling the file reading method, the clinic's list is obtained,
    //and the data is processed according to the comma to generate an arraylist.
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

    //Verify that the option entered when the user selects a clinic is a number
    public static int getBranchesChoice(){
        System.out.println("Please make a choice: ");
        Scanner sc=new Scanner(System.in);
        String branchesChoice = sc.nextLine();
        for(String regex = "^[1-9]+$"; !branchesChoice.matches(regex); branchesChoice = sc.next()) {
            System.out.print("Please enter correct number: ");
        }
        return Integer.valueOf(branchesChoice);

    }

    //Verify that what the user entered when choosing a clinic is within the valid range.
    public static Branches getBrancheByChoice2(int choice){
        ArrayList<Branches> bList = getBranchesList();
        if (bList.size()<choice || choice <= 0){
            return null;
        }
        return bList.get(choice-1);
    }

    public void loadApp(){  //get the data from the file
        readfile.setFileName("appointment.txt");
        ArrayList<String> lines = readfile.readDataFromFile();
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
            appointment.setAppBeginTime(lineArray[6]);
            appointment.setAppEndTime(lineArray[7]);
            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(lineArray[5] + ' ' + lineArray[8]);
                appointment.setCheckInTime(date);
            } catch (Exception e) {
                appointment.setCheckInTime(null);
                // e.printStackTrace();
            }
            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(lineArray[5] + ' ' + lineArray[6]);

                appointment.setAppTime(date);
            } catch (Exception e) {
                appointment.setAppTime(null);
                // e.printStackTrace();
            }
            appointments.add(appointment);
        }
    }

    public void loadReason(){
        readfile.setFileName("reason.txt");
        ArrayList<String> lines = readfile.readDataFromFile();
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



    public Date stringToDate(String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            return simpleDateFormat.parse(dateStr);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
