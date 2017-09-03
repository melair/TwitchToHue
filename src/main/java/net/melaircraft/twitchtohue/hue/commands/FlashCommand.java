package net.melaircraft.twitchtohue.hue.commands;

import net.melaircraft.twitchtohue.hue.BulbData;

public class FlashCommand implements Command {
    private final BulbData bulbData;

    public FlashCommand(BulbData bulbData) {
        this.bulbData = bulbData;
    }

    public BulbData getBulbData() {
        return bulbData;
    }
}
