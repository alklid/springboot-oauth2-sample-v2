package com.medit.meditlink.common;

public class Router {

    public static final String API_VERSION = "/{v}";
    public static final String SCHEMA_VERSION = "/{s}";
    public static final String PREFIX_VERSION = Router.API_VERSION + Router.SCHEMA_VERSION;

    public static final class User {
        public static final String USER = Router.PREFIX_VERSION + "/users";
    }
}
