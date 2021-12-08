package core.input;

public interface InputProvider<T extends InputSchema> {
    T provideInput();
}
