package com.example.finalExam1.services;

import com.example.finalExam1.CSVHandle.CSVReader;
import com.example.finalExam1.models.EmployeeProject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
@Service
public class CSVService {
    public ArrayList<EmployeeProject> handleFileUpload(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Upload a file");
        }
        try {
            ArrayList<EmployeeProject> epAL = CSVReader.Read(file);
            return epAL;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to process the file");
        }
    }
}
