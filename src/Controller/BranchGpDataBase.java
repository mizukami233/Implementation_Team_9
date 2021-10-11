package Controller;

import Model.Appointment;
import Model.BranchGp;
import Model.Branches;
import connector.DbConnector;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;

public class BranchGpDataBase {
    private ArrayList<BranchGp> branchGp;
    private DbConnector dbConnector;

    public BranchGpDataBase(){
        branchGp = new ArrayList<>();
        dbConnector = new DbConnector();
    }

    public BranchGpDataBase(ArrayList<BranchGp> branchGp, DbConnector dbConnector) {
        this.branchGp = branchGp;
        this.dbConnector = dbConnector;
    }

    public HashSet<Integer> useBranchFindGp(int branchId){ //use hashset to avoid repeat value
//        showBranchGp();
        HashSet<Integer> gpLi = new HashSet<>(); //new arraylist
        for (BranchGp oneBranchGp : branchGp){ //for each
            if(oneBranchGp.getBranchId()== branchId){
                gpLi.add(oneBranchGp.getGpId());
            }
        }
        return gpLi;
    }

    public boolean findGpWorkingDate(DayOfWeek date, int gId){
        // showBranchGp();
        System.out.println(date.toString());
        boolean ava = false;
        //ArrayList<String> gpWorkingDate = new ArrayList<>();
        for (BranchGp oneBranchGp : branchGp){
            if(oneBranchGp.getGpId() == gId){
                String workingTime = oneBranchGp.getWorkingTime();
                System.out.println();
                if(workingTime.equals(date.toString()))
                {
                    ava = true;
                    break;
                }
            }
        }

        return ava;
    }
//        //MONDAY TUESDAY
//        //比较date和gp working time是否一致
//        //一致返回true 否则false
//        for(int i = 0; i < gpWorkingDate.size();i++)
//        {
//            if(gpWorkingDate.get(i).equals(date))
//            {
//                ava = true;
//                break;
//            }
//        }





    public ArrayList<String> showGpWorkingTimeByGidBid(int gId, int bId)
    {
        ArrayList<String> workingHourLi = new ArrayList<>();
        for (BranchGp oneBranchGp : branchGp)
        {
            if (oneBranchGp.getGpId() == gId && oneBranchGp.getBranchId() == bId)
            {
                workingHourLi.add(oneBranchGp.getWorkingTime() + " from: "
                        + oneBranchGp.getWorkingBegin()
                        + ":00 to: " + oneBranchGp.getWorkingEnd() + ":00]" + "\n");
            }
        }
        return workingHourLi;
    }

    public void showBranchGp(){ //get the data from the file
        dbConnector.setFileName("BranchGp.txt");
        dbConnector.setHasHeader(false);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            String[] lineArray = line.split(",");
            //int bgId, int gpId, int branchId, String workingTime, int workingBegin, int workingEnd
            branchGp.add(new BranchGp(Integer.parseInt(lineArray[0]),Integer.parseInt(lineArray[1]),
                    Integer.parseInt(lineArray[2]),lineArray[3],
                    Integer.parseInt(lineArray[4]),Integer.parseInt(lineArray[5])));
        }
    }

    public String showBranchGpLi(){   //print list logic
        String listBranch = "";
        for (BranchGp branchGps : branchGp){
            //int bgId, int gpId, int branchId, String workingTime, int workingBegin, int workingEnd
            listBranch += branchGps.getBranchId() + ", "
                    +branchGps.getBgId() + ", "
                    +branchGps.getGpId() + ", "
                    +branchGps.getWorkingTime() + ", ["
                    +branchGps.getWorkingBegin() + ":00, "
                    +branchGps.getWorkingEnd()  + ":00]\n";
        }
        return listBranch;

    }

    public ArrayList<BranchGp> getBranches() {
        return branchGp;
    }

    public void setBranches(ArrayList<BranchGp> branchGp) {
        this.branchGp = branchGp;
    }

    public DbConnector getDbConnector() {
        return dbConnector;
    }

    public void setDbConnector(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
}
