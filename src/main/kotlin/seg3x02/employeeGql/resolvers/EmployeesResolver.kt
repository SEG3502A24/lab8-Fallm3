package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Component
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import seg3x02.employeeGql.resolvers.types.UpdateEmployeeInput

@Component
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    // Query to fetch all employees
    fun getAllEmployees(): List<Employee> {
        return employeesRepository.findAll()
    }

    // Query to fetch an employee by ID
    fun getEmployeeById(id: String): Employee? {
        return employeesRepository.findById(id).orElse(null)
    }

    // Mutation to create a new employee
    fun createEmployee(input: CreateEmployeeInput): Employee? {
        val newEmployee = input.name?.let {
            input.salary?.let { it1 ->
                input.city?.let { it2 ->
                    input.dateOfBirth?.let { it3 ->
                        Employee(
                            name = it,
                            dateOfBirth = it3,
                            city = it2,
                            salary = it1,
                            gender = input.gender,
                            email = input.email
                        )
                    }
                }
            }
        }
        return newEmployee?.let { employeesRepository.save(it) }
    }

    // Mutation to update an existing employee
    fun updateEmployee(id: String, input: UpdateEmployeeInput): Employee? {
        val existingEmployee = employeesRepository.findById(id).orElse(null) ?: return null
        existingEmployee.name = input.name ?: existingEmployee.name
        existingEmployee.dateOfBirth = input.dateOfBirth ?: existingEmployee.dateOfBirth
        existingEmployee.city = input.city ?: existingEmployee.city
        existingEmployee.salary = input.salary ?: existingEmployee.salary
        existingEmployee.gender = input.gender ?: existingEmployee.gender
        existingEmployee.email = input.email ?: existingEmployee.email
        return employeesRepository.save(existingEmployee)
    }

    // Mutation to delete an employee by ID
    fun deleteEmployee(id: String): Boolean {
        return if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
