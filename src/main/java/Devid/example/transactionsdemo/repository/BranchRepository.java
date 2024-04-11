package Devid.example.transactionsdemo.repository;
import Devid.example.transactionsdemo.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByName(String name);
    //void deleteAll();
}
