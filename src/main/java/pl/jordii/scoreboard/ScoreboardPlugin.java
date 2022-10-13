package pl.jordii.scoreboard;

import pl.jordii.scoreboard.listener.PlayerConnectionListener;
import pl.jordii.scoreboard.scheduler.SchedulerRepository;
import pl.jordii.scoreboard.sidebar.update.SidebarUpdater;
import pl.jordii.scoreboard.sidebar.version.SidebarUpdaterVersionTemplate;
import pl.jordii.scoreboard.sidebar.version.SidebarVersionTemplate;
import pl.jordii.scoreboard.v_1_8.VersionedSidebar;
import pl.jordii.scoreboard.v_1_8.VersionedSidebarUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ScoreboardPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // register all events where placeholder api is used
            Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        } else {
            getLogger().log(Level.WARNING, "Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        VersionedSidebar versionedSidebar = new VersionedSidebar();
        VersionedSidebarUpdater versionedSidebarUpdater = new VersionedSidebarUpdater();

        SidebarUpdaterVersionTemplate.setInstance(versionedSidebarUpdater);
        SidebarVersionTemplate.setInstance(versionedSidebar);

        SidebarUpdater.startUpdater();

    }


    @Override
    public void onDisable() {
        SchedulerRepository.getInstance().interruptAll();
    }

}
