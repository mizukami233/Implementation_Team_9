package Controller;

import Model.Branches;
import connector.ReadFile;

import java.util.*;

public class BranchDataBase {
    private ArrayList<Branches> branch;
    private ReadFile readfile;

    public BranchDataBase(){
        branch = new ArrayList<>();
        readfile = new ReadFile();
    }

    public BranchDataBase(ArrayList<Branches> branch, ReadFile readfile) {
        this.branch = branch;
        this.readfile = readfile;
    }

    public void showBranch(){ //get the data from the file
        readfile.setFileName("branche.txt");
        ArrayList<String> lines = readfile.readDataFromFile();
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

    public ReadFile getDbConnector() {
        return readfile;
    }

    public void setDbConnector(ReadFile readfile) {
        this.readfile = readfile;
    }

}
