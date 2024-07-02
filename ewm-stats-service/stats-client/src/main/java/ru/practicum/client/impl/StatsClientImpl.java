package ru.practicum.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;

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

    @Value("${datetime.format}")
    private String dateTimeFormat;

    @Override
    public void saveStats(String ip, String uri, String appName) {
        var dto = RequestHitDto.builder()
                .ip(ip)
                .uri(uri)
                .app(appName)
                .timestamp(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .build();

        postForLocation(ewmStatsServiceUrl + "/hit", dto);
        log.info("Создан запрос: POST {}/hit body={}", ewmStatsServiceUrl, dto);
    }

    @Override
    public ResponseEntity<List<ResponseStatsDto>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("start = {}, end={}, uris ={}, unique = {}", start, end, uris, unique);

        var url = new StringBuilder(ewmStatsServiceUrl + "/stats?");
        Map<String, Object> uriVariables = new HashMap<>();

        if (start != null) {
            var startEncode = URLEncoder.encode(start.format(DateTimeFormatter.ofPattern(dateTimeFormat)),
                    StandardCharsets.UTF_8);

            uriVariables.put("start", startEncode);
            url.append("start={start}");
        }

        if (end != null) {
//            var endEncode = URLEncoder.encode(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                    StandardCharsets.UTF_8);
            var endEncode = URLEncoder.encode(end.format(DateTimeFormatter.ofPattern(dateTimeFormat)),
                    StandardCharsets.UTF_8);
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
        log.info("Создан запрос: GET {}/stats?start={}&end={}&uris={}&unique={}", ewmStatsServiceUrl, start, end, uris, unique);

        return exchange(url.toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseStatsDto>>() {
                }, uriVariables);
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
