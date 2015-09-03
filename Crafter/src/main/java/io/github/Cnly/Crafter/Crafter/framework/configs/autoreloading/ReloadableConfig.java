package io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an IConfigManager instance which should be reloaded when auto
 * reloading is being called
 * 
 * @author Cnly
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReloadableConfig
{
    String group() default "";
    
    int priority() default 0;
}
