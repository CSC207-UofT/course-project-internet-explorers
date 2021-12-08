package core.input;

public class InputMapping<T extends InputSchema> {

    private final InputProvider<T> provider;
    private final InputHandler<T> handler;

    public InputMapping(InputProvider<T> provider, InputHandler<T> handler) {
        this.provider = provider;
        this.handler = handler;
    }

    public void mapInput() {
        handler.handleInput(provider.provideInput());
    }
}
