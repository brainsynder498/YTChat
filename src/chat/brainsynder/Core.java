package chat.brainsynder;

import chat.brainsynder.command.Command;
import chat.brainsynder.command.CommandListener;
import chat.brainsynder.command.CommandManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Core extends JavaPlugin implements CommandListener {
    public List<String> members = null;
    private FileMaker config = null;

    @Override
    public void onEnable() {
        config = new FileMaker(this, "config.yml",
                "Just a notice any value you change in here",
                "will automatically update in on its own",
                "so no need for reloading the plugin when changing values."
        );
        config.loadDefaults();
        members = new ArrayList<>();
        CommandManager.register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        config = null;
        members.clear();
        members = null;
    }

    @Command(name = "ytchat")
    public void onCommand (Player player, String[] args) {
        if (!player.hasPermission(config.getString("permission", false))) {
            player.sendMessage(config.getString("noPermission", true));
            return;
        }
        String uuid = player.getUniqueId().toString();
        if (members.contains(uuid)) {
            members.remove(uuid);
            player.sendMessage(config.getString("toggledOff", true));
        }else{
            members.add(uuid);
            player.sendMessage(config.getString("toggledOn", true));
        }
    }

    public FileMaker getConfiguration() {
        return config;
    }
}
