package chat.brainsynder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileMaker {
    private File file;
    private FileConfiguration configuration;

    public FileMaker(Plugin plugin, String fileName, String... header) {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        boolean exists = true;
        if (!file.exists()) {
            exists = false;
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(file);
        if (!exists) {
            setHeader(header);
        }
    }

    public void loadDefaults () {
        if (!isSet("permission")) set("permission", "yt.chat");
        if (!isSet("noPermission")) set("noPermission", "&cOnly YouTuber SMP Members may access this!");
        if (!isSet("messageFormat")) set("messageFormat", "&7[&cYTChat&7] {displayName}&7: &f{message}");
        if (!isSet("toggledOn")) set("toggledOn", "&aYT chat has been turned on");
        if (!isSet("toggledOff")) set("toggledOff", "&cYT chat has been turned off");
    }

    public String getString(String tag, boolean color) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        return this.configuration.get(tag) != null ? (color ? this.translate(this.configuration.getString(tag)) : this.configuration.getString(tag)) : tag;
    }

    public String getString(String tag) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        return this.configuration.get(tag) != null ? this.configuration.getString(tag) : tag;
    }

    public boolean getBoolean(String tag) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        return this.configuration.get(tag) != null && this.configuration.getBoolean(tag);
    }

    public boolean isSet(String tag) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        return this.configuration.get(tag) != null;
    }

    private String translate(String msg) {
        return msg.replace("&", "§");
    }

    public void set(String tag, Object data) {
        configuration.set(tag, data);
        try {
            configuration.save(file);
        } catch (IOException ignored) {
        }
    }

    public void setHeader(String... header) {
        this.configuration.options().header(Arrays.toString(header));
        try {
            configuration.save(file);
        } catch (IOException ignored) {
        }
    }
}