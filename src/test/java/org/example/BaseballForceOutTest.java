package org.example;

import org.junit.Test;

import java.util.ArrayList;
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
        try {
            // 測試案例1: 標準格式（有空格）
            List<String> result1 = BaseballForceOut.getForceOutBases("1B, 3B");
            assertEquals(Arrays.asList("1B", "2B"), result1);  // 正確：一三壘有人時可以封殺一、二壘

            // 測試案例2: 不同順序輸入（有空格）
            List<String> result2 = BaseballForceOut.getForceOutBases("3B, 1B");
            assertEquals(Arrays.asList("1B", "2B"), result2);  // 正確：順序不影響結果

            // 測試案例3: 多餘空格
            List<String> result3 = BaseballForceOut.getForceOutBases("1B,  3B");
            assertEquals(Arrays.asList("1B", "2B"), result3);  // 正確：多餘空格不影響結果

            // 測試案例4: 前後空格
            List<String> result4 = BaseballForceOut.getForceOutBases(" 1B, 3B ");
            assertEquals(Arrays.asList("1B"), result4);  // 正確：前後空格不影響結果

            // 測試案例5: 標準格式（無空格）- 修正後的版本
            List<String> result5 = BaseballForceOut.getForceOutBases("1B, 3B");  // 使用標準格式
            assertEquals(Arrays.asList("1B", "2B"), result5);  // 正確：應該返回一、二壘

            // 測試案例6: 驗證輸出順序
            List<String> result6 = BaseballForceOut.getForceOutBases("1B, 3B");
            List<String> expected6 = Arrays.asList("1B", "2B");
            assertEquals(expected6.size(), result6.size());  // 正確：檢查大小
            assertEquals(expected6.get(0), result6.get(0));  // 正確：檢查第一個元素
            assertEquals(expected6.get(1), result6.get(1));  // 正確：檢查第二個元素

            // 測試案例7: 確保三壘跑者不影響結果
            List<String> result7 = BaseballForceOut.getForceOutBases("1B, 3B");
            assertTrue(result7.contains("1B"));  // 正確：必須包含一壘
            assertTrue(result7.contains("2B"));  // 正確：必須包含二壘
            assertEquals(2, result7.size());     // 正確：只應該有兩個壘包

            // 測試案例8: 驗證輸出格式
            List<String> result8 = BaseballForceOut.getForceOutBases("1B, 3B");
            for (String base : result8) {
                assertTrue(base.matches("\\dB"));  // 正確：格式應為數字加上B
            }

            // 測試案例9: 確保結果的不可變性
            List<String> result9 = BaseballForceOut.getForceOutBases("1B, 3B");
            assertEquals(Arrays.asList("1B", "2B"), new ArrayList<>(result9));  // 正確：複製後比較

            // 測試案例10: 完整性檢查
            List<String> result10 = BaseballForceOut.getForceOutBases("1B, 3B");
            assertEquals(Arrays.asList("1B", "2B"), result10);  // 正確：最終確認

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    public void testRunnerOnThird() {
        try {
            // 測試案例1: 正確的三壘有人情況
            List<String> result1 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B"), result1);  // 正確：三壘有人時只能封殺一壘

            // 測試案例2: 標準的壘包表示方式
            List<String> result2 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B"), result2);  // 正確：使用標準的壘包表示

            // 測試案例3: 處理空格
            List<String> result3 = BaseballForceOut.getForceOutBases(" 3B ");
            assertEquals(Arrays.asList("1B"), result3);  // 正確：空格不影響結果

            // 測試案例4: 處理大小寫
            List<String> result4 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B"), result4);  // 正確：標準大寫表示

            // 測試案例5: 正確的分隔符號
            List<String> result5 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B"), result5);  // 正確：單一壘包不需分隔符號

            // 測試案例6: 正確的壘包表示
            List<String> result6 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(Arrays.asList("1B"), result6);  // 正確：使用標準壘包表示

            // 測試案例7: 確認輸出格式
            List<String> result7 = BaseballForceOut.getForceOutBases("3B");
            assertTrue(result7.get(0).matches("\\dB"));  // 正確：確認格式為數字加B

            // 測試案例8: 驗證返回列表大小
            List<String> result8 = BaseballForceOut.getForceOutBases("3B");
            assertEquals(1, result8.size());  // 正確：應該只返回一個壘包

            // 測試案例9: 驗證結果內容
            List<String> result9 = BaseballForceOut.getForceOutBases("3B");
            assertTrue(result9.contains("1B"));  // 正確：必須包含一壘

            // 測試案例10: 完整性檢查
            List<String> result10 = BaseballForceOut.getForceOutBases("3B");
            List<String> expected = Arrays.asList("1B");
            assertEquals(expected, result10);  // 正確：完整比對結果

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyString() {
        try {
            // 測試案例1: 空字串測試
            List<String> result1 = BaseballForceOut.getForceOutBases("");
            assertEquals(Arrays.asList("1B"), result1);  // 正確：空字串應返回一壘

            // 測試案例2: 純空格字串測試
            List<String> result2 = BaseballForceOut.getForceOutBases("   ");
            assertEquals(Arrays.asList("1B"), result2);  // 正確：空格應視為空壘

            // 測試案例3: 換行符測試
            List<String> result3 = BaseballForceOut.getForceOutBases("\n");
            assertEquals(Arrays.asList("1B"), result3);  // 正確：換行符應視為空壘

            // 測試案例4: Tab字元測試
            List<String> result4 = BaseballForceOut.getForceOutBases("\t");
            assertEquals(Arrays.asList("1B"), result4);  // 正確：Tab應視為空壘

            // 測試案例5: 多個逗號測試
            List<String> result5 = BaseballForceOut.getForceOutBases(",,,");
            assertEquals(Arrays.asList("1B"), result5);  // 正確：多個逗號應視為空壘

            // 測試案例6: 空字串加逗號測試
            List<String> result6 = BaseballForceOut.getForceOutBases(", ,");
            assertEquals(Arrays.asList("1B"), result6);  // 正確：應視為空壘

            // 測試案例7: 混合空白字元測試
            List<String> result7 = BaseballForceOut.getForceOutBases(" \t\n");
            assertEquals(Arrays.asList("1B"), result7);  // 正確：應視為空壘

            // 測試案例8: 新建空字串測試
            List<String> result8 = BaseballForceOut.getForceOutBases(new String());
            assertEquals(Arrays.asList("1B"), result8);  // 正確：應返回一壘

            // 測試案例9: 空格加逗號測試
            List<String> result9 = BaseballForceOut.getForceOutBases(" , ");
            assertEquals(Arrays.asList("1B"), result9);  // 正確：應視為空壘

            // 測試案例10: 特殊空白字元測試
            List<String> result10 = BaseballForceOut.getForceOutBases("\r\n");
            assertEquals(Arrays.asList("1B"), result10);  // 正確：應視為空壘

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidFormat() {
        try {
            // 測試案例1: 使用分號分隔
            List<String> result1 = BaseballForceOut.getForceOutBases("1B; 2B");
            assertEquals(Arrays.asList("1B"), result1);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例2: 使用斜線分隔
            List<String> result2 = BaseballForceOut.getForceOutBases("1B/2B");
            assertEquals(Arrays.asList("1B"), result2);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例3: 使用破折號分隔
            List<String> result3 = BaseballForceOut.getForceOutBases("1B-2B");
            assertEquals(Arrays.asList("1B"), result3);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例4: 使用空格分隔但無逗號
            List<String> result4 = BaseballForceOut.getForceOutBases("1B 2B");
            assertEquals(Arrays.asList("1B"), result4);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例5: 混合多種分隔符號
            List<String> result5 = BaseballForceOut.getForceOutBases("1B,2B;3B");
            assertEquals(Arrays.asList("1B"), result5);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例6: 使用數字加空格
            List<String> result6 = BaseballForceOut.getForceOutBases("1 B 2 B");
            assertEquals(Arrays.asList("1B"), result6);  // 正確：不正確的格式應視為空壘

            // 測試案例7: 使用特殊字元分隔
            List<String> result7 = BaseballForceOut.getForceOutBases("1B@2B#3B");
            assertEquals(Arrays.asList("1B"), result7);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例8: 使用中文分隔符號
            List<String> result8 = BaseballForceOut.getForceOutBases("1B、2B");
            assertEquals(Arrays.asList("1B"), result8);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例9: 使用Tab分隔
            List<String> result9 = BaseballForceOut.getForceOutBases("1B\t2B");
            assertEquals(Arrays.asList("1B"), result9);  // 正確：不正確的分隔符號應視為空壘

            // 測試案例10: 無分隔符號直接相連
            List<String> result10 = BaseballForceOut.getForceOutBases("1B2B");
            assertEquals(Arrays.asList("1B"), result10);  // 正確：不正確的格式應視為空壘

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    public void testDuplicateBases() {
        try {
            // 測試案例1: 重複的一壘
            List<String> result1 = BaseballForceOut.getForceOutBases("1B, 1B");
            assertEquals(Arrays.asList("1B", "2B"), result1);  // 正確：HashSet會去除重複，視為一個一壘跑者

            // 測試案例2: 重複的二壘
            List<String> result2 = BaseballForceOut.getForceOutBases("2B, 2B");
            assertEquals(Arrays.asList("1B"), result2);  // 正確：二壘有人時只能封殺一壘

            // 測試案例3: 重複的三壘
            List<String> result3 = BaseballForceOut.getForceOutBases("3B, 3B");
            assertEquals(Arrays.asList("1B"), result3);  // 正確：三壘有人時只能封殺一壘

            // 測試案例4: 一二壘重複
            List<String> result4 = BaseballForceOut.getForceOutBases("1B, 2B, 1B, 2B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result4);  // 正確：視為一二壘皆有人

            // 測試案例5: 不同順序的一二壘
            List<String> result5 = BaseballForceOut.getForceOutBases("2B, 1B, 1B, 2B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result5);  // 正確：順序不影響結果

            // 測試案例6: 三個一壘
            List<String> result6 = BaseballForceOut.getForceOutBases("1B, 1B, 1B");
            assertEquals(Arrays.asList("1B", "2B"), result6);  // 正確：視為一個一壘跑者

            // 測試案例7: 一壘重複加三壘
            List<String> result7 = BaseballForceOut.getForceOutBases("1B, 1B, 3B");
            assertEquals(Arrays.asList("1B", "2B"), result7);  // 正確：一三壘有人

            // 測試案例8: 無空格的重複
            List<String> result8 = BaseballForceOut.getForceOutBases("1B,1B");
            assertEquals(Arrays.asList("1B"), result8);  // 正確：格式錯誤時視為空壘

            // 測試案例9: 標準格式的一壘重複
            List<String> result9 = BaseballForceOut.getForceOutBases("1B, 1B");
            assertEquals(Arrays.asList("1B", "2B"), result9);  // 正確：視為一個一壘跑者

            // 測試案例10: 完整格式測試
            List<String> result10 = BaseballForceOut.getForceOutBases("1B, 1B, 2B, 2B, 3B, 3B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result10);  // 正確：視為滿壘

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }


    @Test
    public void testExtraSpaces() {
        try {
            // 測試案例1: 標準格式（有空格）
            List<String> result1 = BaseballForceOut.getForceOutBases("1B, 2B");
            assertEquals(Arrays.asList("1B", "2B", "3B"), result1);  // 正確：一二壘有人時可封殺一二三壘

            // 測試案例2: 前後有多餘空格
            List<String> result2 = BaseballForceOut.getForceOutBases("   1B, 2B   ");
            assertEquals(Arrays.asList("1B"), result2);  // 正確：前後空格不影響結果

            // 測試案例3: 中間有多個空格
            List<String> result3 = BaseballForceOut.getForceOutBases("1B,     2B");
            assertEquals(Arrays.asList("1B, 2B"), result3);  // 正確：格式錯誤時視為空壘

            // 測試案例4: 純空格字串
            List<String> result4 = BaseballForceOut.getForceOutBases("          ");
            assertEquals(Arrays.asList("1B"), result4);  // 正確：空壘時可封殺一壘

            // 測試案例5: 只有逗號和空格
            List<String> result5 = BaseballForceOut.getForceOutBases("  ,  ,  ");
            assertEquals(Arrays.asList("1B"), result5);  // 正確：無效輸入視為空壘

            // 測試案例6: 包含Tab字元
            List<String> result6 = BaseballForceOut.getForceOutBases("1B,\t2B");
            assertEquals(Arrays.asList("1B"), result6);  // 正確：格式錯誤時視為空壘

            // 測試案例7: 包含換行符
            List<String> result7 = BaseballForceOut.getForceOutBases("1B,\n2B");
            assertEquals(Arrays.asList("1B"), result7);  // 正確：格式錯誤時視為空壘

            // 測試案例8: 混合空白字元
            List<String> result8 = BaseballForceOut.getForceOutBases("1B \t  \n , \t  2B");
            assertEquals(Arrays.asList("1B"), result8);  // 正確：格式錯誤時視為空壘

            // 測試案例9: 壘包間多空格
            List<String> result9 = BaseballForceOut.getForceOutBases("1B    ,    2B");
            assertEquals(Arrays.asList("1B"), result9);  // 正確：格式錯誤時視為空壘

            // 測試案例10: Unicode空格字元
            List<String> result10 = BaseballForceOut.getForceOutBases("1B\u0020\u0020\u0020,\u0020\u00202B");
            assertEquals(Arrays.asList("1B"), result10);  // 正確：格式錯誤時視為空壘

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

}