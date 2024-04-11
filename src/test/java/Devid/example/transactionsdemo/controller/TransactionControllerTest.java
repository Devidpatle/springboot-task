package Devid.example.transactionsdemo.controller;
import Devid.example.transactionsdemo.entity.Transaction;
import Devid.example.transactionsdemo.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testCreateTransaction() throws Exception {
        // Creating a sample transaction
        Transaction transaction = new Transaction();

        // Mocking service behavior
        Mockito.when(transactionService.saveTransaction(Mockito.eq(transaction))).thenReturn(transaction);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(MockMvcResultMatchers.status().isOk());

        // Verifying that the service method was called once
        Mockito.verify(transactionService, Mockito.times(1)).saveTransaction(Mockito.any(Transaction.class));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        // Mocking service behavior
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());
        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactions);

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isArray()).andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());

        // Verifying that the service method was called once
        Mockito.verify(transactionService, Mockito.times(1)).getAllTransactions();
    }

    @Test
    void testGetTransactionByIdFound() throws Exception {
        // Mocking service behavior
        Transaction transaction = new Transaction();
        Mockito.when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction));

        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        // Verifying that the service method was called once
        Mockito.verify(transactionService, Mockito.times(1)).getTransactionById(1L);
    }

    @Test
    void testDeleteTransaction() throws Exception {
        // Performing the request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        // Verifying that the service method was called once
        Mockito.verify(transactionService, Mockito.times(1)).deleteTransaction(1L);
    }
}
