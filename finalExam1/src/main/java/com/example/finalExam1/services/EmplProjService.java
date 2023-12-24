package com.example.finalExam1.services;

import com.example.finalExam1.models.EmployeeProject;
import com.example.finalExam1.repositories.EmplProjRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        long longestDuraton = 0;
        String result = "";
        for (Map.Entry<Integer, ArrayList<EmployeeProject>> entry : employeePair.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<EmployeeProject> value = entry.getValue();
            LocalDate e1Start = value.get(0).getStartDate();
            LocalDate e1End = value.get(0).getEndDate();
            LocalDate e2Start = value.get(1).getStartDate();
            LocalDate e2End = value.get(1).getEndDate();

            long daysTogether;
            LocalDate today = LocalDate.now();
            if (Objects.equals(e1End, today)) {
                daysTogether = Math.abs(ChronoUnit.DAYS.between(e2Start, e2End));
            } else if (Objects.equals(e2End, today)) {
                daysTogether = Math.abs(ChronoUnit.DAYS.between(e1Start, e1End));

            }
            else if (e1Start.isBefore(e2Start)) {
                daysTogether = Math.abs(ChronoUnit.DAYS.between(e1End, e2Start));
            } else {
                daysTogether = Math.abs(ChronoUnit.DAYS.between(e1Start, e2End));
            }

            if (daysTogether > longestDuraton) {
                result = key + ", ";
                for (EmployeeProject employeeProject : value) {
                    result += employeeProject.getEmployeeId() + " ";
                }
                result += daysTogether;
            }
        }
        return result;
    }
    private static void fillMap(EmployeeProject employeeProject, EmployeeProject employeeProject1, Map<Integer, ArrayList<EmployeeProject>> employeePair) {
        ArrayList<EmployeeProject> employeeProjectsPair = new ArrayList<>();
        employeeProjectsPair.add(employeeProject);
        employeeProjectsPair.add(employeeProject1);
        employeePair.put(employeeProject.getProjId(), employeeProjectsPair);
    }
}
