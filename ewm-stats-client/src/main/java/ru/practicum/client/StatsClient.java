package ru.practicum.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Nikolay Radzivon
 * @Date 16.06.2024
 */
@Component
public class StatsClient extends RestTemplate {
public interface StatsClient {

    void saveStats(HttpServletRequest request, String appName);
}
