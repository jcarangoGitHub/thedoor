package com.jca.thedoor.constants;

public final class ApiPaths {

    public static final String LOGIN = "/api/auth/login";
    public static final String LOGOUT = "/logout";
    public static final String LOGIN_PAGE = "/login"; //used to log out
    public static final String REGISTER_ADMIN = "/api/user/registeradmin";
    public static final String REGISTER_USER = "/api/user/register";
    public static final String EXCHANGER_RATES_CURRENT = "/api/exchanger/getCurrentRates";
    public static final String EXCHANGER_RATES_LAST_DAYS = "/api/exchanger/getRatesLastDays";
    public static final String NOTEBOOK = "/api/notebook/notebook";
    public static final String USER_FIND_BY_USERNAME = "/api/user/findByUserName";
    public static final String USER_UPDATE = "/api/user/update";
    public static final String NOTEBOOK_FIND_ALL_BY_USER = "/api/notebook/findAllByUser";
    public static final String NOTEBOOK_FIND_BY_USER_AND_NAME = "/api/notebook/findByUserAndName";
    public static final String COWORKER = "/api/coworker/coworker";
    public static final String COWORKER_FIND_BY_ID = "/api/coworker/findById";
    public static final String COWORKER_FIND_ALL = "/api/coworker/findAll";
    public static final String COWORKER_FIND_REVIEWERS = "/api/coworker/findReviewers";
    public static final String HASHTAG = "/api/hashtag/hashtag";
    public static final String HASHTAG_FIND_ALL = "/api/hashtag/findAll";
    public static final String THOUGHT = "/api/thought/thought";
    public static final String THOUGHT_FIND_BY_NOTEBOOK = "/api/thought/findByNotebook";

    public static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/configuration/**",
            "/swagger*/**",
            "/webjars/**"
    };

    public static final String[] SECURED_PATHS = {
            USER_FIND_BY_USERNAME,
            USER_UPDATE,
            EXCHANGER_RATES_CURRENT,
            EXCHANGER_RATES_LAST_DAYS,
            NOTEBOOK,
            NOTEBOOK_FIND_ALL_BY_USER,
            NOTEBOOK_FIND_BY_USER_AND_NAME,
            COWORKER,
            COWORKER_FIND_BY_ID,
            COWORKER_FIND_ALL,
            COWORKER_FIND_REVIEWERS,
            HASHTAG,
            HASHTAG_FIND_ALL,
            THOUGHT,
            THOUGHT_FIND_BY_NOTEBOOK
    };

    private ApiPaths() {
        // Prevent instantiation
    }
}

