package skylinequeries.rtree;

import com.google.common.base.Optional;

import static com.google.common.base.Optional.absent;

public final class ObjectsHelper {

    private ObjectsHelper() {
        // prevent instantiation
    }

    static void instantiateForTestCoveragePurposesOnly() {
        new ObjectsHelper();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> asClass(Object object, Class<T> cls) {
        if (object == null)
            return absent();
        else if (object.getClass() != cls)
            return absent();
        else
            return Optional.of((T) object);
    }

}
