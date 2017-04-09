package edu.hm.weidacher.softarch.reflection;


import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class ArrayRenderer implements Renderer {

    private static final String EMPTY_SUBJECT_STRING = "[ /empty/ ]";
    /**
     * Array of objects that will be rendered.
     */
    private final Object[] subject;

    /**
     * Ctor.
     *
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(Object... subject) {
	this.subject = subject;
    }

    /**
     * Ctor.
     *
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(Object subject) {
	if (!subject.getClass().isArray()) {
	    throw new IllegalArgumentException("This renderer can only render Array types");
	}

	this.subject = deflateArray(subject);
    }


    /**
     * Takes the object, the Renderer was constructed with and renders it.
     *
     * @return a representative string of the object to render
     */
    @Override
    public String render() {
	if (subject.length == 0) {
	    return EMPTY_SUBJECT_STRING;
	}

	StringBuilder sb = new StringBuilder();

	sb.append("[");

	for (int i = 0; i < subject.length - 1; i++) {
	    Object o = subject[i];
	    sb.append(String.format("%s ,", o == null ? "null" : o.toString()));
	}

	Object lastElemenent = subject[subject.length -1];

	sb.append(String.format("%s]", lastElemenent == null ? "null" : lastElemenent.toString()));

	return sb.toString();
    }

    /**
     * Deflates the given object to an array.
     *
     * If the given array was not of an array type, an array of the length 1 containing the element is returned
     *  if the given array was of primitive type, the values will be wrapped
     * @param array
     * @return
     */
    private static Object[] deflateArray(Object array) {
        if (!array.getClass().isArray()) {
            // if the parameter wasn't an array, wrap it into one
            return new Object[] {array};
	}

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
