package org.lostmc.abacus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserMessenger {
    private final CommandSender sender;

    public UserMessenger(CommandSender sender) {
        this.sender = sender;
    }

    public void sendSuccess(String message) {
        sender.sendMessage(ChatColor.GREEN + message);
    }

    public void sendFailure(String message) {
        sender.sendMessage(ChatColor.RED + message);
    }
}
