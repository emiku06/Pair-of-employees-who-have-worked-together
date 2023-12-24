package com.example.finalExam1.services;

import com.example.finalExam1.models.EmployeeProject;
import com.example.finalExam1.repositories.EmplProjRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmplProjService {
    private EmplProjRepository emplProjRepository;

    public EmplProjService(EmplProjRepository emplProjRepository) {
        this.emplProjRepository = emplProjRepository;
    }

    public void saveInDB(ArrayList<EmployeeProject> epArrayList){
        for (EmployeeProject ep : epArrayList)
        {
            emplProjRepository.save(ep);
        }
    }
}
