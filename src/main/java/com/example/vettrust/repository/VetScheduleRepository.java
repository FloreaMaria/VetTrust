package com.example.vettrust.repository;

import com.example.vettrust.model.VetSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VetScheduleRepository extends JpaRepository<VetSchedule, Long> {
    List<VetSchedule> findAllByVetUserId(Long id);


}
