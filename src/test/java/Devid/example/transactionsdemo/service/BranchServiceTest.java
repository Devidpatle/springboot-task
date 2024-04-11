package Devid.example.transactionsdemo.service;

import Devid.example.transactionsdemo.entity.Branch;
import Devid.example.transactionsdemo.repository.BranchRepository;
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
public class BranchServiceTest {
    @InjectMocks
    private BranchService branchService;
    @Mock
    private BranchRepository branchRepository;

    private Branch myBranch;
    private Branch yourBranch;

    @BeforeEach
    void init() {
        myBranch = new Branch();
        myBranch.setId(1L);
        myBranch.setName("State Bank Of India");

        // Assert
        yourBranch = new Branch();
        yourBranch.setId(2L);
        yourBranch.setName("Bank of Maharashtra");

    }

    @Test
    @DisplayName("Should save the branch object to database")
    void save() {
//        Branch myBranch = new Branch();
//        myBranch.setId(1L);
//        myBranch.setName("State Bank Of India");

        when(branchRepository.save(any(Branch.class))).thenReturn(myBranch);
        Branch newBranch = branchService.saveBranch(myBranch);

        Assertions.assertNotNull(newBranch);
        assertThat(newBranch.getName()).isEqualTo("State Bank Of India");
    }

    @Test
    @DisplayName("It should the return all branches")
    void getAllBranches() {
//        Branch myBranch = new Branch();
//        myBranch.setName("State Bank Of India");
//        myBranch.setId(1L);
        branchRepository.save(myBranch);

        // Assert
//        Branch yourBranch = new Branch();
//        yourBranch.setName("Bank of Maharashtra");
//        yourBranch.setId(2L);
        branchRepository.save(yourBranch);
        // Assert
        List<Branch> list = new ArrayList<>();
        list.add(yourBranch);
        list.add(myBranch);

        when(branchRepository.findAll()).thenReturn(list);

        List<Branch> branches = branchService.getAllBranches();
//      Assertions.assertNotNull(yourBranch.getId());
        Assertions.assertEquals(2, branches.size());
        Assertions.assertNotNull(list);
    }

    @Test
    @DisplayName("It should the return service branch with Id")
    void getBranchById() {
        // Act
//        Branch myBranch = new Branch();
//        myBranch.setName("State Bank Of India");
//        myBranch.setId(1L);

        when(branchRepository.findById(anyLong())).thenReturn(Optional.of(myBranch));
        Optional<Branch> existingBranch = branchService.getBranchById(1L);

        Assertions.assertTrue(existingBranch.isPresent()); // Check if optional has a value
        Assertions.assertEquals(1L, existingBranch.get().getId()); // Access id inside optional
    }

    @Test
    @DisplayName("It should the return updated service branch(Into database)")
    void updateBranch() {
//        Branch myBranch = new Branch();
//        myBranch.setName("State Bank Of India");
//        myBranch.setId(1L);

        when(branchRepository.findById(anyLong())).thenReturn(Optional.of(myBranch));
        when(branchRepository.save(any(Branch.class))).thenReturn(myBranch);
        myBranch.setName("Reserve Bank Of India");

        Branch updatedBranch = branchService.updateBranch(1L, myBranch);

        Assertions.assertNotNull(updatedBranch);
        Assertions.assertEquals("Reserve Bank Of India", updatedBranch.getName());
    }


    @Test
    @DisplayName("It should the delete the branch from database")
    void deleteBranch() {
//
//        Branch myBranch = new Branch();
//        myBranch.setId(1L);
//        myBranch.setName("State Bank Of India");

//        when(branchRepository.findById(anyLong())).thenReturn(Optional.of(myBranch));
//        doNothing().when(branchRepository).delete(any(Branch.class));

        branchService.deleteBranch(myBranch.getId());
        verify(branchRepository, times(1)).deleteById(anyLong());
    }

}
