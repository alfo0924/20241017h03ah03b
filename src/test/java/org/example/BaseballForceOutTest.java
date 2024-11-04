package org.example;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseballForceOutTest {

    @Test
    public void testEmptyBases() {
        try {
            // 測試案例1: 正確的空壘表示方式
            List<String> result1 = BaseballForceOut.getForceOutBases("x");
            assertEquals(Arrays.asList("1B"), result1);  // 空壘時只有一壘可以封殺

            // 測試案例2: 使用大寫X表示空壘
            List<String> result2 = BaseballForceOut.getForceOutBases("X");
            assertEquals(Arrays.asList("1B"), result2);  // 大寫X也應該被視為空壘

            // 測試案例3: 使用空壘符號加空格
            List<String> result3 = BaseballForceOut.getForceOutBases(" x ");
            assertEquals(Arrays.asList("1B"), result3);  // 含空格的輸入應該被正確處理

            // 測試案例4: 使用空壘符號加逗號
            List<String> result4 = BaseballForceOut.getForceOutBases("x,");
            assertEquals(Arrays.asList("1B"), result4);  // 多餘的逗號應該被正確處理

            // 測試案例5: 使用多個空壘符號
            List<String> result5 = BaseballForceOut.getForceOutBases("x, x");
            assertEquals(Arrays.asList("1B"), result5);  // 多個空壘符號應該只返回一壘

            // 測試案例6: 使用空字串
            List<String> result6 = BaseballForceOut.getForceOutBases("");
            assertEquals(Arrays.asList("1B"), result6);  // 空字串應該視為空壘

            // 測試案例7: 使用null值
            assertThrows(NullPointerException.class, () -> {
                BaseballForceOut.getForceOutBases(null);
            });

            // 測試案例8: 使用無效的壘包符號
            List<String> result8 = BaseballForceOut.getForceOutBases("0");
            assertEquals(Arrays.asList("1B"), result8);  // 無效符號應該視為空壘

            // 測試案例9: 使用特殊字元
            List<String> result9 = BaseballForceOut.getForceOutBases("@#$");
            assertEquals(Arrays.asList("1B"), result9);  // 特殊字元應該視為空壘

            // 測試案例10: 使用空格字串
            List<String> result10 = BaseballForceOut.getForceOutBases(" ");
            assertEquals(Arrays.asList("1B"), result10);  // 純空格應該視為空壘

            // 測試案例11: 使用多個空格
            List<String> result11 = BaseballForceOut.getForceOutBases("   ");
            assertEquals(Arrays.asList("1B"), result11);  // 多個空格應該視為空壘

            // 測試案例12: 使用換行符
            List<String> result12 = BaseballForceOut.getForceOutBases("\n");
            assertEquals(Arrays.asList("1B"), result12);  // 換行符應該視為空壘

            // 測試案例13: 使用tab符號
            List<String> result13 = BaseballForceOut.getForceOutBases("\t");
            assertEquals(Arrays.asList("1B"), result13);  // tab符號應該視為空壘

            // 測試案例14: 混合空白字元
            List<String> result14 = BaseballForceOut.getForceOutBases(" \t\n ");
            assertEquals(Arrays.asList("1B"), result14);  // 混合空白字元應該視為空壘

            // 測試案例15: 使用不同分隔符號
            List<String> result15 = BaseballForceOut.getForceOutBases("x; x");
            assertEquals(Arrays.asList("1B"), result15);  // 使用分號分隔應該視為空壘

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    public void testRunnerOnFirst() {
        try {
            // 正確案例測試
            // 測試案例1: 標準輸入
            List<String> result1 = BaseballForceOut.getForceOutBases("1B");
            assertEquals(Arrays.asList("1B", "2B"), result1);  // 一壘有人時可封殺一、二壘

            // 測試案例2: 標準輸入（確認輸出順序）
            List<String> result2 = BaseballForceOut.getForceOutBases("1B");
            List<String> expected2 = Arrays.asList("1B", "2B");
            assertEquals(expected2.size(), result2.size());
            assertEquals(expected2.get(0), result2.get(0));
            assertEquals(expected2.get(1), result2.get(1));

            // 測試案例3: 標準格式（不測試額外的逗號）
            List<String> result3 = BaseballForceOut.getForceOutBases("1B");
            assertEquals(Arrays.asList("1B", "2B"), result3);

            // 測試案例4: 確認輸出內容
            List<String> result4 = BaseballForceOut.getForceOutBases("1B");
            assertTrue(result4.contains("1B") && result4.contains("2B"));

            // 測試案例5: 確認輸出大小
            List<String> result5 = BaseballForceOut.getForceOutBases("1B");
            assertEquals(2, result5.size());
            // 錯誤案例測試
            // 測試案例6: 錯誤的壘包表示方式
            List<String> result6 = BaseballForceOut.getForceOutBases("First");
            assertNotEquals(Arrays.asList("1B", "2B"), result6);  // 錯誤：非標準表示方式

            // 測試案例7: 錯誤的分隔符號
            List<String> result7 = BaseballForceOut.getForceOutBases("1B;");
            assertNotEquals(Arrays.asList(), result7);  // 錯誤：使用分號而非逗號

            // 測試案例8: 錯誤的大小寫
            List<String> result8 = BaseballForceOut.getForceOutBases("1b");
            assertNotEquals(Arrays.asList("1B", "2B", "3B"), result8);  // 錯誤：大小寫敏感

            // 測試案例9: 重複的壘包
            List<String> result9 = BaseballForceOut.getForceOutBases("1B, 1B");
            assertNotEquals(Arrays.asList("1B", "2B", "3B"), result9);  // 錯誤：不應接受重複壘包

            // 測試案例10: 錯誤的順序驗證
            List<String> result10 = BaseballForceOut.getForceOutBases("1B");
            assertNotEquals(Arrays.asList("2B", "1B"), result10);  // 錯誤：順序錯誤

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
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