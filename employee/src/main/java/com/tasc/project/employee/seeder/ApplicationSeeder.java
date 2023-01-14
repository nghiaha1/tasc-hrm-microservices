package com.tasc.project.employee.seeder;

import com.tasc.entity.BaseStatus;
import com.tasc.project.employee.entity.Department;
import com.tasc.project.employee.entity.Employee;
import com.tasc.project.employee.repository.DepartmentRelationshipRepository;
import com.tasc.project.employee.repository.DepartmentRepository;
import com.tasc.project.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class ApplicationSeeder implements CommandLineRunner {

    private boolean isSeeding = true;

    private static final int NUMBER_OF_DEPARTMENT = 20;

    private static final int NUMBER_OF_EMPLOYEE = 20;

    private Faker faker = new Faker();

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentRelationshipRepository departmentRelationshipRepository;

    @Override
    public void run(String... args) throws Exception {
        if (isSeeding) {
            generateDepartment();
            generateEmployee();
        }
    }

    private void generateDepartment() {
        Faker faker = new Faker();

        List<Department> departments = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_DEPARTMENT; i++) {
            Department department = Department.builder()
                    .name(faker.name().title())
                    .description(faker.lorem().paragraph(2))
                    .detail(faker.lorem().paragraph(3))
                    .build();
            department.setStatus(BaseStatus.ACTIVE);

            int randomRoot = faker.number().numberBetween(0, 2);

            if (randomRoot == 1) {
                department.setIsRoot(1);
            }

            Department abcDepartment = new Department();

            if (randomRoot == 0) {
                department.setIsRoot(0);

                for (int j = 0; j < NUMBER_OF_DEPARTMENT; j++) {
                    Optional<Department> optionalDepartment = departmentRepository.findById(faker.number().numberBetween(1L, (long) (NUMBER_OF_DEPARTMENT + 1)));

                    if (optionalDepartment.isPresent()) {
                        abcDepartment = optionalDepartment.get();
                        department.setParentDepartment(abcDepartment);
                        abcDepartment.getChildrenDepartments().add(department);

                        departmentRepository.save(abcDepartment);

                        break;
                    }
                }
            }

            departments.add(department);

        }
        departmentRepository.saveAll(departments);
    }

    private void generateEmployee() {
        for (int i = 0; i < NUMBER_OF_EMPLOYEE; i++) {
            Employee employee = Employee.builder()
                    .fullName(faker.name().fullName())
                    .gender(faker.gender().binaryTypes())
                    .dob(faker.date().birthday())
                    .email(faker.lorem().characters(10) + "@gmail.com")
                    .phone(faker.phoneNumber().cellPhone())
                    .salary(BigDecimal.valueOf(faker.number().numberBetween(100, 10000) * 100L))
                    .description(faker.lorem().sentence(2))
                    .detail(faker.lorem().sentence(3))
                    .monthlyRate(BigDecimal.valueOf(faker.number().numberBetween(10, 100) * 10L))
                    .build();

            employee.setStatus(BaseStatus.ACTIVE);

            for (int j = 0; j < 999999; j++) {
                Optional<Department> optionalDepartment = departmentRepository.findById((long) faker.number().numberBetween(1, NUMBER_OF_DEPARTMENT));

                if (optionalDepartment.isPresent()) {
                    employee.setDepartment(optionalDepartment.get());
                    break;
                }
            }

            employeeRepository.save(employee);
        }
    }

}

