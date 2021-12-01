package config;

import core.config.Config;
import core.config.ConfigurableSetting;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TestConfig {

    @Test
    public void testInternallyStoredSetting() {
        String name = UUID.randomUUID().toString();
        String desc = UUID.randomUUID().toString();
        int initialValue = (int) ((Math.random() - 0.5) * 400);
        int newValue;

        ConfigurableSetting<Integer> setting = Config.add(Integer.class, name, desc, initialValue, Integer::parseInt);

        Assertions.assertEquals(setting.get(), initialValue, "Can set initial value.");
        Assertions.assertEquals(Config.get(name), setting.get(), "Can get value via Config.");

        Assertions.assertEquals(setting.getDesc(), desc, "Can set description.");
        Assertions.assertEquals(Config.getDesc(name), setting.getDesc(), "Can get description via Config.");

        newValue = (int) (Math.random() * 400);
        setting.set(newValue);
        Assertions.assertEquals(Config.get(name), newValue, "Can set value directly via setting object.");

        newValue = (int) (Math.random() * -400);
        Config.set(name, Integer.toString(newValue));
        Assertions.assertEquals(Config.get(name), newValue, "Can set value from string via Config.");
    }

    @Test
    public void testExternallyStoredSetting() {
        String name = UUID.randomUUID().toString();
        String desc = UUID.randomUUID().toString();
        AtomicReference<Integer> settingValue = new AtomicReference<>(0);
        int initialValue = 1;
        int newValue;

        ConfigurableSetting<Integer> setting = Config.add(
            Integer.class,
            name,
            desc,
            initialValue,
            settingValue::get,
            settingValue::set,
            Integer::parseInt
        );

        Assertions.assertEquals(setting.get(), initialValue, "Can set initial value.");
        Assertions.assertEquals(Config.get(name), setting.get(), "Can get value via Config.");

        Assertions.assertEquals(setting.getDesc(), desc, "Can set description.");
        Assertions.assertEquals(Config.getDesc(name), setting.getDesc(), "Can get description via Config.");

        newValue = (int) (Math.random() * 400);
        setting.set(newValue);
        Assertions.assertEquals(Config.get(name), newValue, "Can set value directly via setting object.");

        newValue = (int) (Math.random() * -400);
        Config.set(name, Integer.toString(newValue));
        Assertions.assertEquals(Config.get(name), newValue, "Can set value from string via Config.");
    }

    @Test
    public void testCantRegisterDuplicateSetting() {
        Config.add(Integer.class, "name", "desc", 2, Integer::parseInt);
        Executable registerDuplicateSetting = () -> Config.add(Integer.class, "name", "desc", 2, Integer::parseInt);
        Assertions.assertThrows(RuntimeException.class, registerDuplicateSetting, "Can't register settings with duplicate names.");
    }
}
