package net.tyan.battleroyal.scheduler;

import net.tyan.battleroyal.BattleRoyal;
import net.tyan.battleroyal.reference.Reference;
import net.tyan.battleroyal.utilities.MapTeleport;
import net.tyan.battleroyal.utilities.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * by Kevin on 24.06.2015.
 */

public class LobbyScheduler implements Runnable {

    private BattleRoyal instance;

    public int time;
    public int runID;

    public LobbyScheduler(BattleRoyal instance) {
        this.instance = instance;
        time = 241;
        runID = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, this, 20L, 20L);
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            time = 241;
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= 12) {
            if (time > 80)
                time = 80;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            TitleUtil.sendActionBar(player, "§3Die Runde startet in §6" + time + (time == 1 ? " Sekunde" : " Sekunden") + "§7.");
        }

        if (time == 240 || time == 210 || time == 180 || time == 150 || time == 120 || time == 90 || time == 60 || time == 30 || time == 15 || time == 10 || time == 5 || time < 5) {

            if (time < -1) return;

            Bukkit.broadcastMessage(Reference.PREFIX + "§7Die Runde startet in §6" + time + (time == 1 ? " Sekunde" : " Sekunden") + "§7." );

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, (time == 1 ? Note.Tone.C : Note.Tone.E), true));
            }

            if (Bukkit.getOnlinePlayers().size() < 4 && time < 60) {
                Bukkit.broadcastMessage(Reference.PREFIX + "§cDer Countdown wurde neu gestartet, da zu wenig Spieler online sind. (mind. 4 Spieler)");

                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 5f, 5f);

                time = 241;
            }
        }
        if (time == 0) {

            instance.getServer().getScheduler().cancelTask(runID);
            new MapTeleport();
        }

        time--;
    }
}
