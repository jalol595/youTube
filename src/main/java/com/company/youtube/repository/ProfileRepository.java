package com.company.youtube.repository;

import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    boolean existsByEmail( String email);

    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.status=?1 where p.id=?2")
    void updateStatusById(ProfileStatus status, Integer pId);


    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.password=?1 where p.id=?2")
    void updatePassword(String password, Integer id);

    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.email=?1 where p.id=?2")
    void updateEmail(String email, Integer id);

    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.name=?1, p.surname=?2 where p.id=?3")
    void updateDetail(String name, String surname, Integer id);

    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.photo=?1 where p.id=?2")
    void deletePhoto(String pid, Integer id);

    @Modifying
    @Transactional
    @Query("update  ProfileEntity p set p.photo=?1 where p.id=?2")
    void updatePhoto(AttachEntity photoId, Integer id);
}
