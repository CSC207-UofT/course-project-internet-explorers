package core.config;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Entity class which represents a setting in the game, including its
 *      * type
 *      * description
 *      * value
 *
 * ConfigurableSettings are registered with the global ConfigManager upon instantiation,
 * such that settings are usable wherever needed. For example, in presenter classes.
 */
public class ConfigurableSetting<T> {

    public final Class<T> type;
    private T value;
    private final String name;
    private final String desc;
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    /**
     * Function to parse strings into a setting value of type T.
     * TODO possibly make it possible to throw ParseException for invalid strings.
     */
    private final Function<String, T> stringParser;

    /**
     * Initialize setting using getters and setters; storing value is left to client code.
     */
    public ConfigurableSetting(
        Class<T> type,
        String name,
        String desc,
        Supplier<T> getter,
        Consumer<T> setter,
        Function<String, T> stringParser
    ) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.getter = getter;
        this.setter = setter;
        this.stringParser = stringParser;

        Config.getManager().registerSetting(this);
    }

    /**
     * Initialize setting with value stored internally. Initially, setting value is `null`.
     */
    public ConfigurableSetting(Class<T> type, String name, String desc, Function<String, T> stringParser) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.getter = () -> this.value;
        this.setter = newValue -> this.value = newValue;
        this.stringParser = stringParser;

        Config.getManager().registerSetting(this);
    }

    /**
     * Initialize setting with specified initial value, stored internally.
     */
    public ConfigurableSetting(Class<T> type, String name, String desc, T initialValue, Function<String, T> stringParser) {
        this(type, name, desc, stringParser);
        setValue(initialValue);
    }

    public T getValue() {
        return getter.get();
    }

    public void setValue(T value) {
        setter.accept(value);
    }

    public void setValueFromString(String valueString) {
        this.setValue(stringParser.apply(valueString));
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
