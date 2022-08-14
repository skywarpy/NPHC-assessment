package org.nphc.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.nphc.entities.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String>{
	
	
	@Query("select e from Employee e where e.salary >= ?1 and e.salary < ?2 order by e.id ASC")
	List<Employee> findAllBySalaryRangeWithOffsetAndLimit(BigDecimal lowerSalary,BigDecimal higherSalary, Pageable page);
	
	@Query("select login from Employee e")
	List<String> findAllLogin();
}
