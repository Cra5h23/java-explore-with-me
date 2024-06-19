package ru.practicum.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.RequestHitDto;

import javax.servlet.http.HttpServletRequest;
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
        RequestHitDto dto = makeDto(request, appName);

        postForLocation(ewmStatsServiceUrl + "/hit", dto);
        log.info("Создан запрос: POST {}/hit body={}", ewmStatsServiceUrl, dto);
    }

    @Override
    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
        var url = new StringBuilder(ewmStatsServiceUrl + "/stats?");
        Map<String, Object> uriVariables = new HashMap<>();

        if (start != null) {
            var startEncode = URLEncoder.encode(start, StandardCharsets.UTF_8);
            uriVariables.put("start", startEncode);
            url.append("start={start}");
        }

        if (end != null) {
            var endEncode = URLEncoder.encode(end, StandardCharsets.UTF_8);
            uriVariables.put("end", endEncode);
            url.append("&end={end}");
        }

        if (uris != null) {
            uriVariables.put("uris", uris.toArray());
            url.append("&uris={uris}");
        }

        if (unique != null) {
            uriVariables.put("unique", unique);
            url.append("&unique={unique}");
        }
        log.info("Создан запрос: GET {}", url);

        return getForEntity(url.toString(), Object.class, uriVariables);
    }

    private RequestHitDto makeDto(HttpServletRequest request, String appName) {
        var requestURI = request.getRequestURI();
        var remoteAddr = request.getRemoteAddr();
        var timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return RequestHitDto.builder()
                .ip(remoteAddr)
                .uri(requestURI)
                .app(appName)
                .timestamp(timestamp)
                .build();
    }
}
