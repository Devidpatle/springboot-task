package Devid.example.transactionsdemo.service;
import Devid.example.transactionsdemo.entity.Customer;
import Devid.example.transactionsdemo.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public List<Customer> getAllCustomers(){
        return customerRepository.findAllWithTransactions();
    }
    public Optional<Customer> getCustomerById(Long id){

        return customerRepository.findById(id);
    }
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

        // Update existing customer properties
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setTransactions(updatedCustomer.getTransactions());
        existingCustomer.setBranch(updatedCustomer.getBranch());

        // Save and return updated customer
        return customerRepository.save(existingCustomer);
    }

    public List<Customer> getAllCustomersWithTransactions() {
        return customerRepository.findAllWithTransactions();

    }

    // Other logic
}
