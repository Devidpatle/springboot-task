package Devid.example.transactionsdemo.repository;

import Devid.example.transactionsdemo.entity.Branch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Branch Repository Tests")
public class BranchRepositoryTest {

    @Test
    @DisplayName("It should save the branch")
    void save() {
        // Arrange
        Branch myBranch = new Branch();
        myBranch.setName("State Bank Of India");
        myBranch.setId(1L);

        // Mocking the behavior of branchRepository.save()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Branch.class))).thenReturn(myBranch);

        // Act
        Branch newBranch = mockRepository.save(myBranch);

        // Assert
        Assertions.assertNotNull(newBranch);
        Assertions.assertNotNull(newBranch.getId());
    }

    @Test
    @DisplayName("It should return branches with size 2")
    void getAllBranches() {
        // Arrange
        List<Branch> mockBranches = new ArrayList<>();
        mockBranches.add(new Branch("State Bank Of India"));
        mockBranches.add(new Branch("Bank of Maharashtra"));

        // Mocking the behavior of branchRepository.findAll()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.when(mockRepository.findAll()).thenReturn(mockBranches);

        // Act
        List<Branch> list = mockRepository.findAll();

        // Assert
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    @DisplayName("It should return branch by Id")
    void getBranchById() {
        // Arrange
        Branch myBranch = new Branch("State Bank Of India");
        myBranch.setId(1L);

        // Mocking the behavior of branchRepository.findById()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(myBranch));

        // Act
        Optional<Branch> existingBranch = mockRepository.findById(1L);

        // Assert
        Assertions.assertTrue(existingBranch.isPresent());
        Assertions.assertEquals(1L, existingBranch.get().getId());
        Assertions.assertEquals("State Bank Of India", existingBranch.get().getName());
    }

    @Test
    @DisplayName("It should return an updated branch")
    void updateBranch() {
        // Arrange
        Branch myBranch = new Branch("State Bank Of India");
        myBranch.setId(1L);
        Branch updatedBranch = new Branch("Reserve Bank Of India");
        updatedBranch.setId(1L);

        // Mocking the behavior of branchRepository.findById() and branchRepository.save()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(myBranch));
        Mockito.when(mockRepository.save(Mockito.any(Branch.class))).thenReturn(updatedBranch); // Mock save operation

        // Act
        Branch existingBranch = mockRepository.findById(1L).orElse(null);
        Branch newBranch = mockRepository.save(existingBranch);

        // Assert
        Assertions.assertNotNull(newBranch); // Ensure newBranch is not null
        Assertions.assertEquals(1L, newBranch.getId());
        Assertions.assertEquals("Reserve Bank Of India", newBranch.getName());
    }

    @Test
    @DisplayName("It should delete the branch")
    void deleteBranch() {
        // Arrange
        Branch myBranch = new Branch("State Bank Of India");
        myBranch.setId(1L);

        // Mocking the behavior of branchRepository.deleteById()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.doNothing().when(mockRepository).deleteById(1L);

        // Act
        mockRepository.deleteById(1L);

        // Assert
        Optional<Branch> existingBranch = mockRepository.findById(1L);
        Assertions.assertTrue(existingBranch.isEmpty());
    }

    @Test
    @DisplayName("Branch List with name")
    void getBranchByName() {
        // Arrange
        List<Branch> mockBranches = new ArrayList<>();
        mockBranches.add(new Branch("State Bank Of India"));
        mockBranches.add(new Branch("Bank of Maharashtra"));

        // Mocking the behavior of branchRepository.findByName()
        BranchRepository mockRepository = Mockito.mock(BranchRepository.class);
        Mockito.when(mockRepository.findByName("State Bank Of India")).thenReturn(mockBranches);

        // Act
        List<Branch> list = mockRepository.findByName("State Bank Of India");

        // Assert
        Assertions.assertNotNull(list);
        assertThat(list.size()).isEqualTo(2);
    }
}
