package com.mlsdev.mlsdevstore.dependency_injection.component.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface FragmentScope {
}
