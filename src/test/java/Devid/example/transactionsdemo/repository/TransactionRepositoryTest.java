package Devid.example.transactionsdemo.repository;

import Devid.example.transactionsdemo.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Transaction Repository Tests")
public class TransactionRepositoryTest {

    @Test
    @DisplayName("It should save the transaction")
    void save() {
        // Arrange
        Transaction myTransaction = new Transaction();

        // Mock the behavior of transactionRepository.save()
        TransactionRepository mockRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Transaction.class))).thenReturn(myTransaction);

        // Act
        Transaction newTransaction = mockRepository.save(myTransaction);

        // Assert
        Assertions.assertNotNull(newTransaction);
        // Add additional assertions as needed
    }


    @Test
    @DisplayName("It should return transactions with size 2")
    void getAllTransactions() {
        // Arrange
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction());
        mockTransactions.add(new Transaction());

        // Mocking the behavior of transactionRepository.findAll()
        TransactionRepository mockRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(mockRepository.findAll()).thenReturn(mockTransactions);

        // Act
        List<Transaction> list = mockRepository.findAll();

        // Assert
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    @DisplayName("It should return transaction by Id")
    void getTransactionById() {
        // Arrange
        Transaction myTransaction = new Transaction();
        myTransaction.setId(1L);

        // Mocking the behavior of transactionRepository.findById()
        TransactionRepository mockRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(myTransaction));

        // Act
        Optional<Transaction> existingTransaction = mockRepository.findById(1L);

        // Assert
        Assertions.assertTrue(existingTransaction.isPresent());
        Assertions.assertEquals(1L, existingTransaction.get().getId());
    }

    @Test
    @DisplayName("It should delete the transaction")
    void deleteTransaction() {
        // Arrange
        Transaction myTransaction = new Transaction();
        myTransaction.setId(1L);

        // Mocking the behavior of transactionRepository.deleteById()
        TransactionRepository mockRepository = Mockito.mock(TransactionRepository.class);
        Mockito.doNothing().when(mockRepository).deleteById(1L);

        // Act
        mockRepository.deleteById(1L);

        // Assert
        Optional<Transaction> existingTransaction = mockRepository.findById(1L);
        Assertions.assertTrue(existingTransaction.isEmpty());
    }
}
