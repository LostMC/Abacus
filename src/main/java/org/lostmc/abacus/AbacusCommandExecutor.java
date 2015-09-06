package org.lostmc.abacus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utility.NumberUtilities;

public class AbacusCommandExecutor implements CommandExecutor {
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (command.getName().equalsIgnoreCase("abacus")) {
            // Make sure we have some arguments.
            if (arguments.length > 0) {
                // If the first argument is one of 'material/materia/materi/.../mat'
                // then it is the material calculator call.
                if (arguments[0].toLowerCase().startsWith("mat")) {
                    if (arguments.length == 3) {
                        if (arguments[2].contains("wood") && arguments[2].contains("stair")) {
                            sender.sendMessage(ChatColor.GREEN + computeMaterials(arguments[1], 134));
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /abacus material <quantity> <material>");
                    }
                } else {
                    try {
                        MultiExpression multiExpression = new MultiExpression(arguments);
                        sender.sendMessage(ChatColor.GREEN + "Result: " + multiExpression.getResult());
                    } catch (MathException exception) {
                        sender.sendMessage(ChatColor.RED + exception.getMessage());
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Provide an expression to evaluate.");
            }
        }
        return true;
    }

    private String computeMaterials(String sQuantity, int type) {
        long quantity;
        try {
            quantity = Long.parseLong(sQuantity);
        } catch (NumberFormatException e) {
            return ChatColor.RED + "'" + sQuantity + "' is not a number.";
        }

        if (quantity < 1) {
            return ChatColor.RED + "'" + sQuantity + "' is an invalid amount.";
        }

        switch (type) {
            case 134:
            case 135:
            case 136:
                long batches = NumberUtilities.divideRoundUpPositive(quantity, 4);
                long planks = batches * 6;
                long logs = NumberUtilities.divideRoundUpPositive(planks, 4);
                return quantity + " wooden "
                        + ((quantity == 1) ? " stair " : " stairs") + " requires "
                        + batches + ((batches == 1) ? " batch" : " batches")
                        + " or " + planks + ((planks == 1) ? " plank" : " planks")
                        + " or " + logs + ((logs == 1) ? " log" : " logs") + ".";
            default:
                return "";
        }
    }
}
