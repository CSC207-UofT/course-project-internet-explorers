package core.debug;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import org.apache.commons.cli.*;

/**
 * basic terminal i/o for debugging purposes
 */
public class Terminal implements Runnable {

    public record Command(String name, Options options, Consumer<CommandLine> handler) {}

    private final Map<String, Command> commands;
    private final CommandLineParser parser;
    private final Scanner inputScanner;
    private boolean shouldClose;

    public Terminal(InputStream inputStream) {
        this.commands = new HashMap<>();
        this.parser = new DefaultParser();
        this.inputScanner = new Scanner(inputStream);
        this.shouldClose = false;

        this.addQuitCommand();
    }

    public Terminal() {
        this(System.in);
    }

    @Override
    public void run() {
        System.out.println("\n\n  ~ debug terminal ~");
        while (!shouldClose) {
            System.out.print("> ");
            String cmdName = inputScanner.next();
            String args = inputScanner.nextLine();
            Command cmd = this.commands.get(cmdName);
            try {
                cmd.handler.accept(parser.parse(cmd.options, args.split(" ")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Bye :)");
    }

    private void addQuitCommand() {
        registerCommand(new Command("exit", new Options(), l -> shouldClose = true));
    }

    /**
     * add a Command that can be invoked from this Terminal
     * <p>
     * overwrites any registered command with the same name
     */
    public void registerCommand(Command command) {
        this.commands.put(command.name, command);
    }
}
