package org.lostmc.abacus;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AbacusCommandExecutorTest {
    @Test
    public void callCommandParserWithCommandName() {
        Command command = new TestCommand(RandomStringUtils.randomAlphabetic(13));
        CommandParser parser = mock(CommandParser.class);
        AbacusCommandExecutor executor = new AbacusCommandExecutor(parser);
        executor.onCommand(new MockCommandSender(), command, null);
        verify(parser).parse(isA(UserMessenger.class), eq(command.getName()));
    }

    @Test
    public void callCommandParserWithArguments() {
        String argument1 = RandomStringUtils.randomAlphabetic(16);
        String argument2 = RandomStringUtils.randomAlphabetic(16);
        String argument3 = RandomStringUtils.randomAlphabetic(16);
        Command command = new TestCommand(RandomStringUtils.randomAlphabetic(13));

        CommandParser parser = mock(CommandParser.class);
        AbacusCommandExecutor executor = new AbacusCommandExecutor(parser);
        executor.onCommand(new MockCommandSender(), command, null, argument1, argument2, argument3);
        verify(parser).parse(isA(UserMessenger.class), eq(command.getName()), eq(argument1), eq(argument2), eq(argument3));
    }

    private static class TestCommand extends Command {
        protected TestCommand(String name) {
            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            return false;
        }
    }
}