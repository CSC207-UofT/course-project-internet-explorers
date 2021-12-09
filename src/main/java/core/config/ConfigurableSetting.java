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

    // for extension – display, etc
    @SuppressWarnings("unused")
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
    protected ConfigurableSetting(
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
    }

    /**
     * Initialize setting with value stored internally. Initially, setting value is `null`.
     */
    protected ConfigurableSetting(Class<T> type, String name, String desc, Function<String, T> stringParser) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.getter = () -> this.value;
        this.setter = newValue -> this.value = newValue;
        this.stringParser = stringParser;
    }

    public T get() {
        return getter.get();
    }

    public void set(T value) {
        setter.accept(value);
    }

    public void parse(String valueString) {
        this.set(stringParser.apply(valueString));
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
