package pl.jordii.scoreboard.sidebar.update;

import com.google.common.collect.Maps;
import pl.jordii.scoreboard.scheduler.type.RepeatingScheduler;
import pl.jordii.scoreboard.sidebar.type.KelpSidebar;
import pl.jordii.scoreboard.sidebar.type.SimpleSidebar;
import org.bukkit.entity.Player;

import java.util.Map;

public class SidebarUpdater {

    private static final Map<Player, KelpSidebar<?>> sidebars = Maps.newHashMap();

    public static void startUpdater() {
        RepeatingScheduler.create().async().every(100).milliseconds().run(taskId -> {
            sidebars.forEach((player, kelpSidebar) -> {
                if (kelpSidebar instanceof SimpleSidebar) {
                    SimpleSidebar simpleSidebar = (SimpleSidebar) kelpSidebar;
                    simpleSidebar.lazyUpdate(player);
                    return;
                }
                kelpSidebar.update(player);
            });
        });
    }

    public static void addUpdater(Player player, KelpSidebar<?> sidebar) {
        sidebars.put(player, sidebar);
    }

    public static void removeUpdater(Player player) {
        sidebars.remove(player);
    }

}
