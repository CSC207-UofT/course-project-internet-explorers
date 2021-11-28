package core.config;

import java.util.HashMap;
import java.util.NoSuchElementException;

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

    private ConfigurableSetting<?> getSetting(String settingName) {
        ConfigurableSetting<?> setting = configurableSettings.get(settingName);
        if (setting == null) {
            throw new NoSuchElementException("Unrecognized setting: " + settingName);
        }
        return setting;
    }

    /**
     * convenience method to get setting value
     */
    public Object get(String settingName) {
        return getSetting(settingName).getValue();
    }

    public void set(String settingName, String valueString) {
        getSetting(settingName).setValueFromString(valueString);
    }
}
