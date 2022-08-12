package org.nphc.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@NotBlank
	@Column(name="id")
	private String id;
	
	@NotBlank
	@Column(name="login")
	private String login;
	
	@NotBlank
	@Column(name="name")
	private String name;
	
	@Column(name="salary", precision = 10, scale = 3)
	@Type(type="big_decimal")
	@PositiveOrZero(message="Invalid salary")
	private BigDecimal salary;
	
	@Column(name="startDate")
	private LocalDate startDate;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)   
			return false;  
			if (obj == this)  
			return true;
			
		Employee emp = 	(Employee) obj;
		
		return ((this.getLogin().equals(emp.getLogin())) &&
				(this.getName().equals(emp.getName()))&&
				(this.getSalary().equals(emp.getSalary())) && 
				(this.getStartDate().equals(emp.getStartDate())));
	}
	
	
	
	
}
