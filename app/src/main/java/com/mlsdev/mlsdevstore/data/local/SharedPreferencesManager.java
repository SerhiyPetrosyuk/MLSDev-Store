package com.mlsdev.mlsdevstore.data.local;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {
    private final Gson gson;
    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesManager(Gson gson, SharedPreferences sharedPreferences) {
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void save(String key, Object data) {
        String json = gson.toJson(data);
        sharedPreferences.edit()
                .putString(key, json)
                .apply();
    }

    public void remove(String key) {
        sharedPreferences.edit()
                .remove(key)
                .apply();
    }

    public <T> T get(String key, Class<T> dataClass) {
        T data = null;
        String json = sharedPreferences.getString(key, null);

        if (json != null)
            data = gson.fromJson(json, dataClass);

        return data;
    }

    public <T> T get(String key, Type type) {
        T data = null;
        String json = sharedPreferences.getString(key, null);

        if (json != null)
            data = gson.fromJson(json, type);

        return data;
    }


    public @interface Key {
        String APPLICATION_ACCESS_TOKEN = "application_access_token";
        String RANDOM_CATEGORY_TREE_NODE = "random_category_tree_node";
    }

}
