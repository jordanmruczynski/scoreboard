package pl.jordii.scoreboard.v_1_8;

import pl.jordii.scoreboard.sidebar.SidebarUtils;
import pl.jordii.scoreboard.sidebar.component.SidebarComponent;
import pl.jordii.scoreboard.sidebar.type.Sidebar;
import pl.jordii.scoreboard.sidebar.type.SimpleSidebar;
import pl.jordii.scoreboard.sidebar.version.SidebarVersionTemplate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class VersionedSidebar extends SidebarVersionTemplate {

    /**
     * Renders the sidebar to a specific player. This means it displays
     * it for the first time (so only use this method if the player does
     * not already see this sidebar to avoid flicker effects or similar behaviour).
     *
     * @param sidebar     The sidebar to render to the given player.
     */
    @Override
    public void renderSidebar(Sidebar<?> sidebar, Player player) {
        Scoreboard scoreboard;
        Objective objective;

        scoreboard = player.getScoreboard();
        boolean hasScoreboard = scoreboard.getObjective(DisplaySlot.SIDEBAR) != null
                || scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null
                || scoreboard.getObjective(DisplaySlot.PLAYER_LIST) != null;

        if (hasScoreboard) {
            scoreboard = player.getScoreboard();
        } else {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
            objective = scoreboard.registerNewObjective("kelpObj", "dummy");
        } else {
            objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);

        // apply all contents to the sidebar.
        this.updateSidebar(sidebar, player);

//        if (sidebar.getClass().isAssignableFrom(AnimatedSidebar.class)) {
//            // if the sidebar is animated start the animation schedulers in
//            // the sidebar repository and set the first animation state as default.
//            AnimatedSidebar animatedSidebar = (AnimatedSidebar) sidebar;
//            objective.setDisplayName(animatedSidebar.getTitle().states().get(0));
//            sidebarRepository.addAnimatedSidebar(animatedSidebar, kelpPlayer);
        if (sidebar.getClass().isAssignableFrom(SimpleSidebar.class)) {
            // if the sidebar is a simple sidebar, simply set the default title.
            SimpleSidebar simpleSidebar = (SimpleSidebar) sidebar;
            objective.setDisplayName(simpleSidebar.getTitle().get());
        }

        // set internally

    }

    /**
     * Performs a lazy update on the sidebar. A lazy update does not remove all entries/lines
     * existing entries in the sidebar, which means that you cannot use it if you know that the amount
     * of lines in the sidebar might change with an update.
     *
     * However this update method is completely free from any flickering effects and it is
     * not as performance heavy as a normal update. So if you can, you should prefer this update
     * method over a normal update.
     *  @param sidebar     The sidebar to update.
     * @param player  The player who should see the updated sidebar.
     */
    @Override
    public void lazyUpdate(Sidebar<?> sidebar, Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        for (Object object : sidebar.getComponents()) {
            if (!(object instanceof SidebarComponent)) {
                continue;
            }

            SidebarComponent component = (SidebarComponent) object;

            component.render().forEach((line, text) -> {
                String teamName = "entry_" + line;
                Team team = scoreboard.getTeam(teamName);

                SidebarUtils.setTeamData(text, team);
            });
        }
    }

    /**
     * Performs a full-update on the given sidebar, which means that all existing
     * contents are removed and then new contents are applied. This method is safe
     * against changing amounts of lines.
     *
     * @param sidebar       The sidebar you want to update.
     * @param player    The player who should see the updates.
     */
    @Override
    public void updateSidebar(Sidebar<?> sidebar, Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        if (objective == null) {
            renderSidebar(sidebar, player);
            return;
        }

        // remove all old entries
        for (String entry : scoreboard.getEntries()) {
            Score score = objective.getScore(entry);

            if (score == null) {
                continue;
            }

            scoreboard.resetScores(entry);
        }

        // unregister all teams
        scoreboard.getTeams().forEach(Team::unregister);

        for (Object object : sidebar.getComponents()) {
            if (!(object instanceof SidebarComponent)) {
                continue;
            }

            SidebarComponent component = (SidebarComponent) object;

            component.render().forEach((line, text) -> {
                String entry = SidebarUtils.randomEmptyEntry(scoreboard);
                objective.getScore(entry).setScore(line);
                Team team = scoreboard.registerNewTeam("entry_" + line);
                team.addEntry(entry);

                SidebarUtils.setTeamData(text, team);
            });

        }
    }

}
