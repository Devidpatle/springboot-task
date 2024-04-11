package Devid.example.transactionsdemo.service;

import Devid.example.transactionsdemo.entity.Transaction;
import Devid.example.transactionsdemo.repository.TransactionRepository;
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
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    private Transaction myTransaction;
    private Transaction yourTransaction;

    @BeforeEach
    void init() {
        myTransaction = new Transaction();
        myTransaction.setId(1L);
        myTransaction.setMode("Credit");

        yourTransaction = new Transaction();
        yourTransaction.setId(2L);
        yourTransaction.setMode("Debit");
    }

    @Test
    @DisplayName("Should save the transaction object to database")
    void save() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(myTransaction);
        Transaction newTransaction = transactionService.saveTransaction(myTransaction);

        Assertions.assertNotNull(newTransaction);
        assertThat(newTransaction.getMode()).isEqualTo("Credit");
    }

    @Test
    @DisplayName("It should the return all transactions")
    void getAllTransactions() {
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(yourTransaction);
        mockTransactions.add(myTransaction);

        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        List<Transaction> transactions = transactionService.getAllTransactions();

        Assertions.assertEquals(2, transactions.size());
        Assertions.assertNotNull(transactions);
    }

    @Test
    @DisplayName("It should return the transaction with specified Id")
    void getTransactionById() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(myTransaction));
        Optional<Transaction> existingTransaction = transactionService.getTransactionById(1L);

        Assertions.assertTrue(existingTransaction.isPresent());
        Assertions.assertEquals(1L, existingTransaction.get().getId());
    }

    @Test
    @DisplayName("It should delete the transaction from database")
    void deleteTransaction() {
        doNothing().when(transactionRepository).deleteById(anyLong());

        transactionService.deleteTransaction(myTransaction.getId());

        verify(transactionRepository, times(1)).deleteById(anyLong());
    }
}
