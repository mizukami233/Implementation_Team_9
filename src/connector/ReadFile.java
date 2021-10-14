package connector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReadFile {
    private String fileName;

    public ReadFile(){
        fileName = "";
    }

    public ReadFile(String fileName, boolean hasHeader){
        this.fileName = fileName;
    }

    public ArrayList<String> readDataFromFile(){
        FileReader fileReader = null;
        ArrayList<String> data = new ArrayList<>();
        try {
            fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
            bufferedReader.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
