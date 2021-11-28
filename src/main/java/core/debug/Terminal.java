package core.debug;

import com.badlogic.gdx.Gdx;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import org.apache.commons.cli.*;

/**
 * basic terminal i/o for debugging purposes
 */
public class Terminal implements Runnable {

    // error-proof callback https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception
    @FunctionalInterface
    public interface CommandCallback<T> extends Consumer<T> {
        @Override
        default void accept(final T param) {
            try {
                acceptThrows(param);
            } catch (final Exception e) {
                System.err.println(e.getMessage());
            }
        }

        void acceptThrows(T elem) throws Exception;
    }

    public record Command(String name, Options options, CommandCallback<CommandLine> handler) {}

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
        this.addEchoCommand();
    }

    public Terminal() {
        this(System.in);
    }

    @Override
    public void run() {
        System.out.print("\n\n  ~ debug terminal ~");
        do {
            System.out.print("\n> ");
            try {
                if (inputScanner.hasNext() && inputScanner.hasNextLine()) {
                    String verb = inputScanner.next();
                    String[] args = inputScanner.nextLine().trim().split(" ");
                    Command cmd = commands.get(verb);
                    if (cmd == null) {
                        System.out.println(verb + " is not the name of a command.");
                    } else {
                        cmd.handler.accept(parser.parse(cmd.options, args));
                    }
                }
            } catch (ParseException | NoSuchElementException e) {
                System.err.println(e.getMessage());
            }
        } while (!shouldClose);
        System.out.println("Bye :)");
        Gdx.app.exit();
    }

    private void addQuitCommand() {
        registerCommand(new Command("exit", new Options(), l -> shouldClose = true));
    }

    private void addEchoCommand() {
        registerCommand(new Command("echo", new Options(), l -> System.out.println(String.join(" ", l.getArgs()))));
    }

    public static void main(String[] args) {
        new Thread(new Terminal()).start();
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
