package pl.jordii.scoreboard.listener;

import org.bukkit.Bukkit;
import pl.jordii.scoreboard.ScoreboardPlugin;
import pl.jordii.scoreboard.sidebar.component.StatefulTextComponent;
import pl.jordii.scoreboard.sidebar.component.StatelessTextComponent;
import pl.jordii.scoreboard.sidebar.type.SimpleSidebar;
import pl.jordii.scoreboard.sidebar.update.SidebarUpdater;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private final String SB_BAR = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------";
    private final String TITLE = "§b§lUHCMeetup  ";

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SimpleSidebar sidebar = SimpleSidebar.create();

        sidebar.title(() -> TITLE);
        sidebar.addComponent(StatelessTextComponent.create().line(14).text(""));
        sidebar.addComponent(StatefulTextComponent.create().line(13).text(() -> "§bNick: §f" + PlaceholderAPI.setPlaceholders(player, player.getName())));
        sidebar.addComponent(StatefulTextComponent.create().line(12).text(() -> "§bRanga: §f" + ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%"))));
        sidebar.addComponent(StatefulTextComponent.create().line(11).text(() -> "§bDywizja: §f" + ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, "%alonsoleagues_league_display%"))));
        sidebar.addComponent(StatelessTextComponent.create().line(10).text(""));
        sidebar.addComponent(StatefulTextComponent.create().line(9).text(() -> "§bZabojstwa: §f" + PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_kills%")));
        sidebar.addComponent(StatefulTextComponent.create().line(8).text(() -> "§bSmierci: §f" + PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_deaths%")));
        sidebar.addComponent(StatefulTextComponent.create().line(7).text(() -> "§bK/D: §f" + PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_kd%")));
        sidebar.addComponent(StatefulTextComponent.create().line(6).text(() -> "§bMonety: §f" + PlaceholderAPI.setPlaceholders(player, "%mceconomy_ecoWezHajs%")));
        sidebar.addComponent(StatelessTextComponent.create().line(5).text(""));
        sidebar.addComponent(StatefulTextComponent.create().line(4).text(() -> "§bWygrane gry: §f" + PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_wins%")));
        sidebar.addComponent(StatefulTextComponent.create().line(3).text(() -> "§bRozegrane gry: §f" + PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_games%")));
        sidebar.addComponent(StatefulTextComponent.create().line(2).text(() -> PlaceholderAPI.setPlaceholders(player, "%mcrankingsystem_streakstatus%")));
        sidebar.addComponent(StatelessTextComponent.create().line(1).text(""));
        sidebar.addComponent(StatelessTextComponent.create().line(0).text("§ewww.mylobby.pl"));

        Bukkit.getScheduler().scheduleSyncDelayedTask(ScoreboardPlugin.getPlugin(ScoreboardPlugin.class), () -> {
            sidebar.render(player);
            SidebarUpdater.addUpdater(player, sidebar);
        }, 5L);
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        SidebarUpdater.removeUpdater(event.getPlayer());
    }
}
