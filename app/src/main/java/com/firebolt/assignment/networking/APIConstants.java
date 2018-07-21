package com.firebolt.assignment.networking;

import com.firebolt.assignment.BuildConfig;

public class APIConstants {
    private static String BASE_URL = "";
    public static final String GET_USERS = "api/users";
    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setAPIEnvironment() {
        switch (APIEnvironment.valueOf(BuildConfig.ENV)) {
            case DEBUG:
                 setupDevelopmentEnvironment();

                break;

            case RELEASE:
                setupProductionEnvironment();
                break;

            default:
                setupDevelopmentEnvironment();
                break;
        }
    }

    private static void setupDevelopmentEnvironment() {
        BASE_URL = "https://reqres.in/";
    }

    private static void setupProductionEnvironment() {
        BASE_URL = "https://reqres.in/";
    }

    public enum APIEnvironment {
        DEBUG, RELEASE
    }
}
