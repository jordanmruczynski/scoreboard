package pl.jordii.scoreboard.listener;

import pl.jordii.scoreboard.sidebar.component.EmptyLineComponent;
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

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SimpleSidebar sidebar = SimpleSidebar.create();

        String locationText = "§7Location: %player_x% %player_y% %player_z%";
        String rankText = "&fRanga: %luckperms_prefix%";
        String skyblockOnline = "&fSkyBlock &7=> &6%bungee_skyblock%";
        String lobbyOnline = "&fLobby &7=> &6%bungee_lobby%";
        String totalOnline = "&fŁącznie &7=> &6%bungee_total%";
        String SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "-----------------";

        sidebar.title(() -> "§e§lLOBBY");
        sidebar.addComponent(EmptyLineComponent.create().line(99));
        sidebar.addComponent(StatelessTextComponent.create().line(98).text("§fWitaj, §a" + player.getName()));
        sidebar.addComponent(StatefulTextComponent.create().line(97).text(() ->
                PlaceholderAPI.setPlaceholders(player, rankText)));
        sidebar.addComponent(EmptyLineComponent.create().line(96));
        sidebar.addComponent(StatefulTextComponent.create().line(95).text(() ->
                PlaceholderAPI.setPlaceholders(player, skyblockOnline)));
        sidebar.addComponent(StatelessTextComponent.create().line(94).text("§fWkrótce.."));
        sidebar.addComponent(StatefulTextComponent.create().line(93).text(() ->
                PlaceholderAPI.setPlaceholders(player, totalOnline)));
        sidebar.addComponent(EmptyLineComponent.create().line(92));
        sidebar.addComponent(StatelessTextComponent.create().line(91).text("§ewww.betmc.pl"));
        sidebar.addComponent(StatelessTextComponent.create().line(90).text(SB_BAR));
//        sidebar.addComponent(StatefulTextComponent.create()
//                .line(96)
//                .text(() -> PlaceholderAPI.setPlaceholders(player, locationText)));

        sidebar.render(player);

        SidebarUpdater.addUpdater(player, sidebar);
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        SidebarUpdater.removeUpdater(event.getPlayer());
    }

}
