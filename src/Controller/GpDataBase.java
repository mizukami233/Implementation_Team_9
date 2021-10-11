package Controller;

import Model.BranchGp;
import Model.Gp;
import connector.DbConnector;
import netscape.javascript.JSObject;

import java.util.ArrayList;

public class GpDataBase {
    private ArrayList<Gp> gp;
    private DbConnector dbConnector;

    public GpDataBase(){
        gp = new ArrayList<>();
        dbConnector = new DbConnector();
    }

    public GpDataBase(ArrayList<Gp> gp, DbConnector dbConnector) {
        this.gp = gp;
        this.dbConnector = dbConnector;
    }

    public void showGp(){  //get the data from the file
        dbConnector.setFileName("Gp.txt");
        dbConnector.setHasHeader(false);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            String[] lineArray = line.split(",");
            //int gpId, String firstName, String lastName, String phone, String expert
            gp.add(new Gp(Integer.parseInt(lineArray[0]),lineArray[1],lineArray[2],lineArray[3],lineArray[4]));
        }
    }

    public String showGpByGpId(int gpId){
//        showGp();
        String listGp = "";
        for (Gp gps: gp)
        {
            if (gps.getGpId() == gpId){
                listGp += gps.getGpId() + ", "
                        +gps.getFirstName() + " "
                        +gps.getLastName() + ", "
                        +gps.getExpert()  + "\n";
            }
            else
            {
                continue;
            }
        }
        return listGp;
    }

    public String showGpLi(){   //print list logic 没用这个方法
        String listGp = "";
        for (Gp gps : gp){
            //int gpId, String firstName, String lastName, String phone, String expert
            listGp += gps.getGpId() + ", "
                    +gps.getFirstName() + ", "
                    +gps.getLastName() + ", "
                    +gps.getExpert()  + "\n";
        }
        return listGp;

    }

    public ArrayList<Gp> getBranches() {
        return gp;
    }

    public void setBranches(ArrayList<Gp> gp) {
        this.gp = gp;
    }

    public DbConnector getDbConnector() {
        return dbConnector;
    }

    public void setDbConnector(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
}
