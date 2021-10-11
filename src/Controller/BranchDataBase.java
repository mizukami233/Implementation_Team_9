package Controller;

import Model.Branches;
import connector.DbConnector;
import java.util.*;
import java.lang.Math;

public class BranchDataBase {
    private ArrayList<Branches> branch;
    private DbConnector dbConnector;

    public BranchDataBase(){
        branch = new ArrayList<>();
        dbConnector = new DbConnector();
    }

    public BranchDataBase(ArrayList<Branches> branch, DbConnector dbConnector) {
        this.branch = branch;
        this.dbConnector = dbConnector;
    }

    public void showBranch(){ //get the data from the file
        dbConnector.setFileName("branche.txt");
        dbConnector.setHasHeader(false);
        ArrayList<String> lines = dbConnector.readDataFromFile();
        for(String line:lines){
            String[] lineArray = line.split(",");
            //brid;name;postcode;openingHour;phone;streetName;suburb
            branch.add(new Branches(Integer.parseInt(lineArray[0]),lineArray[1],Integer.parseInt(lineArray[2]),lineArray[3],
                    Integer.parseInt(lineArray[4]),lineArray[5],lineArray[6]));
        }

    }

    public ArrayList<Branches> getBranches() {
        return branch;
    }

    public void setBranches(ArrayList<Branches> branch) {
        this.branch = branch;
    }

    public DbConnector getDbConnector() {
        return dbConnector;
    }

    public void setDbConnector(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

}
