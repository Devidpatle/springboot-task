package Devid.example.transactionsdemo.controller;

import Devid.example.transactionsdemo.entity.Branch;
import Devid.example.transactionsdemo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public List<Branch> getAllBranches(){
        return branchService.getAllBranches();
    }

    @GetMapping("/{id}")
    public Branch getBranchById(@PathVariable Long id){
        return branchService.getBranchById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id " + id));
    }
     @PostMapping
    public Branch saveBranch(@RequestBody Branch branch){
        return branchService.saveBranch(branch);
     }

    @DeleteMapping("/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
    }
    // Other CRUD operations and mappings as needed


    @PutMapping("/{id}")
    public Branch updateBranch(@PathVariable Long id, @RequestBody Branch updatedBranch) {
        // Retrieve the existing branch from the database
        Branch existingBranch = branchService.getBranchById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id " + id));

        // Update the fields of the existing branch with the values from the updated branch
        existingBranch.setName(updatedBranch.getName());

        // Save the updated branch
        return branchService.saveBranch(existingBranch);
}
}