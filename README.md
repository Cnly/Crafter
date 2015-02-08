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
==

Crafter 包含以下框架：

commands
---
commands 框架可以让你:

  轻松创建命令，无须担心 sender 和权限的检测

  轻松创建包含子命令的主命令

示例
    
类 MySubCommand 中:
    
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


主类的 onEnable() 方法中:
    
    SimpleMainCommand mainCommand = new SimpleMainCommand(this);
    mainCommand.addSubcommand(new MySubCommand());
    getCommand("mycmd").setExecutor(mainCommand);

然后，一个主命令和一个子命令就创建好啦！当你输入“/mycmd sub”时，主命令会自动找到
action 为“sub”的子命令来执行。

configs
---
configs 框架让你轻松创建和管理配置和数据文件

示例
    
    // 下面这行会创建一个 config manager ，其会自动从jar中复制出默认文件config.yml
    config = new SimpleYamlConfigManager(new File(this.getDataFolder(), "config.yml"), true);
    Map<String, String> map = config.getStringMap("path"); // 其中有一些便利的方法
    
    // 看我如何创建一个数据文件管理器，虽然它不从jar复制出默认文件，但它会定时保存
    data = new SimpleYamlConfigManager(new File(this.getDataFolder(), "data.yml"), false);
    data.setAutoSaveInterval(this, 60); // “this”是你的JavaPlugin，“60”是以秒为单位的保存间隔

locales
---
locales 框架让你轻松管理多语言环境

示例
  
    // 来创建一个语言环境为“zh_CN”的 locale manager ：
    locale = new SimpleLocaleManager("zh_CN", this.getDataFolder(), true); // “true”使其自动从jar中复制
                                                                           // /locales/zh_CN.yml 到 
                                                                           // this.getDataFolder()
    String localizedString = locale.getLocalizedString("msg1");
    
Utils
---
Crafter 还有些别的Util包:)



English
===
It contains following frameworks:

commands
---
The commands framework lets you:
  Create your commands without the need to worry about the checking of sender or permission
  Create your main command with subcommands quickly

e.g.
    
In class MySubCommand:
    
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


And in onEnable() method of your main class:
    
    SimpleMainCommand mainCommand = new SimpleMainCommand(this);
    mainCommand.addSubcommand(new MySubCommand());
    getCommand("mycmd").setExecutor(mainCommand);

Then it's done! When you type "/mycmd sub", the main command will automatically
get you the subcommand with the action "sub".

configs
---
The configs framework lets you create and deal with config file or data file easily

e.g.
    
    // The following line creates a config manager which gets the config.yml from your
    // jar automatically
    config = new SimpleYamlConfigManager(new File(this.getDataFolder(), "config.yml"), true);
    Map<String, String> map = config.getStringMap("path"); // Convenience methods are included
    
    // Now let's create a data manager which doesn't copy anything from your jar, but
    // saves regularly
    data = new SimpleYamlConfigManager(new File(this.getDataFolder(), "data.yml"), false);
    data.setAutoSaveInterval(this, 60); // where "this" is your JavaPlugin, and "60" is the 
                                        // interval in seconds

locales
---
The locales framework lets you easily get localized strings

e.g.
  
    // The following line creates a locale manager with locale "zh_CN"
    locale = new SimpleLocaleManager("zh_CN", this.getDataFolder(), true); // "true" tells it to copy a default
                                                                           // locale file from jar at
                                                                           // /locales/zh_CN.yml to the data folde
    String localizedString = locale.getLocalizedString("msg1");
    
Utils
---
It also has some util classes:)
