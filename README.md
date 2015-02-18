# Crafter
写Bukkit插件的得力助手

An assistant for writing Bukkit Plugins

基于 MIT 协议开源

Open Source License: MIT

    >>>
    README.md 有中文和 English 两种语言版本
    This README.md has two language versions: 中文 and English
    >>>

中文
===
`Crafter尚在开发中，API可能经常变更。`

使用方法： Maven 3 ，下载，编译，安装，依赖。推荐把Crafter打包成独立插件。Crafter的下载将在之后放出。

亦可以打包进插件使用（但不推荐同时安装多个以打包形式使用Crafter的插件）。打包时如果需要删除没有用到的类，可以考虑在pom中加入：
```xml
        	<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>2.3</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<minimizeJar>true</minimizeJar>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

Crafter 包含以下框架：

commands
---
commands 框架

* 轻松创建命令，无须费心检测 sender 和权限

* 轻松创建包含子命令的主命令
  
示例
    
类 `MySubCommand` 中:
```java
public MySubCommand()
{
  this.setAction("sub");
  this.setPermission("perm.node");
  this.setPlayerNeeded(true); // 自动检查及确认 sender 是玩家
}

@Override
protected void executeCommand(CommandSender sender, String[] args)
{
  Player p = (Player)sender; // 不需要检测sender
  p.doSomething();
}
```

主类的 `onEnable()` 方法中:
```java
CrafterMainCommand mainCommand = new CrafterMainCommand(this);
mainCommand.addSubcommand(new MySubCommand());
getCommand("mycmd").setExecutor(mainCommand);
```
然后，一个主命令和一个子命令就创建好啦！当你输入`/mycmd sub`时，主命令会自动找到
action 为`sub`的子命令来执行。

```java
// 1.1.0版起，ICrafterCommand支持一种新的使用方法：
aCommand.setAction("action").setHelp(help).setPlayerNeeded(true)...;
```

configs
---
configs 框架
* 轻松创建和管理配置和数据文件
  
示例
```java
// 下面这行会创建一个 config manager ，其会自动从jar中复制出默认文件config.yml
config = new CrafterYamlConfigManager(new File(this.getDataFolder(), "config.yml"), true, this); // “this”为 
                                                                                                 // JavaPlugin
Map<String, String> map = config.getStringMap("path"); // 其中有一些便利的方法
```
```java
// 看我如何创建一个数据文件管理器，虽然它不从jar复制出默认文件，但它会定时保存
data = new CrafterYamlConfigManager(new File(this.getDataFolder(), "data.yml"), false, this);
data.setAutoSaveInterval(this, 60); // “this”是你的JavaPlugin，“60”是以秒为单位的保存间隔
```
```java
// 1.1.0版起，有一个自动重载配置文件的方法：
@ReloadableConfig(priority = 2345, group = "group1") // 用这个注解来标注可以重载的 IConfigManager 。优先级是一个 	                                                     // int ，默认为0。优先级越高，越先被重载。
                                                     // group 用来标识该 IConfigManager 所属的组。默认为“”。
                                                     // 一个 CrafterConfigReloader 只负责一个组。
arConfig = new CrafterYamlConfigManager(new File(this.getDataFolder(), "config1.yml"), true, this);

@ReloadableConfig(group = "group2") // 这个注解的 group 为 group2 ， 不会被下面的 scr 重载。
thatConfig = new CrafterYamlConfigManager(new File(this.getDataFolder(), "thatConfig.yml"), true, this);

CrafterConfigReloader scr = new CrafterConfigReloader("group1");
scr.addClass(this); // 当 scr.doReload() 被调用时，它会在已经添加的类中找出里面所有可以重载的且属于自己负责的组的
                    // IConfigManager ，按优先级排序，并逐个执行 load() 。
                    
// 另请参见： CrafterReloadCommand 。
// 感谢 Dummyc0m 提出优先级的想法。
```
```java
// 1.1.0版起，IConfigManager支持一种新的使用方法：
aConfigManager.load().set(path, value).set(path, value).set(...)...;
```

databases
---
databases 框架
* 轻松连接数据库

示例
```java
CrafterDataBaseConnectionManager sdcm = new CrafterDataBaseConnectionManager().setDbType("mysql").setHost("localhost").setPort(3306).setUsername("root").setPassword("changeme");
sdcm.getConnection().preparedStatement(...)...; // sdcm.getConnection() 会确保连接可用
sdcm.close();
```

locales
---
locales 框架
* 轻松管理多语言环境

示例
```java
// 来创建一个语言环境为“zh_CN”的 locale manager ：
locale = new CrafterLocaleManager("zh_CN", this.getDataFolder(), true, this); // “true”使其自动从jar中复制
                                                                              // /locales/zh_CN.yml 到 
                                                                              // this.getDataFolder()
                                                                              // “this” 为 JavaPlugin
String localizedString = locale.getLocalizedString("msg1");
```

notifiers
---
notifiers 框架
* 轻松通知

示例
```java
// 我用这个方法来通知服务器管理员：插件启动时出错了。
// 我写出这个的原因是有时你的服务器插件太多，启动过程中的 stacktrace 太不显眼。

// 在 onEnable() 中：
public void onEnable()
{
    BootCompleteNotifier bcn = new BootCompleteNotifier(this, 2000); // 更多信息请查看 javadocs
    // 加载配置或执行其它任务
    ...
    // 最终上面的所有代码都被执行，即插件启动成功。
    bcn.cancel();
}

