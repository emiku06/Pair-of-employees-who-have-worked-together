package com.example.finalExam1.controllers;

import com.example.finalExam1.CSVHandle.CSVReader;
import com.example.finalExam1.models.EmployeeProject;
import com.example.finalExam1.services.CSVService;
import com.example.finalExam1.services.EmplProjService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/addEmployees")
public class EmplProjController {
    static ArrayList<EmployeeProject> epAL;
    private EmplProjService emplProjService;
    private CSVService csvService;

    public EmplProjController(EmplProjService emplProjService, CSVService csvService) {
        this.emplProjService = emplProjService;
        this.csvService = csvService;
    }
    @PostMapping("/saveFromCSV")
    public void saveFromCSV(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            epAL = csvService.handleFileUpload(file);
            emplProjService.saveInDB(epAL);

        }catch (Exception e){
           throw new Exception("it didn't work");
        }
    }
    @GetMapping("/longest-duration-pairs")
    public String findLongestDurationPairs() {
        return emplProjService.findLongestDurationPairs(epAL);
    }
}
