package Controller;

import Model.Appointment;
import Model.Branches;
import Model.Spare;
import connector.DbConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Appointmentdb {
    private ArrayList<Appointment> appointments;
    private ArrayList<String> spares;
    private DbConnector dbConnector;

    public Appointmentdb() {
        appointments = new ArrayList<>();
        dbConnector = new DbConnector();
    }


    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public DbConnector getDbConnector() {
        return dbConnector;
    }

    public void setDbConnector(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public ArrayList<String> getSpares() {
        return spares;
    }

    public void setSpares(ArrayList<String> spares) {
        this.spares = spares;
    }

    public void loadApp(){
        dbConnector.setFileName("Appointment.txt");
        dbConnector.setHasHeader(true);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            //1,1,1,1,1,2021/09/22,10:00
            if (line.equals("\n") || line.equals("")){
                continue;
            }
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
            appointments.add(appointment);

        }
    }
    public int[] generateReason(Date startDate, Date endDate){
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

    public boolean findGpTimeFrames(Date date, LocalTime beginTime, LocalTime endTime, int gId) {
        boolean gpEmpty = true;
        boolean gpNone = true;
        for (Appointment appointment: appointments) { //这个人没任何预约，不出现在预约表
            if (appointment.getGpId() == gId) {
                gpNone = false;
            }
        }
        if (!gpNone) { //这个人有预约，但是日期不冲突
            for (Appointment appointment : appointments) {
                if (appointment.getGpId() == gId && appointment.getAppDate().equals(date)) {
                    gpEmpty = false;
                }
            }
            if (!gpEmpty) {  //这个人有预约，但是日期冲突，所以才比对预约时间
                for (Appointment appointment : appointments) {
                    if (appointment.getGpId() == gId) { //医生有预约
                        if (appointment.getAppDate().equals(date)) {
                            //且当天预约时间不冲突
                            LocalTime appLocalStartTime = LocalTime.parse(appointment.getAppBeginTime());
                            LocalTime appLocalEndTime = LocalTime.parse(appointment.getAppEndTime());
                            if (appLocalEndTime.isBefore(beginTime) || appLocalStartTime.isAfter(endTime)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void writeAppointmentData(Appointment appointment, int reasonId) {
        int appId = appointments.size() + 1;  //set appId
        try {
            FileWriter writer = new FileWriter("appointment.txt", true); // 读文件的数据放到写入流里
            int patId = (int) (1 + Math.random() * 100000);  //set patId
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd"); //转换date format
            String date= simpleDateFormat.format(appointment.getAppDate());
            //String date = appointment.getAppDate().toString();
            String data = String.format("\n%d,%d,%d,%d,%d,%s,%s,%s", appId, reasonId, patId,
                    appointment.getBranchId(), appointment.getGpId(), date, appointment.getAppBeginTime(),
                    appointment.getAppEndTime());  //重新组装app
            System.out.println(data);
            writer.write(data);// 写入文件
            writer.close(); // 关闭流
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void add(Appointment appointment){
        appointments.add(appointment);
    }

    public void randomGp() {
        // Spare spare = new Spare();
        dbConnector.setFileName("spare.txt");
        dbConnector.setHasHeader(false);
        spares = dbConnector.readDataFromFile();
//        for (String line : lines) {
//            spares.add(line);
//        }
    }

    public void writeRandomGp() {
        try {
            FileWriter writer = new FileWriter("appointment.txt", true); // 读文件的数据放到写入流里
            // get all data
            ArrayList<String> strings = getSpares(); //all spare's data
            // get last raw data
            String autoApp = strings.get(strings.size() - 1);//get last raw
            System.out.println(autoApp);
            // last raw data write into appointment.txt
            writer.write("\n" + autoApp);// 写入文件
            writer.close(); // 关闭流
            //last remove
            strings.remove(strings.size() - 1);

            removeTxt(strings);
            // add


        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void removeTxt(ArrayList<String> strings){

        try {
            FileWriter writer = new FileWriter("spare.txt"); // 读文件的数据放到写入流里
            // last raw data write into spare.txt
            for (String string: strings){
                writer.write(string + "\n");// 写入文件
            }
            writer.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
