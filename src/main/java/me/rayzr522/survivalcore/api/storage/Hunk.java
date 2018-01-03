package me.rayzr522.survivalcore.api.storage;

import me.rayzr522.survivalcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Hunk {
    private final Map<String, Object> data;

    public Hunk(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * @return A hunk with no data.
     */
    public static Hunk empty() {
        return new Hunk(new HashMap<>());
    }

    /**
     * Gets the raw data map.
     *
     * @return The raw data map.
     */
    public Map<String, Object> getRaw() {
        return data;
    }

    /**
     * Checks to see if there is a value at the given key.
     *
     * @param key The key to check.
     * @return Whether or not a value is present at the given key.
     */
    public boolean hasKey(String key) {
        return get(key).isPresent();
    }

    /**
     * Gets the value with the given key, casting the raw object to the type parameter if it is present.
     *
     * @param key The key to retrieve.
     * @param <T> The target type.
     * @return An {@link Optional} containing the value.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key) {
        return get(key, val -> (T) val);
    }

    /**
     * Gets the value with the given key, using the given mapper to convert to the type parameter if it is present.
     *
     * @param key    The key to retrieve.
     * @param mapper The mapper to use to convert from a raw (non-null) object to the type parameter.
     * @param <T>    The target type.
     * @return An {@link Optional} containing the value.
     */
    private <T> Optional<T> get(String key, Function<Object, T> mapper) {
        return Optional.ofNullable(data.get(key)).map(mapper);
    }

    /**
     * Sets a value at the given key.
     *
     * @param key   The key to set.
     * @param value The value to set at the key.
     */
    public void set(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Gets a string.
     *
     * @param key The key of the string.
     * @return An {@link Optional} containing the string.
     */
    public Optional<String> getString(String key) {
        return get(key, Objects::toString);
    }

    /**
     * Gets an integer.
     *
     * @param key The key of the integer.
     * @return An {@link Optional} containing the integer.
     */
    public Optional<Integer> getInt(String key) {
        return get(key);
    }

    /**
     * Gets a double.
     *
     * @param key The key of the double.
     * @return An {@link Optional} containing the double.
     */
    public Optional<Double> getDouble(String key) {
        return get(key);
    }

    /**
     * Gets a float.
     *
     * @param key The key of the float.
     * @return An {@link Optional} containing the float.
     */
    public Optional<Float> getFloat(String key) {
        return get(key);
    }

    /**
     * Gets a boolean.
     *
     * @param key The key of the boolean.
     * @return An {@link Optional} containing the boolean.
     */
    public Optional<Boolean> getBoolean(String key) {
        // Handle integer-booleans
        return get(key, val -> val instanceof Integer ? (Integer) val != 0 : (Boolean) val);
    }

    /**
     * Gets an enum constant.
     *
     * @param key      The key of the enum constant.
     * @param enumType The enum class.
     * @param <T>      The type of the enum class.
     * @return An {@link Optional} containing the enum constant.
     */
    public <T extends Enum<T>> Optional<T> getEnumConstant(String key, Class<T> enumType) {
        return getString(key).flatMap(val -> Utils.getEnumConstant(enumType, val));

    }

    /**
     * Gets a Hunk.
     *
     * @param key The key of the hunk.
     * @return An {@link Optional} containing the hunk.
     */
    public Optional<Hunk> getHunk(String key) {
        return this.<Map>get(key).map(Hunk::new);
    }
}
