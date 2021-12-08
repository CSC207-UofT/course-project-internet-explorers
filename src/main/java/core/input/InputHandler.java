package core.input;

public interface InputHandler<T extends InputSchema> {
    void handleInput(T input);
}
