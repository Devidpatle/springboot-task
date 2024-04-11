package Devid.example.transactionsdemo.controller;

import Devid.example.transactionsdemo.entity.Transaction;
import Devid.example.transactionsdemo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id){
        return transactionService.getTransactionById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
    }

//    @PostMapping
//    public Transaction createTransaction(@RequestBody Transaction transaction) {
//        return transactionService.saveTransaction(transaction);
//    }
@PostMapping
public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
    Transaction savedTransaction = transactionService.saveTransaction(transaction);
    return ResponseEntity.ok(savedTransaction);
}


    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
