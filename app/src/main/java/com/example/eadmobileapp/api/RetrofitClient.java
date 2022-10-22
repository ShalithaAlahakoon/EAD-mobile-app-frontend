package com.example.eadmobileapp.api;

public class RetrofitClient {
    private static final String BASE_URL = "http://127.0.0.0:3000";
    String LOGIN_URL = BASE_URL + "/login";
    String REGISTER_URL = BASE_URL + "/register";
    String GET_USER_URL = BASE_URL + "/user";
    String GET_ALL_USERS_URL = BASE_URL + "/users";
    String UPDATE_USER_URL = BASE_URL + "/user";
    String DELETE_USER_URL = BASE_URL + "/user";
    String GET_ALL_STATIONS_URL = BASE_URL + "/stations";
    String GET_ALL_AREAS_URL = BASE_URL + "/stations/areas";
}
