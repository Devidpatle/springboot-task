package Devid.example.transactionsdemo.controller;

import Devid.example.transactionsdemo.entity.Branch;
import Devid.example.transactionsdemo.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BranchControllerTest {

    @MockBean
    private BranchService branchService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Branch branch;

    @BeforeEach
    void init() {
        branch = new Branch();
        branch.setName("Test Branch");
        branch.setId(1L);
    }

    @Test
    @DisplayName("It should create a new branch")
    void createNewBranch() throws Exception {
        when(branchService.saveBranch(any(Branch.class))).thenReturn(branch);

        this.mockMvc.perform(post("/branches").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(branch))).andExpect(jsonPath("$.name").value(branch.getName()));
    }

    @Test
    @DisplayName("It should fetch all branches")
    void fetchAllBranches() throws Exception {
        List<Branch> branchList = new ArrayList<>();
        branchList.add(branch);

        when(branchService.getAllBranches()).thenReturn(branchList);

        this.mockMvc.perform(get("/branches")).andExpect(jsonPath("$.size()", is(branchList.size())));
    }

    @Test
    @DisplayName("It should fetch branch by id")
    void fetchBranchById() throws Exception {
        when(branchService.getBranchById(anyLong())).thenReturn(Optional.of(branch));

        this.mockMvc.perform(get("/branches/{id}", 1L)).andExpect(jsonPath("$.name", is(branch.getName())));
    }

    @Test
    @DisplayName("It should delete a branch")
    void deleteBranch() throws Exception {
        doNothing().when(branchService).deleteBranch(anyLong());

        this.mockMvc.perform(delete("/branches/{id}", 1L)).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("It should update a branch")
    void updateBranch() throws Exception {
        // Create an existing branch
        Branch existingBranch = new Branch();
        existingBranch.setId(1L);
        existingBranch.setName("Existing Branch");

        // Mock the service to return the existing branch when fetching by ID
        when(branchService.getBranchById(1L)).thenReturn(Optional.of(existingBranch));

        // Perform the update operation
        this.mockMvc.perform(put("/branches/{id}", 1L).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(existingBranch))).andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").exists())      // Check if the ID exists in the response
//                .andExpect(jsonPath("$.name").value(existingBranch.getName())); // Check if the name matches the updated value
    }
}
