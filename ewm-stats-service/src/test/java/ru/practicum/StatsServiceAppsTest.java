package ru.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nikolay Radzivon
 * @Date 16.06.2024
 */
class StatsServiceAppsTest {

    @Test
    void mainTest() {
        Assertions.assertDoesNotThrow(StatsServiceApps::new);
        Assertions.assertDoesNotThrow(() -> StatsServiceApps.main(new String[]{}));
    }
}