package com.inn.cafe.employee.repository;

import com.inn.cafe.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Employee,Long> {
}
