package pro.sky.EmployeeBookStream.service.Impl;

import org.springframework.stereotype.Service;
import pro.sky.EmployeeBookStream.exception.EmployeeAlreadyAddedException;
import pro.sky.EmployeeBookStream.exception.EmployeeNotFoundException;
import pro.sky.EmployeeBookStream.exception.EmployeeStorageIsFullException;
import pro.sky.EmployeeBookStream.model.Employee;
import pro.sky.EmployeeBookStream.service.EmployeeService;
import pro.sky.EmployeeBookStream.service.EmployeeValidationService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tomcat.util.IntrospectionUtils.capitalize;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    public static final int EMPLOYEE_MAX_COUNT = 10;

    private final Map<String, Employee> employees;
    private final EmployeeValidationService validationService;

    public EmployeeServiceImpl(EmployeeValidationService validationService) {
        this.employees = new HashMap<>();
        this.validationService = validationService;
    }


    @Override
    public Employee add(String firstName, String lastName) {
        validationService.validate(firstName, lastName);
        if (employees.size() >= EMPLOYEE_MAX_COUNT) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName);

        if (employees.containsKey(employee.getFullName())) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(employee.getFullName(), employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            employees.remove(employee.getFullName(), employee);
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Collection<Employee> findAll() {
        return employees.values();
    }
}
