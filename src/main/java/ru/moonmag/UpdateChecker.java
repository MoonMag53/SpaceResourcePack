package ru.moonmag;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String repo = "MoonMag53/SpaceResourcePack";
    private final Logger logger;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/MoonMag53/SpaceResourcePack/releases/latest");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.addRequestProperty("User-Agent", "Mozilla/5.0");
                if (conn.getResponseCode() == 200) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject)parser.parse(new InputStreamReader(conn.getInputStream()));
                    String latestVersion = (String)json.get("tag_name");
                    String currentVersion = this.plugin.getDescription().getVersion();
                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        this.logger.warning("Доступна новая версия плагина: " + latestVersion + " (текущая: " + currentVersion + ")");
                        this.logger.warning("Скачать: https://github.com/MoonMag53/SpaceResourcePack/releases/tag/" + latestVersion);
                    } else {
                        this.logger.info("Вы используете последнюю версию плагина (" + currentVersion + ")");
                    }
                } else {
                    this.logger.warning("Не удалось проверить обновление. Код: " + conn.getResponseCode());
                }

                conn.disconnect();
            } catch (Exception var7) {
                Exception e = var7;
                this.logger.warning("Ошибка при проверке обновлений: " + e.getMessage());
            }

        });
    }
}
