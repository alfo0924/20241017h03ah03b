

# 棒球封殺壘包判斷程式

## 程式結構

這個專案包含三個主要部分：
1. `BaseballForceOut` 類別：實現核心邏輯
2. `BaseballForceOutTest` 類別：包含單元測試
3. `pom.xml` 檔案：Maven 專案配置

## BaseballForceOut 類別

```java
package org.example;

import org.junit.Test;
import java.util.*;
import static junit.framework.Assert.assertEquals;

public class BaseballForceOut {
    public static List<String> getForceOutBases(String baseState) {
        List<String> forceOutBases = new ArrayList<>();
        Set<String> occupiedBases = new HashSet<>(Arrays.asList(baseState.split(", ")));

        // 一壘永遠是可封殺的
        forceOutBases.add("1B");

        // 如果一壘有人,二壘也是可封殺的
        if (occupiedBases.contains("1B")) {
            forceOutBases.add("2B");

            // 如果一二壘都有人,三壘也是可封殺的
            if (occupiedBases.contains("2B")) {
                forceOutBases.add("3B");
            }
        }

        return forceOutBases;
    }
}
```

### 說明

- `getForceOutBases` 方法接受一個字串參數 `baseState`，表示當前壘包狀態。
- 方法使用 `HashSet` 來儲存已佔據的壘包，便於快速查詢。
- 邏輯判斷：
  1. 一壘永遠可以封殺。
  2. 如果一壘有人，則二壘也可以封殺。
  3. 如果一壘和二壘都有人，則三壘也可以封殺。
- 方法返回一個包含所有可封殺壘包的列表。

## BaseballForceOutTest 類別

```java
package org.example;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertEquals;

public class BaseballForceOutTest {
    // 測試方法...
}
```

### 測試案例

1. `testEmptyBases`：測試無人在壘的情況
2. `testRunnerOnFirst`：測試只有一壘有人的情況
3. `testRunnersOnFirstAndThird`：測試一、三壘有人的情況
4. `testRunnerOnThird`：測試只有三壘有人的情況
5. `testRunnersOnFirstAndSecond`：測試一、二壘有人的情況
6. `testBasesLoaded`：測試滿壘的情況

每個測試案例都使用 `assertEquals` 方法來驗證 `getForceOutBases` 方法的返回結果是否符合預期。

## pom.xml 檔案

Maven 專案配置文件，主要內容包括：

1. 專案基本資訊：組織 ID、專案名稱、版本等
2. Java 編譯器版本設置（Java 17）
3. 專案依賴：
   - Jackson 資料綁定庫
   - PMD Java 程式碼分析工具
   - JSON Simple
   - JUnit 測試框架
4. 建置插件配置：
   - PMD 插件：用於程式碼品質分析
   - Maven Deploy Plugin：用於部署
   - Maven JXR Plugin：生成原始碼交叉引用
   - Maven Surefire Plugin：執行單元測試
   - Maven Site Plugin：生成專案網站
   - Maven Project Info Reports Plugin：生成專案資訊報告
5. 報告設定：配置 PMD 和 JXR 插件
6. 部署設定：配置部署儲存庫資訊

## 程式特點

1. **模組化設計**：核心邏輯和測試分離，便於維護和擴展。
2. **完整的測試覆蓋**：包含多種情況的單元測試，確保程式的正確性。
3. **使用 Maven 管理**：便於依賴管理、建置和部署。
4. **程式碼品質保證**：使用 PMD 進行靜態程式碼分析。