// 当然，上面只是一种使用的情况。
```
    
Utils
---
Crafter 还有些别的工具包:)



English
===
`Crafter is still under development so the API may often change.`

Usage: Maven 3. Download. Compile. Install. Depend. Packing Crafter into a standalone jar is recommended. Downloads will be open later.
You can also pack Crafter into your plugin jar(But using multiple plugins with Crafter packaged is NOT recommended). If you want to remove those unused classes in your jar, consider adding the
following snippet to your pom:
```xml
        	<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>2.3</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<minimizeJar>true</minimizeJar>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

It contains following frameworks:

commands
---
The commands framework
* Create your commands without the need to worry about the checking of sender or permission
* Create your main command with subcommands quickly

e.g.
    
In class `MySubCommand`:
```java
public MySubCommand()
{
  this.setAction("sub");
  this.setPermission("perm.node");
  this.setPlayerNeeded(true); // Do this and the sender will be checked automatically
}

@Override
protected void executeCommand(CommandSender sender, String[] args)
{
  Player p = (Player)sender; // No need to check if the sender is a player!
  p.doSomething();
}
```

And in `onEnable()` method of your main class:
```java
CrafterMainCommand mainCommand = new CrafterMainCommand(this);
mainCommand.addSubcommand(new MySubCommand());
getCommand("mycmd").setExecutor(mainCommand);
```
Then it's done! When you type `/mycmd sub`, the main command will automatically
get you the subcommand with the action `sub`.

```java
// Since 1.1.0, there's a new way to use ICrafterCommand:
aCommand.setAction("action").setHelp(help).setPlayerNeeded(true)...;
```

configs
---
The configs framework
* create and deal with config file or data file easily

e.g.
```java
// The following line creates a config manager which gets the config.yml from your
// jar automatically
config = new CrafterYamlConfigManager(new File(this.getDataFolder(), "config.yml"), true, this); // 'this' is your
                                                                                                 // JavaPlugin
Map<String, String> map = config.getStringMap("path"); // Convenient methods are included
```
```java
// Now let's create a data manager which doesn't copy anything from your jar, but
// saves regularly
data = new CrafterYamlConfigManager(new File(this.getDataFolder(), "data.yml"), false, this);
data.setAutoSaveInterval(this, 60); // where 'this' is your JavaPlugin, and '60' is the 
                                    // interval in seconds
```
```java
// Since 1.1.0, there is a new way to reload configs automatically:
@ReloadableConfig(priority = 2345, group = "group1") // Use this annotation to mark which IConfigManager should be
                                                     // reloaded automatically. The priority is an int whose
                                                     // default value is 0. The group parameter is used to mark
                                                     // the group this IConfigManager belongs to. Default: ''. A 
                                                     // CrafterConfigReloader only cares about ONE group.
                                                     // An IConfigManager with a higher priority will be reloaded 
                                                     // before those having lower priorities.
arConfig = new new CrafterYamlConfigManager(new File(this.getDataFolder(), "config1.yml"), true, this);

@ReloadableConfig(group = "group2") // This annotation's group parameter is set to 'group2' ,so it will NOT be
                                    // reloaded by the scr below.
thatConfig = new new CrafterYamlConfigManager(new File(this.getDataFolder(), "thatConfig.yml"), true, this);

CrafterConfigReloader scr = new CrafterConfigReloader("group1");
scr.addClass(this); // When scr.doReload() is called, it will search all classes added for all IConfigManagers
                    // with annotation @ReloadableConfig and annotation param group==scr's group then sort them 
                    // by priority and reload one by one.
                    
// See also: CrafterReloadCommand
// Thanks Dummyc0m for his idea of priority.
```
```java
// Since 1.1.0, there's a new way to use IConfigManager:
aConfigManager.load().set(path, value).set(path, value).set(...)...;
```
databases
---
The databases framework
* easily connect to your databases

e.g.
```java
CrafterDataBaseConnectionManager sdcm = new CrafterDataBaseConnectionManager().setDbType("mysql").setHost("localhost").setPort(3306).setUsername("root").setPassword("changeme");
sdcm.getConnection().preparedStatement(...)...; // sdcm.getConnection() ensures the connection is ready
sdcm.close();
```

locales
---
The locales framework
* easily get localized strings

e.g.
```java
// The following line creates a locale manager with locale "zh_CN"
locale = new CrafterLocaleManager("zh_CN", this.getDataFolder(), true, this); // 'true' tells it to copy a default
                                                                              // locale file from jar at
                                                                              // /locales/zh_CN.yml to the data 
                                                                              // folder; 'this' is your JavaPlugin
String localizedString = locale.getLocalizedString("msg1");
```
notifiers
---
The notifiers framework
* notify easily

e.g.
```java
// I use this method to notify the server admin if there's something wrong on boot.
// The reason I wrote this was that there're sometimes lots of plugins installed and you may not notice the
// stacktrace or something else gave out by the plugin when the server is booting.

// In onEnable():
public void onEnable()
{
    BootCompleteNotifier bcn = new BootCompleteNotifier(this, 2000); // See javadocs for more info
    // Now you can load config, etc.
    ...
    // Finally all code above is executed, so the boot is successful.
    bcn.cancel();
}

// Of course, you can use the notifiers in other cases.
```

Utils
---
It also has some util classes:)
