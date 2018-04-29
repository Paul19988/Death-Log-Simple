package uk.co.paulcodes.dls;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by paulb on 25/04/2018.
 */
public class DLSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("dls")) {
            if(sender.hasPermission("dls.admin")) {
                if (args[0].equalsIgnoreCase("start")) {
                    Core.logg = true;
                    sender.sendMessage(Core.prefix + "You have started logging player deaths.");
                } else if (args[0].equalsIgnoreCase("end")) {
                    Core.logg = false;
                    sender.sendMessage(Core.prefix + "You have stopped logging player deaths.");
                } else if (args[0].equalsIgnoreCase("reset")) {
                    Core.logg = false;
                    Core.resetLog(sender);
                    sender.sendMessage(Core.prefix + "You have reset the log for player deaths.");
                }
            }else{
                sender.sendMessage(Core.prefix + Core.nopermission);
            }
        }
        return false;
    }

}
