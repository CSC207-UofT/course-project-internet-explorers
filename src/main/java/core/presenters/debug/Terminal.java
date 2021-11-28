package core.presenters.debug;

import com.badlogic.gdx.Gdx;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.BiConsumer;
import org.apache.commons.cli.*;

/**
 * basic terminal i/o for debugging purposes
 */
public class Terminal implements Runnable {

    // error-proof callback https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception
    @FunctionalInterface
    public interface CommandCallback<T, U> extends BiConsumer<T, U> {
        @Override
        default void accept(final T param1, final U param2) {
            try {
                acceptThrows(param1, param2);
            } catch (final Exception e) {
                System.err.println(e.getMessage());
            }
        }

        void acceptThrows(T param1, U param2) throws Exception;
    }

    /**
     * @param name Name used to identify and invoke the command.
     * @param options Options object specifying options.
     * @param handler Callback function to handle parsed input to the command.
     *                Output should be written to the provided OutputStream.
     */
    public record Command(String name, Options options, CommandCallback<CommandLine, PrintStream> handler) {}

    private final Map<String, Command> commands;
    private final CommandLineParser parser;
    private final Scanner inputScanner;
    private final PrintStream printStream;
    private boolean shouldClose;

    public Terminal(InputStream inputStream, PrintStream printStream) {
        this.commands = new HashMap<>();
        this.parser = new DefaultParser();
        this.inputScanner = new Scanner(inputStream);
        this.printStream = printStream;
        this.shouldClose = false;

        this.addQuitCommand();
        this.addEchoCommand();
    }

    public Terminal() {
        this(System.in, System.out);
    }

    @Override
    public void run() {
        System.out.print("\n\n  ~ debug terminal ~");
        do {
            System.out.print("\n> ");
            try {
                // wait for non-empty line of input
                if (inputScanner.hasNext() && inputScanner.hasNextLine()) {
                    // split input into command name and command arguments
                    String name = inputScanner.next();
                    String[] args = inputScanner.nextLine().trim().split(" ");

                    Command cmd = commands.get(name);
                    if (cmd == null) {
                        System.out.println(name + " is not the name of a command.");
                    } else {
                        cmd.handler.accept(parser.parse(cmd.options, args), printStream);
                    }
                }
            } catch (ParseException | NoSuchElementException e) {
                System.err.println(e.getMessage());
            }
        } while (!shouldClose);
    }

    private void addQuitCommand() {
        registerCommand(
            new Command(
                "exit",
                new Options(),
                (l, out) -> {
                    shouldClose = true;
                    out.println("Bye :)");
                    Gdx.app.exit();
                }
            )
        );
    }

    private void addEchoCommand() {
        registerCommand(new Command("echo", new Options(), (l, out) -> out.println(String.join(" ", l.getArgs()))));
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
