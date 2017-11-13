package rnd.jivesoftware.com.pagingrest.rest.models;

import java.util.Objects;

public class ActorModel {
    public String displayName = "";
    public ImageModel image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorModel that = (ActorModel) o;
        return Objects.equals(displayName, that.displayName) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, image);
    }
}
