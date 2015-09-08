package org.lostmc.abacus;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.ChatColor;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserMessengerTest {
    private final MockCommandSender sender = new MockCommandSender();
    private final UserMessenger messenger = new UserMessenger(sender);

    @Test
    public void sendSuccess() {
        String message = RandomStringUtils.randomAlphabetic(25);
        messenger.sendSuccess(message);

        assertThat(sender.getMessage(), equalTo(ChatColor.GREEN + message));
    }

    @Test
    public void sendFailure() {
        String message = RandomStringUtils.randomAlphabetic(25);
        messenger.sendFailure(message);

        assertThat(sender.getMessage(), equalTo(ChatColor.RED + message));
    }
}