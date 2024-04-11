package Devid.example.transactionsdemo.service;

import Devid.example.transactionsdemo.entity.Customer;
import Devid.example.transactionsdemo.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    private Customer myCustomer;
    private Customer yourCustomer;

    @BeforeEach
    void init() {
        myCustomer = new Customer();
        myCustomer.setId(1L);
        myCustomer.setName("John Doe");

        yourCustomer = new Customer();
        yourCustomer.setId(2L);
        yourCustomer.setName("Jane Doe");
    }

    @Test
    @DisplayName("Should save the customer object to database")
    void save() {
        when(customerRepository.save(any(Customer.class))).thenReturn(myCustomer);
        Customer newCustomer = customerService.saveCustomer(myCustomer);

        Assertions.assertNotNull(newCustomer);
        assertThat(newCustomer.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("It should the return all customers")
    void getAllCustomers() {
        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(yourCustomer);
        mockCustomers.add(myCustomer);

        when(customerRepository.findAllWithTransactions()).thenReturn(mockCustomers);

        List<Customer> customers = customerService.getAllCustomers();

        Assertions.assertEquals(2, customers.size());
        Assertions.assertNotNull(customers);
    }

    @Test
    @DisplayName("It should return the customer with specified Id")
    void getCustomerById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(myCustomer));
        Optional<Customer> existingCustomer = customerService.getCustomerById(1L);

        Assertions.assertTrue(existingCustomer.isPresent());
        Assertions.assertEquals(1L, existingCustomer.get().getId());
    }

    @Test
    @DisplayName("It should return updated customer(Into database)")
    void updateCustomer() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(myCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(myCustomer);

        myCustomer.setName("Updated John Doe");
        Customer updatedCustomer = customerService.updateCustomer(1L, myCustomer);

        Assertions.assertNotNull(updatedCustomer);
        Assertions.assertEquals("Updated John Doe", updatedCustomer.getName());
    }

    @Test
    @DisplayName("It should delete the customer from database")
    void deleteCustomer() {
        doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomer(myCustomer.getId());

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}
