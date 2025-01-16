package com.secor.ecomcustomerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    Producer producer;

    @GetMapping
    public List<Customers> getAllCustomers() {
        return customersRepository.findAll();
    }

    @PostMapping
    public Customers addCustomer(@RequestBody Customers customer) {
        return customersRepository.save(customer);
    }

    @PutMapping("/{id}")
    public Customers updateCustomer(@PathVariable Long id, @RequestBody Customers customerDetails) throws JsonProcessingException {
        Customers customer = customersRepository.findById(id).orElseThrow();
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        producer.pubUpdateCustomerDetailsMessage(customerDetails.getFirstName(), "CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        return customersRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customersRepository.deleteById(id);
    }
}

