package edu.hm.weidacher.softarch.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import edu.hm.weidacher.softarch.reflection.annotation.RenderMe;

/**
 * An immutable Renderer using the Reflection API to get the subjects state.
 * The renderer uses the {@link edu.hm.weidacher.softarch.reflection.annotation.RenderMe} annotation
 * to determine which fields to use.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class ReflectiveRenderer implements Renderer {

    private static final Class< ? extends Annotation> RENDER_ANNOTATION = RenderMe.class;

    /**
     * The object that can be rendered by this Renderer.
     */
    private final Object subject;

    /**
     * ReflectiveRenderer constructor.
     * Creates a new ReflectiveRenderer for a given subject
     *
     * @param subject the object to be rendered
     */
    public ReflectiveRenderer(Object subject) {
        this.subject = subject;
    }

    /**
     * Takes the object, the Renderer was constructed with and renders it.
     *
     * @return a representative string of the object to render
     */
    public String render() {
        StringBuilder sb = new StringBuilder();
        getFields()// get annotated fields
            .map(this::mapString) // map to string representation
            .forEach(sb::append); // glue string together

        return String.format("%s => {%n%s}%n", subjectString(), sb.toString());
    }

    /**
     * Returns a representative string for the subject.
     *
     * @return string representing the subject
     */
    private String subjectString() {
        return subject.getClass().getTypeName() + String.format("%n");
    }

    /**
     * Returns a representative string for the given field.
     * <p>
     * Style like: name (type) value\n
     *
     * @param field field delivering information
     * @return representative string
     */
    private String mapString(Field field) {
        String name, type, value;

        checkAccess(field);

        name = field.getName();
        type = field.getType().getSimpleName();

        try {
            value = field.get(subject).toString();
        } catch (IllegalAccessException e) {
            throw new AssertionError("Field with modified access modifier inaccessible", e);
        }

        // TODO maybe reset access

        return String.format("\t%s (%s) %s%n", name, type, value);
    }

    /**
     * Modifies the access to a given field if required.
     *
     * @param field the field to modify
     */
    private void checkAccess(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * Returns all the fields of the subjects that are annotated to be rendered.
     *
     * @return stream of all annotated fields of the subject
     */
    private Stream<Field> getFields() {
        // TODO also get fields of subclasses
        return Arrays.stream(subject.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(RENDER_ANNOTATION));
    }
}
