package config;

import core.config.Config;
import core.config.ConfigController;
import core.presenters.debug.Terminal;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConfigController {

    /**
     * returns the string outputted when the specified input is typed into a Terminal
     */
    private String getTerminalOutput(String input) {
        return getTerminalOutput(input, terminal -> {});
    }

    /**
     * returns the string outputted when the specified input is typed into a Terminal
     * the terminalConfigCallback is invoked before starting the Terminal thread
     */
    private String getTerminalOutput(String input, Consumer<Terminal> terminalConfigCallback) {
        // Setup Terminal
        InputStream in = new ByteArrayInputStream((input + "\n").getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        Terminal terminal = new Terminal(in, out, false);
        terminalConfigCallback.accept(terminal);

        try {
            // Start Terminal Thread and wait for it to close before returning output
            Thread thread = new Thread(terminal);
            thread.start();
            thread.join();
            return outputStream.toString().trim();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTerminal() {
        Assertions.assertEquals("test", getTerminalOutput("echo test"));
        Assertions.assertEquals("test", getTerminalOutput(" echo   test  \n"));
    }

    @Test
    public void testUseTerminal() {
        getTerminalOutput("", (ConfigController::useTerminal));
    }

    @Test
    public void testCfgCommand() {
        String name = "name";
        int initialValue = 3;
        int newValue = 5;

        Config.add(Integer.class, name, "desc", initialValue, Integer::parseInt);

        Assertions.assertEquals(
            name + ": " + initialValue,
            getTerminalOutput("cfg -get name", (ConfigController::useTerminal)),
            "Get option works"
        );

        Assertions.assertEquals(
            name + ": " + newValue,
            getTerminalOutput("cfg -set name " + newValue, (ConfigController::useTerminal)),
            "Set option works"
        );
    }
}
