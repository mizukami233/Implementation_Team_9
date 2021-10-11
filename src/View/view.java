package View;

import Controller.*;
import Model.Appointment;
import Model.Branches;
import Model.Gp;
import Model.Spare;

import javax.swing.text.BoxView;
import javax.swing.text.View;
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

    public view() {
        appointment = new Appointmentdb();
        reason = new Reasondb();
        branch = new BranchDataBase();
        branchGp = new BranchGpDataBase();
        gp = new GpDataBase();
        oneAppointment = new Appointment();
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
                //验证失败 登陆失败 提醒失败 再次要求登陆账户密码
                System.out.println(" -Incorrect account password!");
            } else {
                //如果正确进入主程序，提醒成功
                System.out.println(" -Login Success!");


                //邮箱密码正确后跳出登陆的循环进入主界面的循环
                boolean flag = true;
                while (flag) {
                    mainScreen();
                    int choice = controller.getChoice();
                    switch (choice) {
                        //选项1展示诊所名字列表
                        case 1:
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

                                case 0: // 返回主菜单
                                    break;
                            }


                            break;


                        case 2:
                            //这里留着给你插入 Manage Appointment的方法
                            appM();
                            int managechoice2 = controller.getChoice2();
                            switch (managechoice2) {
                                case 1:
                                    generate();
                                    int managechoic3 = controller.getChoice2();
                                    if (managechoic3 == 0) {
                                        break;
                                    }

                                case 0: // 返回主菜单
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
                                    System.out.println("This is the GP's detail: 100,Peter,spare,123456,iii");
                                    appointment.writeRandomGp();
                                    System.out.println("   预约成功   ");
                                }
                                else if(input == 0){
                                    break;
                                }
                                else if (input != 1 || input != 0){
                                    System.out.println("you can only choose 0 or 1");
                                    break;
                                }
                            }
                            catch (Exception e){
                                System.out.println("you can only choose 0 or 1");
                                break;
                            }




                            //选项0 退出登录
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
            // 选择gpid
            int choice3 = controller.getChoice3();
            // 如果选择对了
            if(gpIds.contains(choice3)){

                while (true){
                    // 选择预约日期
                    //   1. 输入0  date = null
                    //   2. 一种输入日期  date = 周几
                    System.out.println(" 请输入日期 如果你输入0 将返回上一层选择gp    ");
                    Date date = returnAppDate();
                    if (date == null){
                        break;
                    }
                    DayOfWeek week = setAppointmentDate(date);
                    // 预约日期 和 上班日期作比较(周几）
                    boolean okDate = branchGp.findGpWorkingDate(week, choice3);
                    oneAppointment.setAppDate(date);
                    // 如果比较结果为true 选择预约时间段
                    if(okDate == true) {
                        // 开始选择预约时间
                        LocalTime appBeginTime = appBegin();
                        if (appBeginTime == null){
                            break;
                        }
                        int userReason = userChooseReasonId();
                        LocalTime appEndTime = appEnd(appBeginTime,userReason);
                        boolean okTime = appointment.findGpTimeFrames(date, appBeginTime, appEndTime, choice3);
                        // true 显示预约成功，不然就跳出程序
                        if (okTime == true){
                            oneAppointment.setAppBeginTime(appBeginTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                            oneAppointment.setAppEndTime(appEndTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                            oneAppointment.setGpId(choice3);
                            appointment.add(oneAppointment);
                            appointment.writeAppointmentData(oneAppointment, userReason); //txt 添加
                            System.out.println("   预约成功   ");
                        }
                        else {
                            System.out.println("   预约失败   ");
                            break;
                        }

                        // 输入

                        // 比较

                        // 结果
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



    private static void choiceAvaTime(){     //选了GP之后需要选合适的预约星期几，不然返回上一层重新选择gp
        System.out.println("Please choose GP");
        String back = "-1";
        while (back.equals("-1")){
            Scanner sc = new Scanner(System.in);
            back = sc.nextLine();

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
        System.out.println("1. Display Branches(you can use this choice to make a new appointment)");
        System.out.println();
        System.out.println("2. Manage Appointment");
        System.out.println();
        System.out.println("3. choose a random GP");
        System.out.println();
        System.out.println("0. Log out");
        System.out.println();
    }

    //branches 界面的标题
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

    //通过 打印出用户想要的那个诊所的详细信息
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


    //循环 打印出所有诊所的名字 并且返回数量 可以用来检测用户一会儿的选择
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
        Scanner scanner = new Scanner(System.in);
        String startDateString = scanner.nextLine();
        for(boolean i = isDate(startDateString); i == false;){
            System.out.println(isDate(startDateString));
            System.out.println("Your date format is wrong");
            System.out.println("Please try again: ");
            startDateString = scanner.nextLine();
            i = isDate(startDateString);
        }
        return startDateString;
    }
    public static String askEndDate(){
        System.out.println("Please enter the end date (E.g 2021/01/01) : ");
        Scanner scanner2 = new Scanner(System.in);
        String endDateString = scanner2.nextLine();
        for(boolean i = isDate(endDateString); i == false;){
            System.out.println(isDate(endDateString));
            System.out.println("Your date format is wrong");
            System.out.println("Please try again: ");
            endDateString = scanner2.nextLine();
            i = isDate(endDateString);
        }
        return endDateString;
    }


    public static void generate() {
        start();
        System.out.println("Please enter the start date (E.g 2021/01/01) : ");
        Scanner scanner = new Scanner(System.in);
        String startDateString = scanner.nextLine();

        System.out.println("Please enter the end date (E.g 2021/01/01) : ");
        Scanner scanner2 = new Scanner(System.in);
        String endDateString = scanner2.nextLine();
        try {
            Date startDate = new SimpleDateFormat("yyyy/MM/dd").parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy/MM/dd").parse(endDateString);
            int[] results = controller.generateReason(startDate, endDate);
            int sum = results[0] + results[1] + results[2];
            for (int i = 0; i < reason.getReasons().size(); i++) {
                System.out.println(reason.getReasons().get(i).getType() + ": " + (results[i] * 1.0) / sum * 100 + "%");
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

    private static DayOfWeek setAppointmentDate(Date date) {  //返回周几
        //Date date = returnAppDate();
        DayOfWeek tranDate;
        LocalDate localDate = convertToLocalDateViaSqlDate(date); //date to localdate
        //System.out.println(tranDate);
        tranDate = localDate.getDayOfWeek();
        System.out.println(tranDate);
        return tranDate;
    }

    //返回2021-01-01

    private static Date returnAppDate() {  //返回2021-01-01

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
            System.out.println(date);
//            LocalDate localDate = LocalDate.parse(appointmentDate);
//            //System.out.println(localDate.getDayOfWeek());
//            date = localDate.getDayOfWeek();
        } catch (Exception e) {
            System.out.println("Error! Please choose branch to make appointment again");
        }
        return date;
    }

    private static LocalTime appBegin() {  //设置app开始时间
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

//    private static LocalTime appBeginToLocalTime(){ //开始时间转变成localtime
//        String tranTime = appBegin();
//        LocalTime beginTime;
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        beginTime = LocalTime.parse(tranTime, dateTimeFormatter);
//        return beginTime;
//    }

    private static LocalTime appEnd(LocalTime beginTime, int userSelection){ //结束时间
        int min;
        if (userSelection == 1)
            min = 5;
        else if (userSelection == 2)
            min = 10;
        else
            min = 15;
        LocalTime endTime = beginTime.plusMinutes(min);
        return endTime;
    }

    private static int userChooseReasonId(){
        System.out.println("please select appointment type");
        System.out.println("1,5min");
        System.out.println("2,10min");
        System.out.println("3,15min");
        int userSelection = 0;
        while (userSelection <= 0 || userSelection > 3) {
            Scanner sca = new Scanner(System.in);
            userSelection = sca.nextInt();
        }
        return userSelection;
    }

    public static boolean isDate(String str){
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try{
            format.setLenient(false);
            format.parse(str);

        }catch(Exception e){
            result = false;
        }
        return result;
    }



}


