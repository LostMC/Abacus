package org.lostmc.abacus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AbacusCommandExecutor implements CommandExecutor {
    private final CommandParser parser;

    public AbacusCommandExecutor(CommandParser parser) {
        this.parser = parser;
    }

    /**
     * The onCommand method is called by Bukkit during a command event.
     *
     * @param sender    CommandSender object referencing either the player or the
     *                  console.
     * @param command   Command object representing the command matched by the
     *                  label.
     * @param label     String containing the actual command typed.
     * @param arguments Array of Strings containing everything typed after the
     *                  command.
     * @return False if we want Bukkit to display the 'usage' message.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String... arguments) {
        parser.parse(new UserMessenger(sender), command.getName(), arguments);
        return true;
    }
}
