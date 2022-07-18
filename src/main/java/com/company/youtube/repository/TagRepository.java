package com.company.youtube.repository;

import com.company.youtube.entity.CategoryEntity;
import com.company.youtube.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {


    Optional<TagEntity> findByName(String name);

    List<TagEntity> findAllByVisible(Boolean visible);

    boolean existsByNameAndVisible(String name, Boolean b);

    @Modifying
    @Transactional
    @Query("update  TagEntity t set t.visible=FALSE where t.id=?1")
    void updateStatusById(Integer pId);

    @Query("select new TagEntity (t.id,t.name) " +
            " from TagEntity t " +
            " inner join VideoTagEntity vt " +
            " on vt.tag.id = t.id " +
            " where vt.video.id = ?1")
    List<TagEntity> listByVideoId(String videoId);
}
