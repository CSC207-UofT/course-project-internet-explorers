package core.config;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConfigurableSetting<T> {

    public final Class<T> type;
    private T value;
    private final String name;
    private final String desc;
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final Function<String, T> stringParser;

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

    public ConfigurableSetting(Class<T> type, String name, String desc, Function<String, T> stringParser) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.getter = () -> this.value;
        this.setter = newValue -> this.value = newValue;
        this.stringParser = stringParser;

        Config.getManager().registerSetting(this);
    }

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
