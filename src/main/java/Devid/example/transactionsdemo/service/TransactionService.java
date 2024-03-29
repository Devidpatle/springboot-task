package Devid.example.transactionsdemo.service;
import Devid.example.transactionsdemo.entity.Transaction;
import Devid.example.transactionsdemo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
//    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
//        Transaction existingTransaction = transactionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
//
//        // Update existing transaction properties
//        existingTransaction.setDate(updatedTransaction.getDate());
//        existingTransaction.setMode(updatedTransaction.getMode());
//        existingTransaction.setAccountNumber(updatedTransaction.getAccountNumber());
//        existingTransaction.setUpiId(updatedTransaction.getUpiId());
//        existingTransaction.setStatus(updatedTransaction.getStatus());
//        existingTransaction.setCustomer(updatedTransaction.getCustomer());
//
//        // Save and return updated transaction
//        return transactionRepository.save(existingTransaction);
//    }
    // Other business logic methods as needed

}
