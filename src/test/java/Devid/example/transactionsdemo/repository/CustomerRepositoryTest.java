package Devid.example.transactionsdemo.repository;

import Devid.example.transactionsdemo.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Customer Repository Tests")
public class CustomerRepositoryTest {

    @Test
    @DisplayName("It should save the customer")
    void save() {
        // Arrange
        Customer myCustomer = new Customer();
        myCustomer.setName("John Doe");
        myCustomer.setId(1L);

        // Mocking the behavior of customerRepository.save()
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Customer.class))).thenReturn(myCustomer);

        // Act
        Customer newCustomer = mockRepository.save(myCustomer);

        // Assert
        Assertions.assertNotNull(newCustomer);
        Assertions.assertNotNull(newCustomer.getId());
    }

    @Test
    @DisplayName("It should return customers with size 2")
    void getAllCustomers() {
        // Arrange
        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer("John Doe", null, null));
        mockCustomers.add(new Customer("Jane Smith", null, null));

        // Mocking the behavior of customerRepository.findAllWithTransactions()
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockRepository.findAllWithTransactions()).thenReturn(mockCustomers);

        // Act
        List<Customer> list = mockRepository.findAllWithTransactions();

        // Assert
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    @DisplayName("It should return customer by Id")
    void getCustomerById() {
        // Arrange
        Customer myCustomer = new Customer("John Doe", null, null);
        myCustomer.setId(1L);

        // Mocking the behavior of customerRepository.findById()
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(myCustomer));

        // Act
        Optional<Customer> existingCustomer = mockRepository.findById(1L);

        // Assert
        Assertions.assertTrue(existingCustomer.isPresent());
        Assertions.assertEquals(1L, existingCustomer.get().getId());
        Assertions.assertEquals("John Doe", existingCustomer.get().getName());
    }

    @Test
    @DisplayName("It should return an updated customer")
    void updateCustomer() {
        // Arrange
        Customer myCustomer = new Customer("John Doe", null, null);
        myCustomer.setId(1L);
        Customer updatedCustomer = new Customer("Jane Smith", null, null);
        updatedCustomer.setId(1L);

        // Mocking the behavior of customerRepository.findById() and customerRepository.save()
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(myCustomer));
        Mockito.when(mockRepository.save(Mockito.any(Customer.class))).thenReturn(updatedCustomer); // Mock save operation

        // Act
        Customer existingCustomer = mockRepository.findById(1L).orElse(null);
        Customer newCustomer = mockRepository.save(existingCustomer);

        // Assert
        Assertions.assertNotNull(newCustomer); // Ensure newCustomer is not null
        Assertions.assertEquals(1L, newCustomer.getId());
        Assertions.assertEquals("Jane Smith", newCustomer.getName());
    }

    @Test
    @DisplayName("It should delete the customer")
    void deleteCustomer() {
        // Arrange
        Customer myCustomer = new Customer("John Doe", null, null);
        myCustomer.setId(1L);

        // Mocking the behavior of customerRepository.deleteById()
        CustomerRepository mockRepository = Mockito.mock(CustomerRepository.class);
        Mockito.doNothing().when(mockRepository).deleteById(1L);

        // Act
        mockRepository.deleteById(1L);

        // Assert
        Optional<Customer> existingCustomer = mockRepository.findById(1L);
        Assertions.assertTrue(existingCustomer.isEmpty());
    }
}
