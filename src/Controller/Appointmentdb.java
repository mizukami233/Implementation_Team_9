package Controller;

import Model.Appointment;
import connector.ReadFile;

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
    private ReadFile readfile;

    public Appointmentdb() {
        appointments = new ArrayList<>();
        readfile = new ReadFile();
    }


    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ReadFile getDbConnector() {
        return readfile;
    }

    public void setDbConnector(ReadFile readfile) {
        this.readfile = readfile;
    }

    public ArrayList<String> getSpares() {
        return spares;
    }

    public void setSpares(ArrayList<String> spares) {
        this.spares = spares;
    }

    public void loadApp(){
        readfile.setFileName("Appointment.txt");
        ArrayList<String> lines = readfile.readDataFromFile();
        for(String line:lines){
            //AppID,PatId,BranchId,GpId,ReasonId,AppDate,AppBeginTime,AppEndTime
            //1,1,1,1,1,2021/09/22,10:00,10:05
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
            //Date need to be parsed
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
    public int[] generateReason(Date startDate, Date endDate){// get the number of each type of reason
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
        //return an array
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
            FileWriter writer = new FileWriter("appointment.txt", true); // The data of the read file is put into write stream
            int patId = (int) (1 + Math.random() * 100000);  //set patId
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd"); //tran date format
            String date= simpleDateFormat.format(appointment.getAppDate());
            //String date = appointment.getAppDate().toString();
            String data = String.format("\n%d,%d,%d,%d,%d,%s,%s,%s", appId, patId,
                    appointment.getBranchId(), appointment.getGpId(), reasonId,date, appointment.getAppBeginTime(),
                    appointment.getAppEndTime());  //Reassemble the app
            System.out.println(data);
            writer.write(data);// Write file
            writer.close(); //  close write stream
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void add(Appointment appointment){
        appointments.add(appointment);
    }

    public void randomGp() {
        // Spare spare = new Spare();
        readfile.setFileName("spare.txt");
        spares = readfile.readDataFromFile();
    }

    public void writeRandomGp() {
        try {
            FileWriter writer = new FileWriter("appointment.txt", true);
            // get all data
            ArrayList<String> strings = getSpares(); //all spare's data
            // get last raw data
            String autoApp = strings.get(strings.size() - 1);//get last raw
            System.out.println(autoApp);
            // last raw data write into appointment.txt
            writer.write("\n" + autoApp);
            writer.close();
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
