package me.pincer.namelessmcstoregui.command;

import me.pincer.namelessmcstoregui.menu.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WebStoreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED  + "Command may on be executed by a player!");
            return true;
        }

        Player player  = (Player) sender;
        Main.openStore(player);

        return true;
    }
}
