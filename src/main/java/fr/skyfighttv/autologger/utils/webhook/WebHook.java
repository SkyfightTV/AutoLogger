package fr.skyfighttv.autologger.utils.webhook;

import fr.skyfighttv.autologger.Main;
import net.ranktw.DiscordWebHooks.DiscordMessage;
import net.ranktw.DiscordWebHooks.DiscordWebhook;
import net.ranktw.DiscordWebHooks.connection.WebhookException;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class WebHook {
    private DiscordWebhook hook;
    private DiscordMessage.Builder dM;
    private final List<String> text;

    public WebHook(String url, String event) {
        this.text = new ArrayList<>();
        try {
            this.hook = new DiscordWebhook(url);
            this.dM = new DiscordMessage.Builder()
                    .withUsername("AutoLogger")
                    .withAvatarURL("https://www.spigotmc.org/data/resource_icons/87/87495.jpg?1611009822");
        } catch (Exception e) {
            System.out.println(Main.ANSI_RED + "ERROR WEBHOOK : " + event + " can't find webhook" + Main.ANSI_RESET);
            Main.webhookClients.remove(event.toLowerCase());
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!text.isEmpty()) {
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i <= Math.min(text.size(), 15); i++) {
                        str.append(text.get(0)).append("\n");
                        text.remove(0);
                    }
                    DiscordMessage dm = dM.build();
                    dm.setContent(str.substring(0, str.length() -1));
                    try {
                        hook.sendMessage(dm);
                    } catch (WebhookException e) {
                        System.out.println(Main.ANSI_RED + "ERROR WEBHOOK : " + event + " can't send message because is too fast" + Main.ANSI_RESET);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 10, 10);
    }

    public void addText(String text) {
        this.text.add(text);
    }
}
