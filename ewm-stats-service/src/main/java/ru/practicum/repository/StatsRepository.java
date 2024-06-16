package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stats;

import java.net.URI;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsRepository {
}
public interface StatsRepository extends JpaRepository<Stats, Long> {
    @Query(value = "select " +
            "count(distinct s.ip), " +
            "s.app, " +
            "s.uri " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.app , s.uri ", nativeQuery = true)
    List<CountStats> getCountDistinct(String start, String end);

    @Query(value = "select " +
            "count (s.ip), " +
            "s.app, " +
            "s.uri " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.app, s.uri ", nativeQuery = true)
    List<CountStats> getCount(String start, String end);

    @Query(value = "select " +
            "count (s.ip), " +
            "s.app, " +
            "s.uri " +
            "from stats as s " +
            "where (s.timestamp between ?1 and ?2) " +
            "and s.uri in (?3)" +
            "group by s.app, s.uri ", nativeQuery = true)
    List<CountStats> getCountByUris(String start, String end, List<URI> uris);

    @Query(value = "select " +
            "count(distinct s.ip), " +
            "s.app, " +
            "s.uri " +
            "from stats as s " +
            "where (s.timestamp between ?1 and ?2) " +
            "and s.uri in (?3)" +
            "group by s.app , s.uri ", nativeQuery = true)
    List<CountStats> getCountDistinctByUris(String start, String end, List<URI> uris);

    interface CountStats {
        Long getCount();

        String getApp();

        String getUri();
    }
}

