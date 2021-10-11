package Controller;

import Model.Reason;
import connector.DbConnector;

import java.util.ArrayList;

public class Reasondb {
    private ArrayList<Reason> reasons;
    private DbConnector dbConnector;

    public Reasondb() {
        reasons = new ArrayList<>();
        dbConnector = new DbConnector();
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
}


