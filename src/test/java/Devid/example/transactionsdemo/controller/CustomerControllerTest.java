package Devid.example.transactionsdemo.controller;
import Devid.example.transactionsdemo.entity.Customer;
import Devid.example.transactionsdemo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.mockito.Mockito.*;
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testSaveCustomer() throws Exception {
        // Mocking service behavior
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"John Doe\"}")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        // Verifying that the service method was called once with any Customer object
        verify(customerService, times(1)).saveCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Mocking service behavior
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Updated Name");
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(customer);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Updated Name\"}")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Name"));

        // Verifying that the service method was called once with specific ID and any Customer object
        verify(customerService, times(1)).updateCustomer(eq(1L), any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1")).andExpect(MockMvcResultMatchers.status().isOk());

        // Verifying that the service method was called once with specific ID
        verify(customerService, times(1)).deleteCustomer(1L);
    }


    @Test
    void testGetCustomerById() throws Exception {
        // Mocking service behavior
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        // Verifying that the service method was called once
        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        // Mocking service behavior
        when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")).andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verifying that the service method was called once
        verify(customerService, times(1)).getCustomerById(1L);
    }
    // You can add more tests for other controller methods (saveCustomer, updateCustomer, deleteCustomer) similarly
}
