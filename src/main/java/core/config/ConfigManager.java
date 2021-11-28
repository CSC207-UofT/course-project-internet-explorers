package core.config;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Use-case class for `ConfigurableSetting`'s.
 */
public class ConfigManager {

    protected final HashMap<String, ConfigurableSetting<?>> configurableSettings;

    public ConfigManager() {
        configurableSettings = new HashMap<>();
    }

    public void registerSetting(ConfigurableSetting<?>... settings) {
        for (ConfigurableSetting<?> setting : settings) {
            String name = setting.getName();
            if (this.configurableSettings.containsKey(name)) {
                throw new RuntimeException("Cannot register settings with conflicting key name: " + name + ".");
            }
            this.configurableSettings.put(name, setting);
        }
    }

    /**
     * Get a ConfigurableSetting.
     */
    private ConfigurableSetting<?> getSetting(String settingName) {
        ConfigurableSetting<?> setting = configurableSettings.get(settingName);
        if (setting == null) {
            throw new NoSuchElementException("Unrecognized setting: " + settingName);
        }
        return setting;
    }

    /**
     * Get value of a setting.
     */
    public Object get(String settingName) {
        return getSetting(settingName).getValue();
    }

    /**
     * Set the value of a setting using a String.
     */
    public void set(String settingName, String valueString) {
        getSetting(settingName).setValueFromString(valueString);
    }
}
