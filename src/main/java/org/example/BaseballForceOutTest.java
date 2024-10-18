package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class BaseballForceOutTest {

    @Test
    public void testEmptyBases() {
        List<String> result = BaseballForceOut.getForceOutBases("x");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnerOnFirst() {
        List<String> result = BaseballForceOut.getForceOutBases("1B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }

    @Test
    public void testRunnersOnFirstAndThird() {
        List<String> result = BaseballForceOut.getForceOutBases("1B, 3B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }

    @Test
    public void testRunnerOnThird() {
        List<String> result = BaseballForceOut.getForceOutBases("3B");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnersOnFirstAndSecond() {
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);
    }

    @Test
    public void testBasesLoaded() {
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B, 3B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);
    }
}
