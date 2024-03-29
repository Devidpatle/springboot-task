package Devid.example.transactionsdemo.entity;

import jakarta.persistence.*;
import Devid.example.transactionsdemo.entity.Transaction;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @ManyToOne
    private Branch branch;

    public  Customer(){

    }

    public Customer(String name, List<Transaction> transactions, Branch branch) {
        this.name = name;
        this.transactions = transactions;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}

