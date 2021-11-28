package core.config;

import core.presenters.debug.Terminal;
import org.apache.commons.cli.*;

/**
 * Controller class for global app config.
 */
public class ConfigController {

    /**
     * Registers a command in the Terminal to read/write settings via the `configManager`.
     */
    public static void useTerminal(Terminal terminal) {
        Option set = Option.builder().option("set").desc("sets the value of a setting").hasArgs().numberOfArgs(2).build();
        Option get = Option.builder().option("get").desc("gets the value of a setting").hasArgs().numberOfArgs(1).build();

        Options options = new Options();
        options.addOption(set);
        options.addOption(get);

        terminal.registerCommand(
            new Terminal.Command(
                "cfg",
                options,
                line -> {
                    if (line.hasOption(set)) {
                        String[] args = line.getOptionValues(set);
                        String name = args[0];
                        String value = args[1];

                        Config.set(name, value);
                        System.out.printf("%s: %s", name, Config.get(name));
                    }

                    if (line.hasOption(get)) {
                        String name = line.getOptionValue(get);
                        System.out.printf("%s: %s", name, Config.get(name));
                    }
                }
            )
        );
    }
}
