package edu.hm.weidacher.softarch.reflection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.FIELD})
public @interface RenderMe {

    String DEFAULT_RENDERING_PROVIDER = "edu.hm.weidacher.softarch.reflection.ReflectiveRenderer";

    /**
     * Declares which rendering provider to use.
     *
     * Must be a fully qualified class name.
     * @return n.a.
     */
    String with() default DEFAULT_RENDERING_PROVIDER;

}
