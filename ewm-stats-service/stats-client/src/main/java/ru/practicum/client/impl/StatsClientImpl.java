package ru.practicum.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.RequestStatsDto;


import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikolay Radzivon
 * @Date 15.06.2024
 */
@Service
@Slf4j
public class StatsClientImpl extends RestTemplate implements StatsClient {
    @Value("${ewm-stats-service.url}")
    private String ewmStatsServiceUrl;

    public void saveStats(HttpServletRequest request, String appName) {
        var dto = makeDto(request, appName);

        postForLocation(ewmStatsServiceUrl + "/hit", dto);
        log.info("POST {}/hit body={}", ewmStatsServiceUrl, dto);
    }

    @Override
    public ResponseEntity<Object> getStats(String start, String end, List<URI> uris, Boolean unique) {
        var startEncode = URLEncoder.encode(start, StandardCharsets.UTF_8);
        var endEncode = URLEncoder.encode(end, StandardCharsets.UTF_8);

        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("start", startEncode);
        uriVariables.put("end", endEncode);
        uriVariables.put("uris", uris.toArray());
        uriVariables.put("unique", unique);

        log.info("GET {}stats?start={}&end={}&uris={}&unique={}",
                ewmStatsServiceUrl, startEncode, endEncode, uris, unique);
        return getForEntity(ewmStatsServiceUrl + "?start={start}&end={end}&uris={uris}&unique={unique}",
                Object.class, uriVariables);
    }

    private RequestStatsDto makeDto(HttpServletRequest request, String appName) {
        var requestURI = request.getRequestURI();
        var remoteAddr = request.getRemoteAddr();
        var timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return RequestStatsDto.builder()
                .ip(remoteAddr)
                .uri(requestURI)
                .app(appName)
                .timestamp(timestamp)
                .build();
    }
}
