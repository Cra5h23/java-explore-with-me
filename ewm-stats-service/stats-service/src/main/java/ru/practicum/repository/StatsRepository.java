package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Hit;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsRepository extends JpaRepository<Hit, Long>, CustomStatsRepository {
//    @Query(value = "SELECT count(h.ip) as count, " +
//            "h.app as app, " +
//            "h.uri as uri " +
//            "from Hit as h " +
//            "where ((:uris) IS NULL OR h.uri in :uris) and " +
//            "(h.timestamp between :start and :end) " +
//            "group by h.app, h.uri " +
//            "order by count(h.ip) DESC ")
//    List<CountHits> getCountHits(@Param("start") ZonedDateTime start,
//                                 @Param("end") ZonedDateTime end,
//                                 @Param("uris") List<String> uris
//    );
//
//    @Query(value = "SELECT count(distinct h.ip) as count, " +
//            "h.app as app, " +
//            "h.uri as uri " +
//            "from Hit as h " +
//            "where ((:uris) IS NULL OR h.uri in :uris) and " +
//            "(h.timestamp between :start and :end) " +
//            "group by h.app, h.uri " +
//            "order by count(distinct h.ip) DESC ")
//    List<CountHits> getUniqueCountHits(@Param("start") ZonedDateTime start,
//                                       @Param("end") ZonedDateTime end,
//                                       @Param("uris") List<String> uris
//    );
//
//    interface CountHits {
//        Long getCount();
//
//        String getApp();
//
//        String getUri();
//    }
}

