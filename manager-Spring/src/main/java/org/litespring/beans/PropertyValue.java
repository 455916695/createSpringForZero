package org.litespring.beans;

public class PropertyValue {

    private  final String  name;
    private final Object value;
    private boolean converted = false;
    private Object convertedValue;

    public PropertyValue (String name ,Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    //为什么此处要上锁
    public synchronized  boolean isConverted() {
        return  this.converted;
    }

    public synchronized Object getConvertedValue() {
        return convertedValue;
    }

    public synchronized void setConvertedValue(Object convertedValue) {
        this.convertedValue = convertedValue;
    }
}
