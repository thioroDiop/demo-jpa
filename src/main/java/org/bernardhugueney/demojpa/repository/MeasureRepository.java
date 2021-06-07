package org.bernardhugueney.demojpa.repository;

import org.bernardhugueney.demojpa.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {

    Optional<Measure> findFirstByTypeOrderByValueDesc(String measureType);

    Optional<Measure> findFirstByTypeOrderByMeasureDateDesc(String measureType);

    /*
    @Query(value="SELECT m FROM Measure m WHERE m.type = ?1 AND"+
            " m.measureDate BETWEEN ?2 AND ?3")
    List<Measure> myFind(String measureType, LocalDateTime startDate, LocalDateTime endDate);

     */
    @Query(value="SELECT m FROM Measure m WHERE m.type = :type AND"+
            " m.measureDate BETWEEN :startDate AND :endDate")
    List<Measure> myFind(@Param("type") String measureType,
                         @Param("startDate") LocalDateTime startDate,
                         @Param("endDate") LocalDateTime endDate);

    List<Measure> findByTypeAndMeasureDateBetween(String measureType, LocalDateTime startDate, LocalDateTime endDate);
}