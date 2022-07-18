package com.company.youtube.repository;

import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {


    Optional<CategoryEntity> findByName(String name);

    List<CategoryEntity> findAllByVisible(Boolean visible);

    boolean existsByNameAndVisible(String name, Boolean b);

    @Modifying
    @Transactional
    @Query("update  CategoryEntity c set c.visible=FALSE where c.id=?1")
    void updateStatusById(Integer pId);
}
