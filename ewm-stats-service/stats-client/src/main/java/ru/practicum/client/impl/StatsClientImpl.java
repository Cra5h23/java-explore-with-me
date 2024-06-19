package ru.practicum.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.RequestHitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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
    @Validated
    public ResponseEntity<Object> getStats(@NotNull String start, @NotNull String end, List<String> uris, Boolean unique) {
        var startEncode = URLEncoder.encode(start, StandardCharsets.UTF_8);
        var endEncode = URLEncoder.encode(end, StandardCharsets.UTF_8);
        var url = new StringBuilder(ewmStatsServiceUrl + "/stats?start={start}&end={end}");
        Map<String, Object> uriVariables = new HashMap<>();

        uriVariables.put("start", startEncode);
        uriVariables.put("end", endEncode);

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
