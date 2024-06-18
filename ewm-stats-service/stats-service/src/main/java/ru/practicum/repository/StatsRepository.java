package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Stats;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsRepository extends JpaRepository<Stats, Long> {
//    @Query(value = "select " +
//            "count(distinct s.ip), " +
//            "s.app, " +
//            "s.uri " +
//            "from stats as s " +
//            "where s.timestamp between ?1 and ?2 " +
//            "group by s.app , s.uri ", nativeQuery = true)
//    List<CountStats> getCountDistinct(String start, String end);
//
//    @Query(value = "select " +
//            "count (s.ip), " +
//            "s.app, " +
//            "s.uri " +
//            "from stats as s " +
//            "where s.timestamp between ?1 and ?2 " +
//            "group by s.app, s.uri ", nativeQuery = true)
//    List<CountStats> getCount(String start, String end);
//
//    @Query(value = "select " +
//            "count (s.ip), " +
//            "s.app, " +
//            "s.uri " +
//            "from stats as s " +
//            "where (s.timestamp between ?1 and ?2) " +
//            "and s.uri in (?3)" +
//            "group by s.app, s.uri ", nativeQuery = true)
//    List<CountStats> getCountByUris(String start, String end, List<URI> uris);
//
//    @Query(value = "select " +
//            "count(distinct s.ip), " +
//            "s.app, " +
//            "s.uri " +
//            "from stats as s " +
//            "where (s.timestamp between ?1 and ?2) " +
//            "and s.uri in (?3)" +
//            "group by s.app , s.uri ", nativeQuery = true)
//    List<CountStats> getCountDistinctByUris(String start, String end, List<URI> uris);


    @Query(value = "SELECT count(s.ip) as count, " +
            "s.app as app, " +
            "s.uri as uri " +
            "from Stats as s " +
            "where ((:uris) IS NULL OR s.uri in :uris) and " +
            "(s.timestamp between :start and :end) " +
            "group by s.app, s.uri " +
            "order by count(s.ip) DESC ")
    List<CountStats> getCountHits(@Param("start") ZonedDateTime start,
                                  @Param("end") ZonedDateTime end,
                                  @Param("uris") List<String> uris
    );

    @Query(value = "SELECT count(distinct s.ip) as count, " +
            "s.app as app, " +
            "s.uri as uri " +
            "from Stats as s " +
            "where ((:uris) IS NULL OR s.uri in :uris) and " +
            "(s.timestamp between :start and :end) " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) DESC ")
    List<CountStats> getUniqueCountHits(@Param("start") ZonedDateTime start,
                                        @Param("end") ZonedDateTime end,
                                        @Param("uris") List<String> uris
    );

    interface CountStats {
        Long getCount();

        String getApp();

        String getUri();
    }
}

