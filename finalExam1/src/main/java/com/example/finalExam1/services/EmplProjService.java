package com.example.finalExam1.services;

import com.example.finalExam1.models.EmployeeProject;
import com.example.finalExam1.repositories.EmplProjRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public String  findLongestDurationPairs(ArrayList<EmployeeProject> epAL) {
        Map<Integer,ArrayList<EmployeeProject>> employeePair = new HashMap<>();
        boolean flag = false;
        for (int i = 0; i < epAL.size(); i++) {
            for (EmployeeProject employeeProject : epAL) {
                if (epAL.get(i).getEmployeeId() == employeeProject.getEmployeeId()) {
                    continue;
                }
                if (epAL.get(i).getProjId() == employeeProject.getProjId()) {
                    if (epAL.get(i).getStartDate().isBefore(employeeProject.getStartDate())) {
                        if (!epAL.get(i).getEndDate().isBefore(employeeProject.getStartDate())) {

                            fillMap(epAL.get(i), employeeProject, employeePair);
                            flag = true;
                        }
                    } else if (epAL.get(i).getStartDate().isAfter(employeeProject.getStartDate())) {
                        if (!epAL.get(i).getStartDate().isAfter(employeeProject.getEndDate())) {

                            fillMap(epAL.get(i), employeeProject, employeePair);
                            flag = true;
                        }
                    } else {
                        fillMap(epAL.get(i), employeeProject, employeePair);
                    }
                    flag = true;
                }
                if (flag) {
                    break;
                }
            }
        }

    }
    private static void fillMap(EmployeeProject employeeProject, EmployeeProject employeeProject1, Map<Integer, ArrayList<EmployeeProject>> employeePair) {
        ArrayList<EmployeeProject> employeeProjectsPair = new ArrayList<>();
        employeeProjectsPair.add(employeeProject);
        employeeProjectsPair.add(employeeProject1);
        employeePair.put(employeeProject.getProjId(), employeeProjectsPair);
    }
}
