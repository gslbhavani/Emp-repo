package com.capgemini.training.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.training.data.EmployeeRepository;
import com.capgemini.training.entity.Employee;
import com.capgemini.training.exception.EmployeeException;

//@Slf4j  Applying Spring AOP for logging
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmployeeById(Long id) throws EmployeeException {
		try {
			Optional<Employee> optional= employeeRepository.findById(id);
			if(optional.isPresent()) {
				Employee employee= optional.get();
				return employee;
			}else {
				throw new EmployeeException("Invalid Employee Id");
			}
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public Employee getEmployeeByName(String name) throws EmployeeException {
		try {
			Employee employee= employeeRepository.findByName(name);
			return employee;
		}catch(DataAccessException e) {
			//log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public List<Employee> getAllEmployees() throws EmployeeException {
		try {
			List<Employee> employeeList= employeeRepository.findAll();
			if(employeeList.size()!=0) {
				return employeeList;
			}else {
				throw new EmployeeException("No employees in the database");			
			}
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public boolean exists(String name) throws EmployeeException {
		if(employeeRepository.findByName(name)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public Employee save(Employee employee) throws EmployeeException {
		try {
			Employee e=employeeRepository.save(employee);
			return e;
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public List<Employee> findByName(String name) throws EmployeeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findByJobAndSalary(String job, Double salary) throws EmployeeException {
		try {			
			List<Employee> employeeList=employeeRepository.findByJobAndSalary(job, salary);
			if(employeeList.size()!=0) {
				return employeeList;
			}else {
				//				log.debug("No employees in the database with the specified conditions");				
				throw new EmployeeException("No employees in the database with the specified job and salary");
			}
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public List<Employee> findByJobAndSalaryGreaterThan(String job, Double salary) throws EmployeeException {
		try {
			List<Employee> employeeList=employeeRepository.findByJobAndSalaryGreaterThan(job, salary);
			if(employeeList.size()!=0) {
				return employeeList;
			}else {
				//				log.debug("No employees in the database with the specified conditions");				
				throw new EmployeeException("No employees in the database with the specified conditions");
			}
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee) throws EmployeeException {
		try {
			Employee e=employeeRepository.save(employee);
			return e;
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}

	@Override
	public Map<String, Boolean> deleteEmployee(Long id) throws EmployeeException {
		try {
			Employee employee=employeeRepository.findById(id).get();								
			employeeRepository.delete(employee);
			Map<String,Boolean> statusMap= new HashMap<>();
			statusMap.put("Employee deleted", Boolean.TRUE);
			return statusMap;
		}catch(DataAccessException e) {
			//			log.error(e.getMessage(), e);
			throw new EmployeeException(e.getMessage(),e);
		}
	}


}
