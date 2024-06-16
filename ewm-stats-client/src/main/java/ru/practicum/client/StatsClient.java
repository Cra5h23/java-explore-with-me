package ru.practicum.client;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 16.06.2024
 */
public interface StatsClient {

    void saveStats(HttpServletRequest request, String appName);

    ResponseEntity<Object> getStats(String start, String end, List<URI> uris, Boolean unique);
}
