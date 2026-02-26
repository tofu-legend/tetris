# JFinal 俄罗斯方块（JDK 1.8）

这是一个基于 **Java + JFinal** 的可运行俄罗斯方块小游戏，前端使用原生 HTML/CSS/JavaScript。

## 环境要求

- JDK 1.8（`1.8.0`）
- Maven 3.6+

## 运行方式

### 方式 1：命令行

```bash
mvn clean package
java -jar target/jfinal-tetris-1.0.0.jar
```

然后在浏览器中访问：

- http://localhost:8080

### 方式 2：IntelliJ IDEA 直接运行

1. 用 IDEA 打开项目根目录（含 `pom.xml`）。
2. 确认 Project SDK 使用 **JDK 1.8**。
3. 运行 `com.tetris.Main` 的 `main` 方法。
4. 如访问页面空白或 404，请检查 **Working directory** 是否是项目根目录（`/workspace/tetris`）。

> 说明：项目启动时会优先使用 `src/main/webapp`（IDEA 开发模式）；如果是 `java -jar` 运行则自动从 classpath 解出页面资源，因此两种方式都可直接启动。

## 操作说明

- `←` / `→`：左右移动
- `↑`：旋转方块
- `↓`：快速下落
- `空格`：直接落地


## 常见问题（IDEA）

### 报错：直接打开项目后一堆类爆红 / 依赖找不到

大概率是 **还没有按 Maven 项目导入**，所以 IDEA 没有下载依赖。

#### 怎么“选 Maven”

> 你不需要新建项目类型，**只要打开含 `pom.xml` 的目录并导入 Maven 即可**。

**方法 A（推荐）**
1. `File -> Open`，选择项目根目录（能看到 `pom.xml`）。
2. 右下角如果出现 `Load Maven Project`，点击它。
3. 右侧 Maven 工具窗口点击刷新（Reimport）。

**方法 B（如果你已经打开了，但没识别成 Maven）**
1. 在项目树里右键 `pom.xml`。
2. 选择 `Add as Maven Project`。
3. 再点 Maven 窗口刷新。

#### 如果你没有看到 `Add as Maven Project`

不同 IDEA 版本菜单不一样，按下面任一方式都可以：

1. **Maven 工具窗口导入**
   - `View -> Tool Windows -> Maven`
   - 在 Maven 窗口点 `+` 或 `Attach Maven Projects`
   - 选择项目里的 `pom.xml`

2. **从 Project Structure 里导入模块**
   - `File -> Project Structure -> Modules`
   - 点 `+` -> `Import Module`
   - 选择 `pom.xml`，按 Maven 模块导入

3. **重新以 Maven 方式打开**
   - 关闭项目后重新 `File -> Open`
   - 直接选 `pom.xml`（不是只选文件夹）
   - 出现提示时选 `Open as Project` / `Trust Project` / `Load Maven`

> 重点：只要 IDEA 右侧出现 Maven 窗口并能看到 Lifecycle（clean/compile/package），就说明已经按 Maven 识别成功。

#### 导入后再检查
1. `File -> Project Structure -> Project`：Project SDK 选 **JDK 1.8**。
2. 在 Maven 面板执行 `Lifecycle -> compile`（能过说明依赖已拉取）。
3. 再运行 `com.tetris.Main`。

### 报错：`JFinal.start(..., Main.class)` 找不到合适的方法

这是 **JFinal 版本 API 差异** 导致的。你本地依赖只支持：

```java
JFinal.start(String webAppDir, int port, String context, int scanIntervalSeconds)
```

本项目已改为 4 参数写法：

```java
JFinal.start("src/main/webapp", 8080, "/", 5);
```

如果你本地仍旧报这个错，请 Maven 面板刷新并重新 `mvn clean compile`。

### 报错：`<version>${jfinal.version}</version>`、`<version>4.13.2</version>` 报红

这一般不是版本号写错，而是 **IDEA 还没成功解析 Maven 依赖**（本地仓库没下载下来或 Maven 导入失败）。

#### 快速修复

1. 先确认项目已按 Maven 导入（右键 `pom.xml` -> `Add as Maven Project`）。
2. 打开右侧 Maven 窗口，点击 **Reload All Maven Projects**。
3. 在 IDEA 终端执行：

```bash
mvn -U clean compile
```

4. 如果命令行能成功，回到 IDEA 执行 `File -> Invalidate Caches -> Invalidate and Restart`。

#### 如果依赖下载失败（国内常见）

给 `~/.m2/settings.xml` 配镜像（例如阿里云），再重新 Reload Maven：

```xml
<mirrors>
  <mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>aliyun maven</name>
    <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
</mirrors>
```

#### 额外检查

- `Settings -> Build Tools -> Maven -> Maven home path` 是否可用。
- `Settings -> Build Tools -> Maven -> User settings file` 是否指向正确的 `settings.xml`。
- Project SDK / Maven Runner JRE 是否都为可用 JDK（本项目建议 1.8）。

### 报错：`Can't start Git: D:\ProgramFiles\Git\bin\git.exe`

这个错误和项目代码无关，是 **IDEA 的 Git 可执行文件路径配置错误**。

#### 修复步骤（Windows）

1. 打开 IDEA：`File -> Settings -> Version Control -> Git`
2. 在 **Path to Git executable** 中选择你机器上真实存在的 `git.exe`，常见路径：
   - `C:\Program Files\Git\bin\git.exe`
   - `C:\Program Files\Git\cmd\git.exe`（更推荐）
3. 点击 **Test**，显示版本号即成功。
4. 点 **Apply / OK**。

#### 如果没有安装 Git

1. 下载安装 Git for Windows（官网：https://git-scm.com/download/win）
2. 安装后在 IDEA 里重新配置上面的路径并 Test。

#### 命令行自检（PowerShell）

```powershell
git --version
where git
```

如果能输出版本和路径，说明系统里的 Git 正常。

