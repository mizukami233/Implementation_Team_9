package View;

import Controller.*;
import Model.Appointment;
import Model.Branches;
import Model.Gp;
import Model.PatientQueue;
import Model.Spare;

import javax.swing.text.BoxView;
import javax.swing.text.View;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class view {
    private static BranchGpDataBase branchGp;
    private static Appointmentdb appointment;
    private static Reasondb reason;
    private static BranchDataBase branch;
    private static GpDataBase gp;
    private static Appointment oneAppointment;
    private static PatientQueue patientQueue;

    public view() {
        appointment = new Appointmentdb();
        reason = new Reasondb();
        branch = new BranchDataBase();
        branchGp = new BranchGpDataBase();
        gp = new GpDataBase();
        oneAppointment = new Appointment();
        patientQueue = new PatientQueue(null, null);
    }

    public static void main(String[] args) {
        //view view = new view();
        //generate();
        myStart();
        while (true) {
            logInSc();
            String email = askEmail();
            String password = askPassword();
            boolean isValid = controller.checkIsValid(email, password);
            if (isValid == false) {
                //Verification failed. Login failed, reminding failed,
                // login account password is required again.
                System.out.println(" -Incorrect account password!");
            } else {
                //If you enter the main program correctly, the reminder is successful
                System.out.println(" -Login Success!");


                //After the email password is correct,
                // it jumps out of the login cycle and enters the main interface cycle
                boolean flag = true;
                while (flag) {
                    mainScreen();
                    int choice = controller.getChoice();
                    switch (choice) {
                        //Option 1 shows a list of clinic names
                        case 1:
                            brancheLiTitle();
                            ArrayList<Branches> bList = controller.getBranchesList();
                            ArrayList<Branches> sList = controller.sortBranchesList(bList);
                            brList(sList);

                            boolean flag3 = true;
                            int c = 0;
                            while (flag3) {
                                int brancheChoice = controller.getBranchesChoice();
                                Branches b = controller.getBrancheByChoice2(brancheChoice);
                                if (b != null) {
                                    appMange(b);
                                    flag3 = false;
                                    c = Integer.valueOf(b.getId());
                                    oneAppointment.setBranchId(c);
                                }
                            }

                            int choice2 = controller.getChoice2();
                            switch (choice2) {
                                case 1:
                                    //show all ava GP to user
                                    gpLiTitle();
                                    // BranchGpDataBase branchGp = new BranchGpDataBase();
                                    //branchGp.showBranchGp();
                                    System.out.println("branch id: " + c);
                                    //use branch id to search gpId
                                    HashSet<Integer> gpIds = branchGp.useBranchFindGp(c);
                                    // GpDataBase gp = new GpDataBase();
                                    // for gpIds to get each gpId
                                    Iterator<Integer> iterator = gpIds.iterator();
                                    while (iterator.hasNext()) {
                                        Integer gpid = iterator.next();
                                        System.out.print(gp.showGpByGpId(gpid) +
                                                branchGp.showGpWorkingTimeByGidBid(gpid, c)); //use gid bid to show GpWorkingTime
                                    }
                                    choiceRightGpid(gpIds);




                                    break;

                                case 0: // Back to main menu
                                    break;
                            }


                            break;


                        case 2:
                            //Method of Manage Appointment
                            appM();
                            int managechoice2 = controller.getChoice2();
                            switch (managechoice2) {
                                case 1:
                                    generate();//generate function
                                    int managechoic3 = controller.getChoice2();
                                    if (managechoic3 == 0) {
                                        break;
                                    }

                                case 0: // Back to main menu
                                    break;
                            }
                            break;

                        case 3:
                            //choose random GP
                            System.out.println("We will auto set you a GP to make a appointment");
                            System.out.println("Input 1 to auto make a appointment, input 0 to back");
                            try {
                                Scanner sc = new Scanner(System.in);
                                int input = sc.nextInt();
                                if(input == 1){
                                    System.out.println("We auto asset you to a GP who has least booking");
                                    System.out.println("This is the GP's detail: 100,Peter,spare,04037022436,General");
                                    appointment.writeRandomGp();
                                    System.out.println("   Appointment successful    ");
                                    break;
                                }
                                else if(input == 0){
                                    break;
                                }
                                else if (input != 1 && input != 0){
                                    System.out.println("you can only choose 0 or 1");
                                    break;
                                }
                            }
                            catch (Exception e){
                                System.out.println("Error, Please check your spare file");
                                break;
                            }


                        case 4:
                            Date checkinTime = null;
                            Scanner sc = new Scanner(System.in);
                            int appid;
                            while (true) {
                                try {
                                    System.out.println("Please input your appointment id:");
                                    appid = Integer.parseInt(sc.nextLine());
                                    break;
                                }
                                catch (Exception e){
                                    System.out.println("you can only input integer");
                                }
                            }

                            while (true) {
                                try {
                                    System.out.println("If you want to check in, please input the time now (yyyy/MM/dd HH:mm) e.g.(2021/09/23 12:41). Notice that you can only check in 10 minutes before the appointment time");
                                    String checkin = sc.nextLine();
                                    checkinTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(checkin);
                                    break;
                                }
                                catch (Exception e){
                                    System.out.println("Wrong time format! Please input again!");
                                }
                            }
                            // System.out.println("#######");
                            // for (Appointment appointment : appointment.getAppointments()) {
                            //     System.out.println(appointment.getAppId());
                            // }
                            ArrayList<Appointment> appointmentCopy = (ArrayList<Appointment>) appointment.getAppointments().clone();
                            patientQueue = new PatientQueue(appointmentCopy, checkinTime);
                            int isCheckin = controller.isCheckinTime(appid, checkinTime, appointment.getAppointments(), patientQueue);

                            if (isCheckin == 0) {
                                System.out.println("Sorry. We cannot find this appointment record.");
                                continue;
                            } else if (isCheckin == 1) {
                                System.out.println("More than 10 minutes before the appointment time. Please wait.");
                                continue;
                            } else {
                                System.out.println("Check in Success! You can press 1 to see how many people are in front of the queue or press 0 to back");
                                int input = sc.nextInt();
                                if(input == 1){
                                    System.out.println("There are " + String.valueOf(patientQueue.countFront(appid)) + " people in front of you");
                                    break;
                                } else if(input == 0){
                                    break;
                                } else {
                                    System.out.println("you can only choose 0 or 1");
                                    break;
                                }
                            }

                        //sign out
                        case 0:
                            flag = false;
                            break;
                    }
                }
            }
        }

    }

    private static void choiceRightGpid(HashSet<Integer> gpIds){
        while(true){
            // choose gpId
            int choice3 = controller.getChoice3();
            if(gpIds.contains(choice3)){

                while (true){
                    // Choose appointment date
                    // 1. Enter 0 date = null
                    // 2. An input date date = day of the week
                    System.out.println(" Please enter the date. If you enter 0, you will return to the previous level.   ");
                    Date date = returnAppDate();
                    if (date == null){
                        break;
                    }
                    DayOfWeek week = setAppointmentDate(date);
                    // Compare the appointment date with the work date (day of the week)
                    boolean okDate = branchGp.findGpWorkingDate(week, choice3);
                    oneAppointment.setAppDate(date);
                    // If the comparison result is true, select the appointment time period
                    if(okDate == true) {
                        // Start choosing appointment time
                        LocalTime appBeginTime = appBegin();
                        if (appBeginTime == null){
                            break;
                        }
                        int userReason = userChooseReasonId();
                        LocalTime appEndTime = appEnd(appBeginTime,userReason);
                        boolean okTime = appointment.findGpTimeFrames(date, appBeginTime, appEndTime, choice3);
                        // true Show that the appointment is successful, otherwise it will jump out of the program
                        if (okTime == true){
                            oneAppointment.setAppBeginTime(appBeginTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                            oneAppointment.setAppEndTime(appEndTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                            oneAppointment.setGpId(choice3);
                            appointment.add(oneAppointment);
                            appointment.writeAppointmentData(oneAppointment, userReason); //txt 添加
                            System.out.println("   Appointment successful   ");
                        }
                        else {
                            System.out.println("    Appointment failed    ");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            else {
                System.out.println("Please input correct GP id!");
            }
        }
    }



    private static void choiceAvaTime(){    //After choosing the GP, you need to choose the appropriate day of the appointment,
        // otherwise you will return to the previous level and re-select gp
        System.out.println("Please choose GP");
        String back = "-1";
        while (back.equals("-1")){
            Scanner sc = new Scanner(System.in);
            back = sc.nextLine();

        }

    }

    //Print login interface
    private static void logInSc() {
        System.out.println("=========================================");
        System.out.println("     Monash Patient Management System     ");
        System.out.println("=========================================");
        System.out.println("   ***Please enter user credentials***   ");
        System.out.println("      ");
    }

    //Print request input email
    private static String askEmail() {
        System.out.println("   -Enter your Email:   ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        return email;
    }

    //Print request to enter password
    private static String askPassword() {
        System.out.println("      ");
        System.out.println("   -Enter your Password:   ");
        Scanner sc2 = new Scanner(System.in);
        String password = sc2.nextLine();
        return password;
    }


    //Print display main interface
    private static void mainScreen() {
        System.out.println("=========================================");
        System.out.println("          Appointment Management         ");
        System.out.println("=========================================");
        System.out.println();
        System.out.println();
        System.out.println("       ***Please Make a Choice***        ");
        System.out.println("1. Display Branches(you can use this choice to make a new appointment)");
        System.out.println();
        System.out.println("2. Manage Appointment");
        System.out.println();
        System.out.println("3. choose a random GP");
        System.out.println();
        System.out.println("4. Check in");
        System.out.println();
        System.out.println("0. Log out");
        System.out.println();
    }

    //The title of the branches interface
    private static void brancheLiTitle() {
        System.out.println("=========================================");
        System.out.println("               Branches List             ");
        System.out.println("=========================================");
        System.out.println();
    }

    //GP page title
    private static void gpLiTitle() {
        System.out.println("=========================================");
        System.out.println("               Gp List             ");
        System.out.println("=========================================");
        System.out.println("choose one GP which you want to booking");
        System.out.println("=====!! input 0 to return to main page !!=====");
        System.out.println();
    }

    //By printing out the detailed information of the clinic that the user wants
    private static void appMange(Branches b) {
        System.out.println("================================================");
        System.out.println("             Appointment Mangement              ");
        System.out.println("================================================");
        System.out.println("                Detail of Branch                ");
        System.out.println(" ");
        System.out.println("Brance name:" + b.getName());
        System.out.println("Postcode:" + b.getPostcode());
        System.out.println("OpeningHour:" + b.getOpeningHour());
        System.out.println("Phone:" + b.getPhone());
        System.out.println("StreetName:" + b.getStreetName());
        System.out.println("Subub:" + b.getSuburb());
        System.out.println(" ");
        System.out.println("1. Make Appointment");
        System.out.println(" ");
        System.out.println("0. Back");
    }


    //Loop, print out the names of all clinics and return the number,
    // which can be used to check the user’s choices for a while
    private static void brList(ArrayList<Branches> bList) {
        int i = 0;
        while (i < bList.size()) {
            System.out.println((i + 1) + "." + bList.get(i).getName());
            i++;
        }
    }

    private static void appM() {
        System.out.println("================================================");
        System.out.println("             Appointment Management              ");
        System.out.println("================================================");
        System.out.println("                Manage Appointment                ");
        System.out.println();
        System.out.println("       ***Please Make a Choice***        ");
        System.out.println("1. Generate report");
        System.out.println();
        System.out.println("0. Back");
        System.out.println();
    }

    public static void myStart() { //init the data
        branch = new BranchDataBase();
        gp = new GpDataBase();
        branchGp = new BranchGpDataBase();
        oneAppointment = new Appointment();
        appointment = new Appointmentdb();
        reason = new Reasondb();
        branch.showBranch();
        gp.showGp();
        branchGp.showBranchGp();
        appointment.loadApp();
        reason.loadReason();
        appointment.randomGp();
    }

    public static void start() {
        appointment = new Appointmentdb();
        reason = new Reasondb();
        appointment.loadApp();
        reason.loadReason();
    }

    public static String askStartDate(){
        System.out.println("Please enter the start date (E.g 2021/01/01) : ");
        // ask for input
        Scanner scanner = new Scanner(System.in);
        //get input data
        String startDateString = scanner.nextLine();
        for(boolean i = isDate(startDateString); i == false;){// if data is not valid, always execute loop
            System.out.println(isDate(startDateString));
            System.out.println("Your date format is wrong");
            System.out.println("Please try again: ");//ask to input again
            startDateString = scanner.nextLine();
            i = isDate(startDateString);//check if date is valid
        }
        return startDateString;
    }
    public static String askEndDate(){
        System.out.println("Please enter the end date (E.g 2021/01/01) : ");
        // ask for input
        Scanner scanner2 = new Scanner(System.in);
        //get input data
        String endDateString = scanner2.nextLine();
        for(boolean i = isDate(endDateString); i == false;){ // if data is not valid, always execute loop
            System.out.println(isDate(endDateString));
            System.out.println("Your date format is wrong");
            System.out.println("Please try again: ");//ask to input again
            endDateString = scanner2.nextLine();
            i = isDate(endDateString);//check if input date is valid
        }
        return endDateString;
    }


    public static void generate() {
        start();
        //ask for input first
        String start = askStartDate();
        String end = askEndDate();

        try {
            //transform string to date
            Date startDate = new SimpleDateFormat("yyyy/MM/dd").parse(start);
            Date endDate = new SimpleDateFormat("yyyy/MM/dd").parse(end);
            //get the number of each type of reason
            int[] results = appointment.generateReason(startDate,endDate);
            int sum = results[0]+results[1]+results[2];
            for(int i =0;i< reason.getReasons().size();i++){
                DecimalFormat df = new DecimalFormat("#.##");// get 2 decimal
                double num;
                if(results[i]<=0||sum == 0)//if there is no appointment, the result is 0
                {num = 0;}
                else
                //calculate in double format and transform to percentage format
                {num= (results[i]*1.0)/sum*100.0;}
                //print result
                System.out.println(reason.getReasons().get(i).getType() + ": " + df.format(num)+"%");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(" ");
        System.out.println("0.Back");
    }

    private static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private static DayOfWeek setAppointmentDate(Date date) {  //return day of week
        //Date date = returnAppDate();
        DayOfWeek tranDate;
            LocalDate localDate = convertToLocalDateViaSqlDate(date); //date to localdate
            tranDate = localDate.getDayOfWeek();
        return tranDate;
    }


    private static Date returnAppDate() {  //return 2021-01-01

        Date date = null;
        System.out.println("Please input a date (E.g 2021-01-01) : ");
        try {
            Scanner sc = new Scanner(System.in);
            String appointmentDate = sc.nextLine();
            if(appointmentDate.equals("0")){
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(appointmentDate);
//            LocalDate localDate = LocalDate.parse(appointmentDate);
//            //System.out.println(localDate.getDayOfWeek());
//            date = localDate.getDayOfWeek();
        } catch (Exception e) {
            System.out.println("Error! Please choose branch to make appointment again");
        }
        return date;
    }

    private static LocalTime appBegin() {  //Set app start time
        LocalTime beginTime = null;
        try {
            System.out.println("please input your booking time(HH:mm) e.g.(09:30)");
            Scanner sc = new Scanner(System.in);
            String begin = sc.nextLine();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            beginTime = LocalTime.parse(begin, dateTimeFormatter);
        } catch (Exception e) {
            System.out.println("Error! Please choose branch to make appointment again");
        }
        return beginTime;
    }


    private static LocalTime appEnd(LocalTime beginTime, int userSelection){ //End Time
        int min;
        if (userSelection == 1)
            min = 15;
        else if (userSelection == 2)
            min = 15;
        else
            min = 30;
        LocalTime endTime = beginTime.plusMinutes(min);
        return endTime;
    }

    private static int userChooseReasonId(){
        System.out.println("please select appointment type");
        System.out.println("1,Standard Consultation Face to Face (15 minutes)");
        System.out.println("2,Telehealth (Video / Phone consult) (15 minutes)");
        System.out.println("3,Long consultation face to face (30 minutes)");
        int userSelection = 0;
        while (userSelection <= 0 || userSelection > 3) {
            Scanner sca = new Scanner(System.in);
            userSelection = sca.nextInt();
        }
        return userSelection;
    }

    public static boolean isDate(String str){//check if the input date is valid
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");//set date format
        try{
            format.setLenient(false);
            format.parse(str);//transform from string to date

        }catch(Exception e){
            result = false;
        }
        return result;
    }

}


