package org.lostmc.abacus;

import org.bukkit.ChatColor;
import utility.NumberUtilities;

public class CommandParser {
    public void parse(UserMessenger messenger, String commandName, String... arguments) {
        if ("abacus".equalsIgnoreCase(commandName)) {
            if (arguments.length > 0) {
                if (arguments[0].startsWith("mat")) {
                    parseMaterial(messenger, arguments);
                } else {
                    try {
                        MultiExpression multiExpression = new MultiExpression(arguments);
                        messenger.sendSuccess("Result: " + multiExpression.getResult());
                    } catch (MathException exception) {
                        messenger.sendFailure(exception.getMessage());
                    }
                }
            } else {
                messenger.sendFailure("Provide an expression to evaluate.");
            }
        }
    }

    private void parseMaterial(UserMessenger messenger, String... arguments) {
        if (arguments.length < 3) {
            messenger.sendFailure("Usage: /abacus material <quantity> <material>");
        } else {
            if (arguments[2].contains("wood") && arguments[2].contains("stair")) {
                messenger.sendSuccess(computeMaterials(arguments[1], 134));
            }
        }
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
