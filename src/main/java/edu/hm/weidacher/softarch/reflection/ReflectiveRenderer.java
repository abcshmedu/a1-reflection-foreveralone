package edu.hm.weidacher.softarch.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.weidacher.softarch.reflection.annotation.RenderMe;

/**
 * An immutable Renderer using the Reflection API to get the subjects state.
 * The renderer uses the {@link edu.hm.weidacher.softarch.reflection.annotation.RenderMe} annotation
 * to determine which fields to use.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class ReflectiveRenderer implements Renderer {

    private static final Class< ? extends Annotation> RENDER_ANNOTATION_TYPE = RenderMe.class;

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
    @Override
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

        // check if the annotation want's another renderer
        Optional<Renderer> specialRenderer = getSpecialRenderer(field);
        if (specialRenderer.isPresent()) {

            // initialize the renderer and let it deal with the problem
            value = specialRenderer.get().render();

        } else {

            try {
                value = field.get(subject).toString();
            } catch (IllegalAccessException e) {
                throw new AssertionError("Field with modified access modifier inaccessible", e);
            }

        }

        return String.format("\t%s (%s) %s%n", name, type, value);
    }

    /**
     * Returns an instance of the renderer depicted in the RenderMe annotation.
     * @param field the field to examine
     * @return Optional resolving the desired Renderer if required and special
     */
    private Optional<Renderer> getSpecialRenderer(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations())
            .filter(annotation -> annotation.annotationType() == (RENDER_ANNOTATION_TYPE))
            .map(annotation -> ((RenderMe)annotation).with())
            .findAny()
            .map(className -> {

                // don't instantiate a new renderer if it is of the same type as this object
                if (className.equals(getClass().getCanonicalName())) {
                    return null;
                }

                try {
                    checkAccess(field);

                    Object instance;
                    Object fieldValue = field.get(subject);

                    instance = Class.forName(className).getConstructor(Object.class).newInstance(fieldValue);

                    if (instance instanceof Renderer) {
                        return (Renderer)instance;
                    }
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    System.err.printf("Classname specified in RenderMe.with must be a Renderer, was %s", className);
                }

                return null;
            });
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
        final Class subjectClass = subject.getClass();
        final List<Field> fields = new ArrayList<>();

        // add fields of the subjects declared fields
        fields.addAll(Arrays.asList(subjectClass.getDeclaredFields()));

        // add all fields for the superclasses
        Class superClassClass = subjectClass.getSuperclass();
        while (superClassClass != Object.class && superClassClass != null) {
            fields.addAll(Arrays.asList(superClassClass.getDeclaredFields()));
            superClassClass = superClassClass.getSuperclass();
        }

        return fields.stream()
            .filter(field -> field.isAnnotationPresent(RENDER_ANNOTATION_TYPE));
    }
}
