package com.affinion.gce.annotation;

import com.affinion.gce.validator.AssetDataValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    public Class<? extends AssetDataValidator> value();
}
