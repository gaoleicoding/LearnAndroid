package com.gaolei.famousfameproject.net.client;

import java.io.Serializable;

/**
 * Created by liuhaiyang on 2016/9/6.
 */
public class BaseServerResponse implements Serializable {
    public static final long serialVersionUID = 2561159070460523099L;
    public int code;
    public String msg;
    public Object data;
}
