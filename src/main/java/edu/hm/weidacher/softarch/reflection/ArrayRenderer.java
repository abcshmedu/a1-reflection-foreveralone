package edu.hm.weidacher.softarch.reflection;


import org.apache.commons.lang3.ArrayUtils;

/**
 * Renderer, rendering array fields.
 *
 * This implementation can deal with arrays of primitives, arrays of objects and even objects.
 * It will only call toString() of the elements to describe.
 *
 * When initialized with a plain object, as a matter of implementation, the field will be rendered as if it would be
 *  contained in an array of length == 1.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class ArrayRenderer implements Renderer {

    private static final String EMPTY_SUBJECT_STRING = "[ /empty/ ]";
    private static final String NULL_STRING = "null";

    /**
     * Array of objects that will be rendered.
     */
    private final Object[] subject;

    /**
     * Ctor for wrapped arrays.
     * This is just a fast way of the other constructor, as it will be chosen at runtime for wrapped arrays over the other
     * ctor.
     *
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(Object... subject) {
	this.subject = subject;
    }

    /**
     * Ctor for rendering primitive arrays and objects.
     *
     * @param subject {@see ArrayRenderer#subject} primitive[], Object[] or Object
     */
    public ArrayRenderer(Object subject) {
	this.subject = deflateArray(subject);
    }


    /**
     * Takes the object, the Renderer was constructed with and renders it.
     *
     * @return a representative string of the object to render
     */
    @Override
    public String render() {
        if (subject == null) {
            return NULL_STRING;
	}
	if (subject.length == 0) {
	    return EMPTY_SUBJECT_STRING;
	}

	StringBuilder sb = new StringBuilder();

	sb.append("[");

	for (int i = 0; i < subject.length - 1; i++) {
	    Object o = subject[i];
	    sb.append(String.format("%s ,", toStringOfNullable(o)));
	}

	// last element treated specially because of StringBuilder
	Object lastElement = subject[subject.length - 1];
	sb.append(String.format("%s]", toStringOfNullable(lastElement)));

	return sb.toString();
    }

    /**
     * Returns a representative String of a given object.
     *
     * @param object an object or null
     * @return the toString() of the given object, or "null"
     */
    private String toStringOfNullable(Object object) {
	return object == null ? NULL_STRING : object.toString();
    }

    /**
     * Deflates the given object to an array.
     *
     * If the given array was not of an array type, an array of the length 1 containing the element is returned
     *  if the given array was of primitive type, the values will be wrapped
     * @param array 	If not of an array type, an array of the length 1 containing the element is returned
     *			if of primitive array type, the values will be wrapped
     *			if null, null is returned
     * @return @see param array
     */
    private static Object[] deflateArray(Object array) {
        if (array == null) {
            return null;
	}
        if (!array.getClass().isArray()) {
            // if the parameter wasn't an array, wrap it into one
            return new Object[] {array};
	}

	// cast and wrap to the type of the array
	switch(array.getClass().getTypeName()) {
	    case "boolean[]" :
	        return ArrayUtils.toObject((boolean[])array);
	    case "byte[]" :
		return ArrayUtils.toObject((byte[])array);
	    case "short[]" :
		return ArrayUtils.toObject((short[])array);
	    case "int[]" :
		return ArrayUtils.toObject((int[])array);
	    case "long[]" :
		return ArrayUtils.toObject((long[])array);
	    case "float[]" :
		return ArrayUtils.toObject((float[])array);
	    case "double[]" :
		return ArrayUtils.toObject((double[])array);
	    default:
	        return (Object[])array;
	}
    }
}
