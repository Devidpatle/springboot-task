package Devid.example.transactionsdemo.service;
import Devid.example.transactionsdemo.entity.Branch;
import Devid.example.transactionsdemo.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;
    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }
    public Optional<Branch> getBranchById(Long id){
        return branchRepository.findById(id);
    }
    public Branch saveBranch(Branch branch){
        return branchRepository.save(branch);
    }
    public void deleteBranch(Long id){
        branchRepository.deleteById(id);
    }
    public Branch updateBranch(Long id, Branch updatedBranch) {
        // Fetch the existing branch
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id " + id));

        // Update the properties of the existing branch with the values from the updated branch
        String updatedName = updatedBranch.getName();
        if (updatedName != null) {
            existingBranch.setName(updatedName);
        }

        // Save and return the updated branch
        return branchRepository.save(existingBranch);
    }
    // Other logic
}
