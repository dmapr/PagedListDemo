package rnd.jivesoftware.com.pagingrest.rest.models;

import java.util.List;
import java.util.Objects;

public class ResourceModel {
    public List<String> allowed;
    public String ref;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceModel that = (ResourceModel) o;
        return Objects.equals(allowed, that.allowed) &&
                Objects.equals(ref, that.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowed, ref);
    }
}
