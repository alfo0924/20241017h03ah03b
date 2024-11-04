package org.example;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;

public class BaseballForceOutTest {

    @Test
    public void testEmptyBases() {
        // 空壘時，只有一壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("x");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnerOnFirst() {
        // 一壘有人時，一、二壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("1B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }

    @Test
    public void testRunnersOnFirstAndThird() {
        // 一、三壘有人時，一、二壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("1B, 3B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }

    @Test
    public void testRunnerOnThird() {
        // 三壘有人時，只有一壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("3B");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnersOnFirstAndSecond() {
        // 一、二壘有人時，一、二、三壘都可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);
    }

    @Test
    public void testBasesLoaded() {
        // 滿壘時，一、二、三壘都可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B, 3B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);
    }

    @Test
    public void testRunnerOnSecond() {
        // 二壘有人時，只有一壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("2B");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnersOnSecondAndThird() {
        // 二、三壘有人時，只有一壘可以封殺
        List<String> result = BaseballForceOut.getForceOutBases("2B, 3B");
        assertEquals(Arrays.asList("1B"), result);
    }

    @Test
    public void testRunnerOnFirstAndThirdWithSpaces() {
        // 測試輸入格式包含空格的情況
        List<String> result = BaseballForceOut.getForceOutBases("1B,  3B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }

    @Test
    public void testRunnerOnFirstAndThirdWithoutSpaces() {
        // 測試輸入格式不包含空格的情況
        List<String> result = BaseballForceOut.getForceOutBases("1B,3B");
        assertEquals(Arrays.asList("1B", "2B"), result);
    }
}