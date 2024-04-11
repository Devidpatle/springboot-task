package Devid.example.transactionsdemo.controller;

import Devid.example.transactionsdemo.entity.Customer;
import Devid.example.transactionsdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomersWithTransactions() {
        return customerService.getAllCustomersWithTransactions();
    }

//    @GetMapping("/{id}")
//    public Customer getCustomerById(@PathVariable Long id){
//        return customerService.getCustomerById(id)
//                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
//    }
@GetMapping("/{id}")
public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
    Optional<Customer> customerOptional = customerService.getCustomerById(id);
    if (customerOptional.isPresent()) {
        Customer customer = customerOptional.get();
        return ResponseEntity.ok(customer);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
