package com.kostserver.repository;

import com.kostserver.model.entity.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface KostRepository extends JpaRepository<Kost,Long> {

    Optional<Kost> findById(Long id);

    @Query(value = "SELECT k FROM tbl_kost k WHERE k.owner.id = :id AND k.isDeleted = false",nativeQuery = false)
    List<Kost> getListKostByOwnerId(@Param("id")Long id);

    @Query("SELECT k FROM tbl_kost k")
    List<Kost> getAllKost();

    @Query(value = "UPDATE tbl_kost set is_deleted = true  WHERE id=:id", nativeQuery = true)
    void softDeleteKost(@Param("id") Long id);


}
