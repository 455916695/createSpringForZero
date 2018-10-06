package org.litespring.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number > numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;

    public CustomNumberEditor (Class<? extends Number > numberClass ,boolean allowEmpty) throws IllegalArgumentException {

            this(numberClass,null,allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number > numberClass,NumberFormat numberFormat,boolean allowEmpty) throws  IllegalArgumentException{
        if(numberClass == null || !Number.class.isAssignableFrom(numberClass)){
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }

        this.numberClass  = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

}
