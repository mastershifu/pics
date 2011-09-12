package com.pics.dao;

import java.io.Serializable;

abstract public class BaseEntity implements Serializable {

    public abstract boolean equals(Object o);

    public abstract int hashCode();

}
