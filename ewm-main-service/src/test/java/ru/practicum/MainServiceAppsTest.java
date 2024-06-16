package ru.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Nikolay Radzivon
 * @Date 16.06.2024
 */
class MainServiceAppsTest {

    @Test
    void mainTest() {
        Assertions.assertDoesNotThrow(MainServiceApps::new);
        Assertions.assertDoesNotThrow(() -> MainServiceApps.main(new String[]{}));
    }
}