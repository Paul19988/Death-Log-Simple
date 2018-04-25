package uk.co.paulcodes.dls;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by paulb on 25/04/2018.
 */
public class Core extends JavaPlugin {

    public static String prefix;
    public static String nopermission;
    public static int lastDeathNo = 0;

    public static boolean logg = false;

    public static File deathsFile;
    public static FileConfiguration deathsFC;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getCommand("dls").setExecutor(new DLSCommand());
        if(getConfig().getString("prefix") != null) {
            prefix = getConfig().getString("prefix");
            nopermission = getConfig().getString("nopermission");
            lastDeathNo = getConfig().getInt("lastdeathno");
        }else{
            getConfig().set("prefix", "&aDeathLogS &8> &e");
            getConfig().set("nopermission", "&cYou do not have the required permissions for this command.");
            getConfig().set("lastdeathno", 0);
            saveConfig();
            reloadConfig();
            if(getConfig().getString("prefix") != null) {
                prefix = getConfig().getString("prefix");
                nopermission = getConfig().getString("nopermission");
                lastDeathNo = getConfig().getInt("lastdeathno");
            }else{
                System.out.println("Config failed to generate please fix file permissions.");
            }
        }
        deathsFile = new File(getDataFolder() + "/deaths.yml");
        if(deathsFile.exists()) {
            deathsFC = YamlConfiguration.loadConfiguration(deathsFile);
        }else{
            try {
                deathsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(deathsFile.exists()) {
                deathsFC = YamlConfiguration.loadConfiguration(deathsFile);
            }
        }
    }

    @Override
    public void onDisable() {
        getConfig().set("lastdeathno", lastDeathNo);
        saveConfig();
    }

    public static Core getInstance() {
        return Core.getPlugin(Core.class);
    }

    static String timeStamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Calendar.getInstance().getTime());

    public static void logDeath(Player p, Player k) {
        Core.deathsFC.createSection(lastDeathNo + "");
        Core.deathsFC.getConfigurationSection(lastDeathNo + "").set("name", p.getName());
        Core.deathsFC.getConfigurationSection(lastDeathNo + "").set("uuid", p.getUniqueId().toString());
        Core.deathsFC.getConfigurationSection(lastDeathNo + "").set("killername", k.getName());
        Core.deathsFC.getConfigurationSection(lastDeathNo + "").set("killeruuid", k.getUniqueId().toString());
        Core.deathsFC.getConfigurationSection(lastDeathNo + "").set("time", timeStamp);
        try {
            deathsFC.save(deathsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lastDeathNo++;
    }
}
