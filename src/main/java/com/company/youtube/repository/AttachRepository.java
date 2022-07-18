package com.company.youtube.repository;



import com.company.youtube.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {

    boolean existsById(String id);

}
