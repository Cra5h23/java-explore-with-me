package ru.practicum.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Nikolay Radzivon
 * @Date 11.07.2024
 */
@Component
@Profile("!test")
public class DatabaseInitializer implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE OR REPLACE FUNCTION distance(" +
                    "lat1 double precision, lon1 double precision, lat2 double precision, lon2 double precision)\n" +
                    "    RETURNS double precision\n" +
                    "AS $$\n" +
                    "DECLARE\n" +
                    "    dist double precision = 0;\n" +
                    "    rad_lat1 double precision;\n" +
                    "    rad_lat2 double precision;\n" +
                    "    theta double precision;\n" +
                    "    rad_theta double precision;\n" +
                    "BEGIN\n" +
                    "    IF lat1 = lat2 AND lon1 = lon2 THEN\n" +
                    "        RETURN dist;\n" +
                    "    ELSE\n" +
                    "        rad_lat1 = pi() * lat1 / 180;\n" +
                    "        rad_lat2 = pi() * lat2 / 180;\n" +
                    "        theta = lon1 - lon2;\n" +
                    "        rad_theta = pi() * theta / 180;\n" +
                    "        dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);\n" +
                    "        IF dist > 1 THEN\n" +
                    "            dist = 1;\n" +
                    "        END IF;\n" +
                    "        dist = acos(dist);\n" +
                    "        dist = dist * 180 / pi();\n" +
                    "        dist = dist * 60 * 1.1515 * 1.609344;\n" +
                    "        RETURN dist;\n" +
                    "    END IF;\n" +
                    "END;\n" +
                    "$$ LANGUAGE PLPGSQL;";
            stmt.executeUpdate(sql);
        }
    }
}
