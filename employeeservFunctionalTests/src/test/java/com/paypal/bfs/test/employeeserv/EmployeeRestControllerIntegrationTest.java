package com.paypal.bfs.test.employeeserv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EmployeeservApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        mvc.perform(get("/v1/bfs/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.first_name", is("ashish")));
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus404() throws Exception {
        mvc.perform(get("/v1/bfs/employees/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPostEmployee_thenCreateEmployee201() throws Exception {
        mvc.perform(post("/v1/bfs/employees")
                .content("{\n" +
                        "\t\"first_name\": \"ashish\",\n" +
                        "\t\"state\": \"haryana\",\n" +
                        "\t\"zip_code\": 127201,\n" +
                        "\t\"city\": \"bhiwani\",\n" +
                        "\t\"country\": \"india\",\n" +
                        "\t\"address_line1\": \"ward no 12\",\n" +
                        "\t\"last_name\": \"lamba\",\n" +
                        "\t\"date_of_birth\": \"21/08/1993\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.first_name", is("ashish")))
                .andExpect(jsonPath("$.last_name", is("lamba")))
                .andExpect(jsonPath("$.zip_code", is(127201)))
                .andExpect(jsonPath("$.city", is("bhiwani")))
                .andExpect(jsonPath("$.country", is("india")))
                .andExpect(jsonPath("$.address_line1", is("ward no 12")))
                .andExpect(jsonPath("$.date_of_birth", is("21/08/1993")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void whenPostEmployee_thenEmployeeExists403() throws Exception {
        mvc.perform(post("/v1/bfs/employees")
                .content("{\n" +
                        "\t\"first_name\": \"ashish\",\n" +
                        "\t\"id\": 1,\n" +
                        "\t\"state\": \"haryana\",\n" +
                        "\t\"zip_code\": 127201,\n" +
                        "\t\"city\": \"bhiwani\",\n" +
                        "\t\"country\": \"india\",\n" +
                        "\t\"address_line1\": \"ward no 12\",\n" +
                        "\t\"last_name\": \"lamba\",\n" +
                        "\t\"date_of_birth\": \"21/08/1993\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(403)))
                .andExpect(jsonPath("$.message", is("Employee Already Exists")));
    }

    @Test
    public void whenPostEmployeeEmpty_thenStatus400() throws Exception {
        mvc.perform(post("/v1/bfs/employees")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Bad Request - Field Validation Failed")));
    }

}