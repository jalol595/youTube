package com.company.youtube.repository;



import com.company.youtube.entity.EmailHistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EmailHistoryRepository extends PagingAndSortingRepository<EmailHistoryEntity, Integer> {


    Optional<EmailHistoryEntity> findByEmail(String s);

}
