package core.config;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Use-case class for `ConfigurableSetting`'s.
 * Settings are stored globally for the whole app.
 */
public class Config {

    protected static final HashMap<String, ConfigurableSetting<?>> configurableSettings = new HashMap<>();

    public static void registerSetting(ConfigurableSetting<?>... settings) {
        for (ConfigurableSetting<?> setting : settings) {
            String name = setting.getName();
            if (configurableSettings.containsKey(name)) {
                throw new RuntimeException("Cannot register settings with conflicting key name: " + name + ".");
            }
            configurableSettings.put(name, setting);
        }
    }

    /**
     * Get a ConfigurableSetting.
     */
    private static ConfigurableSetting<?> getSetting(String settingName) {
        ConfigurableSetting<?> setting = configurableSettings.get(settingName);
        if (setting == null) {
            throw new NoSuchElementException("Unrecognized setting: " + settingName);
        }
        return setting;
    }

    /**
     * Get the description of a setting.
     */
    public static String getDesc(String settingName) {
        return getSetting(settingName).getDesc();
    }

    /**
     * Get the value of a setting.
     */
    public static Object get(String settingName) {
        return getSetting(settingName).get();
    }

    /**
     * Set the value of a setting using a String.
     */
    public static void set(String settingName, String valueString) {
        getSetting(settingName).set(valueString);
    }
}
