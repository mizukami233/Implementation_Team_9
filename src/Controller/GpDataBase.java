package Controller;

import Model.Gp;
import connector.ReadFile;

import java.util.ArrayList;

public class GpDataBase {
    private ArrayList<Gp> gp;
    private ReadFile readfile;

    public GpDataBase(){
        gp = new ArrayList<>();
        readfile = new ReadFile();
    }

    public GpDataBase(ArrayList<Gp> gp, ReadFile readfile) {
        this.gp = gp;
        this.readfile = readfile;
    }

    public void showGp(){  //get the data from the file
        readfile.setFileName("Gp.txt");
        ArrayList<String> lines = readfile.readDataFromFile();
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

    public ReadFile getDbConnector() {
        return readfile;
    }

    public void setDbConnector(ReadFile readfile) {
        this.readfile = readfile;
    }
}
