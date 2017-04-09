package edu.hm.weidacher.softarch.reflection;

/**
 * Renderer Interface.
 *
 * Derivatives of this class can render the state of an object to a representative string.
 * @author Simon Weidacher <weidache@hm.edu>
 */
public interface Renderer {

    /**
     * Takes the object, the Renderer was constructed with and renders it.
     * @return a representative string of the object to render
     */
    String render();

}
