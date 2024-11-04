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
        try {
            // 測試案例1: 錯誤的預期結果
            List<String> result1 = BaseballForceOut.getForceOutBases("x");
            assertEquals(Arrays.asList("1B", "2B"), result1);  // 錯誤：空壘時不應該可以封殺二壘

            // 測試案例2: 錯誤的大小寫處理
            List<String> result2 = BaseballForceOut.getForceOutBases("X");
            assertEquals(Arrays.asList(), result2);  // 錯誤：應該要返回一壘

            // 測試案例3: 錯誤的空格處理
            List<String> result3 = BaseballForceOut.getForceOutBases(" x ");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result3);  // 錯誤：不應該可以封殺二、三壘

            // 測試案例4: 錯誤的逗號處理
            List<String> result4 = BaseballForceOut.getForceOutBases("x,");
            assertEquals(Arrays.asList(), result4);  // 錯誤：應該要返回一壘

            // 測試案例5: 錯誤的重複符號處理
            List<String> result5 = BaseballForceOut.getForceOutBases("x, x");
            assertEquals(Arrays.asList("1B", "2B"), result5);  // 錯誤：重複的空壘符號不應該影響結果

            // 測試案例6: 錯誤的空字串處理
            List<String> result6 = BaseballForceOut.getForceOutBases("");
            assertEquals(Arrays.asList(), result6);  // 錯誤：空字串應該返回一壘

            // 測試案例7: 錯誤的null處理
            List<String> result7 = BaseballForceOut.getForceOutBases(null);
            assertEquals(Arrays.asList("1B"), result7);  // 錯誤：應該拋出NullPointerException

            // 測試案例8: 錯誤的無效符號處理
            List<String> result8 = BaseballForceOut.getForceOutBases("0");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result8);  // 錯誤：無效符號不應該可以封殺所有壘包

            // 測試案例9: 錯誤的特殊字元處理
            List<String> result9 = BaseballForceOut.getForceOutBases("@#$");
            assertEquals(Arrays.asList(), result9);  // 錯誤：特殊字元應該視為空壘

            // 測試案例10: 錯誤的空格字串處理
            List<String> result10 = BaseballForceOut.getForceOutBases(" ");
            assertEquals(Arrays.asList("1B", "2B"), result10);  // 錯誤：空格不應該可以封殺二壘

            // 測試案例11: 錯誤的多空格處理
            List<String> result11 = BaseballForceOut.getForceOutBases("   ");
            assertEquals(Arrays.asList(), result11);  // 錯誤：多個空格應該視為空壘

            // 測試案例12: 錯誤的換行符處理
            List<String> result12 = BaseballForceOut.getForceOutBases("\n");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result12);  // 錯誤：換行符不應該可以封殺所有壘包

            // 測試案例13: 錯誤的tab符號處理
            List<String> result13 = BaseballForceOut.getForceOutBases("\t");
            assertEquals(Arrays.asList("HB"), result13);  // 錯誤：不應該出現本壘

            // 測試案例14: 錯誤的混合空白字元處理
            List<String> result14 = BaseballForceOut.getForceOutBases(" \t\n ");
            assertEquals(Arrays.asList("1B", "2B", "3B", "HB"), result14);  // 錯誤：不應該可以封殺所有壘包

            // 測試案例15: 錯誤的分隔符號處理
            List<String> result15 = BaseballForceOut.getForceOutBases("x; x");
            assertEquals(Arrays.asList("2B", "3B"), result15);  // 錯誤：應該要包含一壘

        } catch (Exception e) {
            // 錯誤的異常處理
            System.out.println("發生異常: " + e.getMessage());
            // 不應該忽略異常
        }
    }
    @Test
    public void testRunnerOnFirst() {
        try {
            // 測試案例1: 錯誤的預期結果（預期可以封殺所有壘包）
            List<String> result1 = BaseballForceOut.getForceOutBases("1B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result1);  // 錯誤：一壘有人時只能封殺一、二壘

            // 測試案例2: 錯誤的壘包表示方式
            List<String> result2 = BaseballForceOut.getForceOutBases("First");
            assertEquals(Arrays.asList("1B", "2B"), result2);  // 錯誤：應該使用標準的壘包表示方式

            // 測試案例3: 錯誤的空格處理
            List<String> result3 = BaseballForceOut.getForceOutBases(" 1B ");
            assertEquals(Arrays.asList("2B", "3B"), result3);  // 錯誤：結果缺少一壘

            // 測試案例4: 錯誤的大小寫處理
            List<String> result4 = BaseballForceOut.getForceOutBases("1b");
            assertEquals(Arrays.asList("1B", "2B", "3B", "HB"), result4);  // 錯誤：不應該包含本壘

            // 測試案例5: 錯誤的分隔符號
            List<String> result5 = BaseballForceOut.getForceOutBases("1B;");
            assertEquals(Arrays.asList(), result5);  // 錯誤：應該返回正確的封殺壘包

            // 測試案例6: 錯誤的多餘符號
            List<String> result6 = BaseballForceOut.getForceOutBases("1B,");
            assertEquals(Arrays.asList("1B"), result6);  // 錯誤：應該包含二壘

            // 測試案例7: 錯誤的數字表示
            List<String> result7 = BaseballForceOut.getForceOutBases("1");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result7);  // 錯誤：不正確的壘包表示方式

            // 測試案例8: 錯誤的特殊字元
            List<String> result8 = BaseballForceOut.getForceOutBases("1B#");
            assertEquals(Arrays.asList("HB"), result8);  // 錯誤：不應該出現本壘

            // 測試案例9: 錯誤的順序
            List<String> result9 = BaseballForceOut.getForceOutBases("1B");
            assertEquals(Arrays.asList("2B", "1B"), result9);  // 錯誤：順序錯誤

            // 測試案例10: 重複的壘包
            List<String> result10 = BaseballForceOut.getForceOutBases("1B, 1B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result10);  // 錯誤：不應該接受重複的壘包

        } catch (Exception e) {
            // 錯誤的異常處理：忽略異常
            System.out.println("發生異常: " + e.getMessage());
        }
    }

    @Test
    public void testRunnersOnFirstAndThird() {
        try {
            // 測試案例1: 錯誤的預期結果（預期可以封殺所有壘包）
            List<String> result1 = BaseballForceOut.getForceOutBases("1B, 3B");
            assertEquals(Arrays.asList("1B", "2B", "3B", "HB"), result1);  // 錯誤：不應該可以封殺三壘和本壘

            // 測試案例2: 錯誤的壘包順序
            List<String> result2 = BaseballForceOut.getForceOutBases("3B, 1B");
            assertEquals(Arrays.asList("2B", "1B", "3B"), result2);  // 錯誤：順序錯誤且不應該可以封殺三壘

            // 測試案例3: 錯誤的壘包表示方式
            List<String> result3 = BaseballForceOut.getForceOutBases("First, Third");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result3);  // 錯誤：使用了錯誤的壘包表示方式

            // 測試案例4: 錯誤的分隔符號
            List<String> result4 = BaseballForceOut.getForceOutBases("1B; 3B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result4);  // 錯誤：使用了分號而不是逗號

            // 測試案例5: 重複的壘包
            List<String> result5 = BaseballForceOut.getForceOutBases("1B, 3B, 3B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result5);  // 錯誤：重複的三壘

            // 測試案例6: 錯誤的空格處理
            List<String> result6 = BaseballForceOut.getForceOutBases("1B,3B");
            assertEquals(Arrays.asList("1B"), result6);  // 錯誤：沒有空格應該影響結果

            // 測試案例7: 多餘的逗號
            List<String> result7 = BaseballForceOut.getForceOutBases("1B, 3B,");
            assertEquals(Arrays.asList("3B", "2B", "1B"), result7);  // 錯誤：順序錯誤且多餘的逗號

            // 測試案例8: 使用數字表示
            List<String> result8 = BaseballForceOut.getForceOutBases("1, 3");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result8);  // 錯誤：使用數字而不是標準表示

            // 測試案例9: 大小寫混合
            List<String> result9 = BaseballForceOut.getForceOutBases("1b, 3B");
            assertEquals(Arrays.asList("HB", "1B", "2B"), result9);  // 錯誤：不應該出現本壘且大小寫敏感

            // 測試案例10: 包含特殊字元
            List<String> result10 = BaseballForceOut.getForceOutBases("1B#, 3B@");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result10);  // 錯誤：包含特殊字元

        } catch (Exception e) {
            // 錯誤的異常處理：忽略異常
            System.out.println("發生異常: " + e.getMessage());
        }
    }
    @Test
    public void testRunnerOnThird() {
        try {
            // 測試案例1: 錯誤的預期結果（預期可以封殺本壘）
            List<String> result1 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B", "HB"), result1);  // 錯誤：三壘有人時不能封殺本壘

            // 測試案例2: 錯誤的壘包表示方式
            List<String> result2 = BaseballForceOut.getForceOutBases("Third");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result2);  // 錯誤：使用了錯誤的壘包表示

            // 測試案例3: 錯誤的空格處理
            List<String> result3 = BaseballForceOut.getForceOutBases(" 3B ");
            assertEquals(Arrays.asList("3B", "HB"), result3);  // 錯誤：預期錯誤的封殺順序

            // 測試案例4: 錯誤的大小寫處理
            List<String> result4 = BaseballForceOut.getForceOutBases("3b");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result4);  // 錯誤：不應該可以封殺所有壘包

            // 測試案例5: 錯誤的分隔符號
            List<String> result5 = BaseballForceOut.getForceOutBases("3B;");
            assertEquals(Arrays.asList(), result5);  // 錯誤：應該要返回一壘

            // 測試案例6: 錯誤的數字表示
            List<String> result6 = BaseballForceOut.getForceOutBases("3");
            assertEquals(Arrays.asList("3B", "HB"), result6);  // 錯誤：使用錯誤的壘包表示

            // 測試案例7: 錯誤的特殊字元
            List<String> result7 = BaseballForceOut.getForceOutBases("3B#");
            assertEquals(Arrays.asList("HB"), result7);  // 錯誤：不應該只返回本壘

            // 測試案例8: 錯誤的多重表示
            List<String> result8 = BaseballForceOut.getForceOutBases("3B, 3B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result8);  // 錯誤：重複的壘包

            // 測試案例9: 錯誤的順序預期
            List<String> result9 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("3B", "2B", "1B"), result9);  // 錯誤：順序錯誤

            // 測試案例10: 錯誤的組合
            List<String> result10 = BaseballForceOut.getForceOutBases("3B, HB");
            assertEquals(Arrays.asList("1B", "2B", "3B", "HB"), result10);  // 錯誤：不合理的組合

        } catch (Exception e) {
            // 錯誤的異常處理：忽略異常
            System.out.println("發生異常: " + e.getMessage());
        }
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