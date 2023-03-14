package com.example.springboottaskmanager.aspect;

import java.lang.annotation.*;

/**
 * Аннотация для прослушивания методов аудитом
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

}
