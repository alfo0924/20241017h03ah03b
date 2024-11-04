package org.example;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseballForceOutTest2 {

    @Test
    public void testEmptyBases() {
        // 錯誤：空壘時預期可以封殺二壘
        List<String> result = BaseballForceOut.getForceOutBases("x");
        assertEquals(Arrays.asList("1B", "2B"), result);  // 應該只有 "1B"
    }

    @Test
    public void testRunnerOnFirst() {
        // 錯誤：一壘有人時預期可以封殺所有壘包
        List<String> result = BaseballForceOut.getForceOutBases("1B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);  // 應該只有 "1B", "2B"
    }

    @Test
    public void testRunnersOnFirstAndThird() {
        // 錯誤：一三壘有人時預期可以封殺三壘
        List<String> result = BaseballForceOut.getForceOutBases("1B, 3B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);  // 應該只有 "1B", "2B"
    }

    @Test
    public void testRunnerOnThird() {
        // 錯誤：三壘有人時預期可以封殺本壘
        List<String> result = BaseballForceOut.getForceOutBases("3B");
        assertEquals(Arrays.asList("1B", "HB"), result);  // 應該只有 "1B"
    }

    @Test
    public void testRunnersOnFirstAndSecond() {
        // 錯誤：一二壘有人時預期不可以封殺三壘
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B");
        assertEquals(Arrays.asList("1B", "2B"), result);  // 應該是 "1B", "2B", "3B"
    }

    @Test
    public void testBasesLoaded() {
        // 錯誤：滿壘時預期可以封殺本壘
        List<String> result = BaseballForceOut.getForceOutBases("1B, 2B, 3B");
        assertEquals(Arrays.asList("1B", "2B", "3B", "HB"), result);  // 應該只有 "1B", "2B", "3B"
    }

    @Test
    public void testInvalidInput() {
        // 錯誤：測試無效的輸入
        List<String> result = BaseballForceOut.getForceOutBases("XX, YY");
        assertEquals(Arrays.asList(), result);  // 應該拋出異常或返回 ["1B"]
    }

    @Test
    public void testNullInput() {
        try {
            // 錯誤版本1: 使用字串 "null" 而不是真正的 null
            List<String> result1 = BaseballForceOut.getForceOutBases("null");
            assertEquals(Arrays.asList("1B"), result1);  // 這不會拋出 NullPointerException

            // 錯誤版本2: 預期錯誤的異常類型
            assertThrows(IllegalArgumentException.class, () -> {
                BaseballForceOut.getForceOutBases(null);
            });  // 這會失敗，因為實際會拋出 NullPointerException

            // 錯誤版本3: 錯誤的異常處理方式
            List<String> result3 = BaseballForceOut.getForceOutBases(null);
            assertNull(result3);  // 這行不會執行到，因為上面會拋出異常

            // 錯誤版本4: 使用錯誤的斷言方法
            try {
                BaseballForceOut.getForceOutBases(null);
                fail("應該要拋出異常");
            } catch (Exception e) {
                assertTrue(e instanceof IllegalArgumentException);  // 錯誤的異常類型判斷
            }

            // 錯誤版本5: 沒有處理異常的情況
            List<String> result5 = BaseballForceOut.getForceOutBases(null);
            assertEquals(Arrays.asList(), result5);  // 預期空列表是錯誤的

        } catch (Exception e) {
            // 錯誤的錯誤處理：吞掉異常
            System.out.println("發生異常: " + e.getMessage());
        }
    }
    @Test
    public void testEmptyString() {
        // 錯誤：測試空字串
        List<String> result = BaseballForceOut.getForceOutBases("");
        assertEquals(Arrays.asList(), result);  // 應該返回 ["1B"]
    }

    @Test
    public void testInvalidFormat() {
        // 錯誤：測試錯誤的格式
        List<String> result = BaseballForceOut.getForceOutBases("1B;2B");
        assertEquals(Arrays.asList("1B", "2B"), result);  // 應該只返回 ["1B"]
    }

    @Test
    public void testDuplicateBases() {
        // 測試重複的壘包應該拋出異常
        assertThrows(IllegalArgumentException.class, () -> {
            BaseballForceOut.getForceOutBases("1B, 1B");
        });
    }

    @Test
    public void testExtraSpaces() {
        // 錯誤：測試多餘的空格
        List<String> result = BaseballForceOut.getForceOutBases("1B,    2B");
        assertEquals(Arrays.asList("1B", "2B", "3B"), result);  // 應該拋出異常或只返回 ["1B"]
    }
}