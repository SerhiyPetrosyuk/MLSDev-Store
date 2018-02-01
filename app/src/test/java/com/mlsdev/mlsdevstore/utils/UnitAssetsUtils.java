package com.mlsdev.mlsdevstore.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;

import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.io.InputStream;

public final class UnitAssetsUtils {

    public static String getAllSignInErrorsJson() {
        return getJsonStringFromResources("sign_in_error_wrong_all_fields.json");
    }

    public static String getVehicleNotExistSignInErrorsJson() {
        return getJsonStringFromResources("sign_in_error_vehicle_not_exist.json");
    }

    public static CategoryTree getCategoryTree() {
        return new Gson().fromJson(getJsonStringFromResources("category_tree.json"), CategoryTree.class);
    }

    public static SearchResult getSearchItemsResult() {
        return new Gson().fromJson(getJsonStringFromResources("search_items.json"), SearchResult.class);
    }

    public static SearchResult getSearchMoreItemsResult() {
        return new Gson().fromJson(getJsonStringFromResources("search_more_items.json"), SearchResult.class);
    }

    public static Item getProductItem() {
        return new Gson().fromJson(getJsonStringFromResources("item_details.json"), Item.class);
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .create();
    }

    private static String getJsonStringFromResources(String fileName) {
        Context context = RuntimeEnvironment.application;
        String json = null;
        try {
            InputStream inputStream = context.getClassLoader().getResourceAsStream(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;

    }

}
