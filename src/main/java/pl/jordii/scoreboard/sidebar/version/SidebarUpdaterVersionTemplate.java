package pl.jordii.scoreboard.sidebar.version;

import org.bukkit.entity.Player;

public abstract class SidebarUpdaterVersionTemplate {

    private static SidebarUpdaterVersionTemplate instance;

    public static SidebarUpdaterVersionTemplate getInstance() {
        return instance;
    }

    public static void setInstance(SidebarUpdaterVersionTemplate instance) {
        SidebarUpdaterVersionTemplate.instance = instance;
    }

    /**
     * Updates the title of sidebar of the given player to the given string.
     * @param to      The title to update to.
     * @param player  The player whose sidebar's title should be updated.
     */
    public abstract void updateTitleOnly(String to, Player player);

}
