package com.example.finalExam1.CSVHandle;

import com.example.finalExam1.models.EmployeeProject;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CSVReader {
    public  static ArrayList<EmployeeProject> Read(MultipartFile file){
        String line;
        ArrayList<EmployeeProject> employeeProjectArrayList= new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            while((line = br.readLine()) != null){
                String[] sLine = line.split(",");
                LocalDate endDate = dataFormatParser(sLine[3]);

                EmployeeProject employeeProject = new EmployeeProject(Integer.parseInt(sLine[0]),
                        Integer.parseInt(sLine[1]), LocalDate.parse(sLine[2]), endDate );
                employeeProjectArrayList.add(employeeProject);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return employeeProjectArrayList;
    }

    private static LocalDate dataFormatParser(String dateString){
        String[] dateFormats = {
                "yyyy-MM-dd",
                "MM/dd/yyyy",
                "dd-MM-yyyy"
        };
        for (String dateFormat : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                return LocalDate.parse(dateString, formatter);
            }catch (DateTimeParseException ignored){

            }
        }
        return LocalDate.now();
    }
}
