package ru.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Nikolay Radzivon
 * @Date 18.06.2024
 */
class StatsServiceAppsTest {

    @Test
    void mainTest() {
        Assertions.assertDoesNotThrow(StatsServiceApps::new);
        Assertions.assertDoesNotThrow(() -> StatsServiceApps.main(new String[]{}));
    }
}