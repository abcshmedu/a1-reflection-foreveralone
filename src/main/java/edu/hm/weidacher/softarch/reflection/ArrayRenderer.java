package edu.hm.weidacher.softarch.reflection;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class ArrayRenderer implements Renderer {

    /**
     * Array of objects that will be rendered.
     */
    private final Object[] subject;

    /**
     * Ctor.
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(Object... subject) {
	this.subject = subject;
    }

    /**
     * Ctor wrapping primitive boolean arrays
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(boolean... subject) {
	final Boolean[] wrapped = new Boolean[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive byte arrays
     * @param subject {@see ArrayRenderer#subject}
     */

    public ArrayRenderer(byte... subject) {
	final Byte[] wrapped = new Byte[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive short arrays
     * @param subject {@see ArrayRenderer#subject}
     */

    public ArrayRenderer(short... subject) {
	final Short[] wrapped = new Short[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive int arrays
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(int... subject) {
	final Integer[] wrapped = new Integer[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive long arrays
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(long... subject) {
	final Long[] wrapped = new Long[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive float arrays
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(float... subject) {
	final Float[] wrapped = new Float[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Ctor wrapping primitive double arrays
     * @param subject {@see ArrayRenderer#subject}
     */
    public ArrayRenderer(double... subject) {
	final Double[] wrapped = new Double[subject.length];

	for (int i = 0; i < subject.length; i++) {
	    wrapped[i] = subject[i];
	}

	this.subject = wrapped;
    }

    /**
     * Takes the object, the Renderer was constructed with and renders it.
     *
     * @return a representative string of the object to render
     */
    @Override
    public String render() {
	return null;
    }
}
