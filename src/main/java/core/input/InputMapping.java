package core.input;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InputMapping<T extends InputSchema> {

    private final Supplier<T> inputSupplier;
    private final Consumer<T> inputConsumer;

    public InputMapping(Supplier<T> inputSupplier, Consumer<T> inputConsumer) {
        this.inputSupplier = inputSupplier;
        this.inputConsumer = inputConsumer;
    }

    public void mapInput() {
        inputConsumer.accept(inputSupplier.get());
    }
}
