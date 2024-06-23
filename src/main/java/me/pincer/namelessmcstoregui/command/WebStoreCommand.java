package me.pincer.namelessmcstoregui.command;

import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import me.pincer.namelessmcstoregui.menu.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WebStoreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("namelessmcstoregui.admin")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
                    return true;
                }
                NamelessMCStoreGUI.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Reloaded store configuration.");
                return true;
            }

        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED  + "Command may only be executed by a player!");
            return true;
        }

        Player player  = (Player) sender;
        MainMenu.openStore(player);

        return true;
    }
}
