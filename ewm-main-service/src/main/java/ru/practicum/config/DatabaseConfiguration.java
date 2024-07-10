package ru.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Nikolay Radzivon
 * @Date 11.07.2024
 */
@Configuration
@Profile("!test")
public class DatabaseConfiguration {

    @Bean
    public String distanceFunction() {
        return "CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)\n" +
                "RETURNS float\n" +
                "LANGUAGE PLPGSQL\n" +
                "AS $$\n" +
                "declare\n" +
                "    dist float = 0;\n" +
                "    rad_lat1 float;\n" +
                "    rad_lat2 float;\n" +
                "    theta float;\n" +
                "    rad_theta float;\n" +
                "BEGIN\n" +
                "    IF lat1 = lat2 AND lon1 = lon2\n" +
                "    THEN\n" +
                "        RETURN dist;\n" +
                "    ELSE\n" +
                "        rad_lat1 = pi() * lat1 / 180;\n" +
                "        rad_lat2 = pi() * lat2 / 180;\n" +
                "        theta = lon1 - lon2;\n" +
                "        rad_theta = pi() * theta / 180;\n" +
                "        dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);\n" +
                "\n" +
                "        IF dist > 1\n" +
                "            THEN dist = 1;\n" +
                "        END IF;\n" +
                "\n" +
                "        dist = acos(dist);\n" +
                "        dist = dist * 180 / pi();\n" +
                "        dist = dist * 60 * 1.8524;\n" +
                "\n" +
                "        RETURN dist;\n" +
                "    END IF;\n" +
                "END;\n" +
                "$$;";
    }
}

