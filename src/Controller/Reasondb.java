package Controller;

import Model.Reason;
import connector.ReadFile;

import java.util.ArrayList;

public class Reasondb {
    private ArrayList<Reason> reasons;
    private ReadFile readfile;

    public Reasondb() {
        reasons = new ArrayList<>();
        readfile = new ReadFile();
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

    public void loadReason(){
        readfile.setFileName("reason.txt");

        ArrayList<String> lines = readfile.readDataFromFile();
        for(String line:lines){
            String[] lineArray = line.split(",");
            //ReasonId, Type, description
            Reason reason = new Reason(Integer.parseInt(lineArray[0]),lineArray[1],lineArray[2]);
            reasons.add(reason);
        }
    }
}


