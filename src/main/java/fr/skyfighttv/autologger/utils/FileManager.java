package fr.skyfighttv.autologger.utils;

import fr.skyfighttv.autologger.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class FileManager {
    private static HashMap<String, YamlConfiguration> values;
    private static HashMap<String, File> files;

    public FileManager() throws IOException {
        values = new HashMap<>();
        files = new HashMap<>();

        File file = new File(Main.getInstance().getDataFolder() + "/config.yml");
        if (!file.exists()) {
            InputStream fileStream = Main.getInstance().getResource("config.yml");
            byte[] buffer = new byte[fileStream.available()];
            fileStream.read(buffer);

            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
        }
        values.put("config", YamlConfiguration.loadConfiguration(file));

        System.out.println(Main.ANSI_GREEN + "Config initialized" + Main.ANSI_RESET);

        for (String events : values.get("config").getConfigurationSection("Modules").getKeys(false)) {
            if (values.get("config").getBoolean("Modules." + events)) {
                files.put(events, new File(Main.getInstance().folders.get(events)
                        + "/"
                        + values.get("config").getString(events + ".FileName")
                        .replaceAll("%date%", new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()))
                        .replaceAll("%filenumber%", Main.getInstance().fileNumber + "")
                        + ".txt"));
                files.get(events).createNewFile();
                System.out.println(Main.ANSI_GREEN + events + " created and initialized" + Main.ANSI_RESET);
            } else {
                System.out.println(Main.ANSI_RED + events + " was skipped" + Main.ANSI_RESET);
            }
        }
    }

    public static void writeInFile(File file, String text) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.sendDebug(file.getName() + " was modify");
    }

    /*public static void save(Files files) {
        File file = new File(Main.getInstance().getDataFolder() + "/" + files.name() + ".yml");
        if (!file.exists()) {
            System.out.println("ERROR : File " + files.name() + " not found !");
            return;
        }
        try {
            values.get(files.name()).save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put(files.name(), YamlConfiguration.loadConfiguration(file));
    }

    public static void reload(Files files) {
        File file = new File(Main.getInstance().getDataFolder() + "/" + files.name() + ".yml");

        if (!file.exists()) {
            System.out.println("ERROR : File " + files.name() + " not found !");
            return;
        }

        values.put(files.name(), YamlConfiguration.loadConfiguration(file));
    }*/

    public static HashMap<String, YamlConfiguration> getValues() {
        return values;
    }

    public static HashMap<String, File> getFiles() {
        return files;
    }
}
