package org.workable.common;

public enum SortEnum {
    LIKE("like"),
    HATE("hate"),
    DATE("date");

    public String sortEnum;


    SortEnum(String sortEnum) {
        this.sortEnum = sortEnum;
    }
}
