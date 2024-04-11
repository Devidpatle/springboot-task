package Devid.example.transactionsdemo.controller;

import Devid.example.transactionsdemo.entity.Branch;
import Devid.example.transactionsdemo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//    @DeleteMapping("/{id}")
//    public void deleteBranch(@PathVariable Long id) {
//        branchService.deleteBranch(id);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }


    // BranchController.java
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch updatedBranch) {
        // Update the branch using the service method
        Branch updated = branchService.updateBranch(id, updatedBranch);

        // Return response with updated branch and status code 200 (OK)
        return ResponseEntity.ok(updated);
    }

}