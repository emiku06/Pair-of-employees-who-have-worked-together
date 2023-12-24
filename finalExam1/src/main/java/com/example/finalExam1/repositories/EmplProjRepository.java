package com.example.finalExam1.repositories;

import com.example.finalExam1.models.EmployeeProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmplProjRepository {
    private JdbcTemplate jdbcTemplate;

    public EmplProjRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(EmployeeProject ep){
        String sql = "Insert into employeeproject (employeeId, projId, startDate, endDate) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, ep.getEmployeeId(), ep.getProjId(), ep.getStartDate(), ep.getEndDate());

    }
}
