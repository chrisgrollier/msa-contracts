package net.chrisgrollier.cloud.apps.common.log.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used where performance or debug logging is necessary. By default, only performance logging is enabled. Debug logging is enabled
 * with {@link #debug()} attribute and method arguments and returned values can be automatically logged with {@link #showArgValues()} attribute.
 * 
 * @author Atos
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Loggable {

    /**
     * Possible values for method signature rendering format in logs automatically generated on components annotated by {@link Loggable}.
     * 
     * @author Atos
     */
    enum SignatureTypes {
        LONG, NORMAL, SHORT
    }

    boolean PERF_DEFAULT_VALUE = true;
    boolean DEBUG_DEFAULT_VALUE = false;
    boolean SHOW_ARG_VALUES_DEFAULT_VALUE = false;
    int SIGNATURE_TYPE_DEFAULT_VALUE = -1;
    String SERVICE_DEFAULT_VALUE = "";

    /**
     * Indicates a logical business service name that will be rendered in logs if present in the annotation.
     * 
     * @return a logical business service name
     */
    String service() default SERVICE_DEFAULT_VALUE;

    /**
     * Indicate whether performance logging should be enabled on the annotated element. Note that environment property "application.log.perf.enabled"
     * must be set to true and info log level must be activated to actually produce performance traces.
     * 
     * @return true if performance logging should be enabled on the annotated element, false otherwise
     */
    boolean perf() default PERF_DEFAULT_VALUE;

    /**
     * Indicate whether debug logging should be enabled on the annotated element. Note that debug log level must also be activated to actually produce
     * debug traces.
     * 
     * @return true if debug logging should be enabled on the annotated element, false otherwise
     */
    boolean debug() default DEBUG_DEFAULT_VALUE;

    /**
     * Indicate whether debug logging should append passed arguments and returned values to generated message. Note that debug log level and
     * {@link #debug()} attribute must also be activated to actually render debug traces with passed arguments and returned values appended.
     * 
     * @return true if debug logging should append passed arguments and returned values to generated message, false otherwise
     */
    boolean showArgValues() default SHOW_ARG_VALUES_DEFAULT_VALUE;

    /**
     * Indicates type of String representation for called/executed method names
     * 
     * @return 0 for normal, strictly positive for long(detailed) and strictly negative for short
     */
    int signatureType() default -1;

}
