# Crafter

An assistant for writing Bukkit Plugins.

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
