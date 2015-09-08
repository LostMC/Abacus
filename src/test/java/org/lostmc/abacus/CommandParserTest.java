package org.lostmc.abacus;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.ChatColor;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CommandParserTest {
    private final CommandParser parser = new CommandParser();
    private final MockCommandSender sender = new MockCommandSender();
    private final UserMessenger messenger = new UserMessenger(sender);

    @Test
    public void returnTrueWhenValid() {
        parser.parse(messenger, "abacus", "1+1");
        assertThat(sender.getMessage(), not(equalTo("")));
    }

    @Test
    public void returnTrueWhenValidDespiteCase() {
        parser.parse(messenger, "aBaCus", "1+1");
        assertThat(sender.getMessage(), not(equalTo("")));
        parser.parse(messenger, "ABACUS", "1+1");
        assertThat(sender.getMessage(), not(equalTo("")));
        parser.parse(messenger, "Abacus", "1+1");
        assertThat(sender.getMessage(), not(equalTo("")));
    }

    @Test
    public void noMessagesForNullCommand() {
        parser.parse(messenger, null, "1+1");
        assertThat(sender.getMessage(), equalTo(""));
    }

    @Test
    public void noMessagesForEmptyCommand() {
        parser.parse(messenger, "", "1+1");
        assertThat(sender.getMessage(), equalTo(""));
    }

    @Test
    public void noMessagesForBlankCommand() {
        parser.parse(messenger, "    \t", "1+1");
        assertThat(sender.getMessage(), equalTo(""));
    }

    @Test
    public void noMessagesForInvalidCommand() {
        parser.parse(messenger, RandomStringUtils.randomAlphabetic(25), "1+1");
        assertThat(sender.getMessage(), equalTo(""));
    }

    @Test
    public void sendMessageForInsufficientArguments() {
        parser.parse(messenger, "abacus");

        assertThat(sender.getMessage(), equalTo(ChatColor.RED + "Provide an expression to evaluate."));
    }

    @Test
    public void evaluateExpression() {
        parser.parse(messenger, "abacus", "1+1");

        assertThat(sender.getMessage(), equalTo(ChatColor.GREEN + "Result: 2"));
    }

    @Test
    public void sendMessageForBadExpression() {
        parser.parse(messenger, "abacus", "+++++------");

        assertThat(sender.getMessage(), equalTo(ChatColor.RED + "Insufficient operands. Check your formula."));
    }

    @Test
    public void parseMaterialNeedsAQuantityAndMaterialName() {
        parser.parse(messenger, "abacus", "material");

        assertThat(sender.getMessage(), equalTo(ChatColor.RED + "Usage: /abacus material <quantity> <material>"));
    }

    @Test
    public void parseMaterial() {
        parser.parse(messenger, "abacus", "material", "1", "woodenstair");

        assertThat(sender.getMessage(), equalTo(ChatColor.GREEN + "1 wooden  stair  requires 1 batch or 6 planks or 2 logs."));
    }
}