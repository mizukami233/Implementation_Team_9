package connector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DbConnector {
    private String fileName;
    private boolean hasHeader;

    public DbConnector(){
        fileName = "";
        hasHeader = false;
    }

    public DbConnector(String fileName,boolean hasHeader){
        this.fileName = fileName;
        this.hasHeader = hasHeader;
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
        if(hasHeader && data.size()>0)
            data.remove(0);
        return data;
    }

//    private void storeData(){ //write data into file
//        try
//        {
//            PrintWriter data = new PrintWriter("appointment.txt");
//            try
//            {
//                for(int i = 0; i < carData.getCars().size();i++)
//                {
//                    String registration_number = carData.getCars().get(i).getRegistration_number();
//                    int made_year = carData.getCars().get(i).getYear();
//                    String color1 = carData.getCars().get(i).getOne_color(0);
//                    String color2 = carData.getCars().get(i).getOne_color(1);
//                    String color3 = carData.getCars().get(i).getOne_color(2);
//                    String car_make = carData.getCars().get(i).getCar_make();
//                    String car_model = carData.getCars().get(i).getCar_model();
//                    int price = carData.getCars().get(i).getPrice();
//                    data.println(registration_number + "," + made_year + "," + color1 + "," + color2 + "," + color3 + "," + car_make + "," + car_model + "," + price);
//                }
//            }
//            finally
//            {
//                System.out.println("We will store your details into appointment file");
//                data.close();
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Operating error");
//        }
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }
}
