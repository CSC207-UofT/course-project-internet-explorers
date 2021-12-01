package core.config;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Use-case class for `ConfigurableSetting`'s.
 * Settings are stored globally for the whole app.
 */
public class Config {

    protected static final HashMap<String, ConfigurableSetting<?>> configurableSettings = new HashMap<>();

    /**
     * Creates a ConfigurableSetting whose value is stored externally.
     */
    public static <T> ConfigurableSetting<T> add(
        Class<T> type,
        String name,
        String desc,
        Supplier<T> getter,
        Consumer<T> setter,
        Function<String, T> stringParser
    ) {
        ConfigurableSetting<T> setting = new ConfigurableSetting<>(type, name, desc, getter, setter, stringParser);
        register(setting);
        return setting;
    }

    /**
     * Creates a ConfigurableSetting whose value is stored externally, with the specified initial value.
     */
    public static <T> ConfigurableSetting<T> add(
        Class<T> type,
        String name,
        String desc,
        T initialValue,
        Supplier<T> getter,
        Consumer<T> setter,
        Function<String, T> stringParser
    ) {
        ConfigurableSetting<T> setting = add(type, name, desc, getter, setter, stringParser);
        setting.set(initialValue);
        return setting;
    }

    /**
     * Creates a ConfigurableSetting whose value is stored internally.
     */
    public static <T> ConfigurableSetting<T> add(Class<T> type, String name, String desc, Function<String, T> stringParser) {
        ConfigurableSetting<T> setting = new ConfigurableSetting<>(type, name, desc, stringParser);
        register(setting);
        return setting;
    }

    /**
     * Creates a ConfigurableSetting whose value is stored internally, with the specified initial value.
     */
    public static <T> ConfigurableSetting<T> add(
        Class<T> type,
        String name,
        String desc,
        T initialValue,
        Function<String, T> stringParser
    ) {
        ConfigurableSetting<T> setting = add(type, name, desc, stringParser);
        setting.set(initialValue);
        return setting;
    }

    private static void register(ConfigurableSetting<?> setting) {
        String name = setting.getName();
        if (configurableSettings.containsKey(name)) {
            throw new RuntimeException("Cannot register settings with conflicting key name: " + name + ".");
        }
        configurableSettings.put(name, setting);
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
