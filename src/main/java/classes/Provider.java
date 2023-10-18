package classes;

public interface Provider<T, K> {
    T provide(K value);
}
