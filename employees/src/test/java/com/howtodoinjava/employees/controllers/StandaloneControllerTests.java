package com.howtodoinjava.employees.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.employees.model.Employee;
import com.howtodoinjava.employees.services.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class StandaloneControllerTests {

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testfindAll() throws Exception {
    Employee employee = new Employee("Lokesh", "Gupta");
    List<Employee> employees = List.of(employee);

    Mockito.when(employeeService.findAll()).thenReturn(employees);

    mockMvc.perform(get("/employee"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh")));
  }

  @Test
  public void testNewEmployee() throws Exception {
    Employee employee = new Employee("Andres", "Ramos");
    employee.setId(1);

    Mockito.when(employeeService.save(any(Employee.class))).thenReturn(employee);

    mockMvc.perform(post("/employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(new Employee("Andres", "Ramos"))))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.firstName").value("Andres"))
            .andExpect(jsonPath("$.lastName").value("Ramos"));


  }
}
