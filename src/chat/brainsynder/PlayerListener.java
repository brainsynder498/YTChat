package chat.brainsynder;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private Core core;

    public PlayerListener (Core core) {
        this.core = core;
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat (AsyncPlayerChatEvent event) {
        if (core.members.contains(event.getPlayer().getUniqueId().toString())) {
            String format = core.getConfiguration().getString("messageFormat", true)
                    .replace("{message}", event.getMessage())
                    .replace("{displayName}", event.getPlayer().getDisplayName())
                    .replace("{name}", event.getPlayer().getName());

            event.setCancelled(true);

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission(core.getConfiguration().getString("permission", false))) {
                    player.sendMessage(format);
                }
            });
        }
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent event) {
        if (core.members.contains(event.getPlayer().getUniqueId().toString())) {
            core.members.remove(event.getPlayer().getUniqueId().toString());
        }
    }

    @EventHandler
    public void onLeave (PlayerKickEvent event) {
        if (core.members.contains(event.getPlayer().getUniqueId().toString())) {
            core.members.remove(event.getPlayer().getUniqueId().toString());
        }
    }
}
