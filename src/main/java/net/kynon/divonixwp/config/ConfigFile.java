package net.kynon.divonixwp.config;

import net.kynon.divonixwp.Main;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigFile {

    public static void loadConfig() {
        String filename = "wpconfig.yml";
        ClassLoader classLoader = Main.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            File file = new File("plugins/DWP/" + filename);
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter("plugins/DWP/" + filename));
                bw.write(result);
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
