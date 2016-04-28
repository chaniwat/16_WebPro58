package com.alumnisystem.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by meranote on 4/28/2016 AD.
 */
public abstract class Model implements Serializable {

    public HashMap<String, Object> tuples = new HashMap<>();

}
