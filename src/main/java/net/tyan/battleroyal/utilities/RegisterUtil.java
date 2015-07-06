package net.tyan.battleroyal.utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * by Kevin on 14.05.2015.
 */

/* A simple class for register listeners and commands without using the plugin.yml */

public class RegisterUtil<P extends Plugin> {

    private P plugin;

    private static String VERSION;

    static {
        String path = Bukkit.getServer().getClass().getPackage().getName();
        VERSION = path.substring(path.lastIndexOf(".") + 1, path.length());
    }


    public RegisterUtil(P plugin) {
        this.plugin = plugin;
    }

    public void registerEvents(Class<?>... listeners) {
        for (Class<?> clazz : listeners) {
            boolean isConstructor = false;
            try {
                clazz.getConstructor(new Class[]{plugin.getClass()});
                isConstructor = true;
            } catch (NoSuchMethodException ex) {
                isConstructor = false;
            }

            try {
                Listener listener = null;
                if (isConstructor) {
                    Constructor<?> cww = clazz.getConstructor(new Class[] { plugin.getClass() });
                    listener = (Listener) cww.newInstance(new Object[] { plugin });
                } else {
                    listener = (Listener) clazz.newInstance();
                }
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void registerCommand(String name, String description, CommandExecutor commandExecutor, String permission, String... aliases) {
        try {
            DynCommand dynCommand = new DynCommand(name, description, commandExecutor, permission, aliases);
            Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".CraftServer");

            Field field = craftServerClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(plugin.getName(), dynCommand);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private class DynCommand extends Command {

        private CommandExecutor executor;

        protected DynCommand(String name, String description, CommandExecutor executor, String permission, String... aliases) {
            super(name);
            this.executor = executor;
            super.setDescription(description);
            super.setAliases(Arrays.asList(aliases));
            super.setPermission(permission);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            executor.onCommand(sender, this, label, args);
            return false;
        }
    }
}
