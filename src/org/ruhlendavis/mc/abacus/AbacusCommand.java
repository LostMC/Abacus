/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ruhlendavis.mc.abacus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Feaelin
 */
public class AbacusCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
	                         String[] arguments)
	{
		if (command.getName().equalsIgnoreCase("abacus"))
		{
			if (arguments.length > 0)
			{
				String expression = "";
				for (String argument : arguments)
				{
					expression = expression + argument;
				}
				
				Parser parser = new Parser(expression);
				sender.sendMessage(ChatColor.GREEN + "Result: " + parser.getResult());
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Provide an expression to evaluate.");
			}
		}
		return true;
	}	
}
