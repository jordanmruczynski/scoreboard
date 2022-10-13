package pl.jordii.scoreboard.v_1_8;

import pl.jordii.scoreboard.sidebar.version.SidebarUpdaterVersionTemplate;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class VersionedSidebarUpdater extends SidebarUpdaterVersionTemplate {

    /**
     * Updates the title of sidebar of the given player to the given string.
     * @param to          The title to update to.
     * @param player  The player whose sidebar's title should be updated.
     */
    @Override
    public void updateTitleOnly(String to, Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        objective.setDisplayName(to);
    }

}
