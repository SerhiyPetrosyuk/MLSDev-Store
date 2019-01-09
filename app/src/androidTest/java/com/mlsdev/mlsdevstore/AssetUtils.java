package com.mlsdev.mlsdevstore;

import android.content.Context;
import androidx.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;

import java.io.IOException;
import java.io.InputStream;

public class AssetUtils {


    public static String getAllSignInErrorsJson() {
        return getJsonStringFromAssets("sign_in_error_wrong_all_fields.json");
    }

    public static String getVehicleNotExistSignInErrorsJson() {
        return getJsonStringFromAssets("sign_in_error_vehicle_not_exist.json");
    }

    public static CategoryTree getCategoryTree() {
        return new Gson().fromJson(getJsonStringFromAssets("category_tree.json"), CategoryTree.class);
    }

    public static SearchResult getSearchItemsResult() {
        return new Gson().fromJson(getJsonStringFromAssets("search_items.json"), SearchResult.class);
    }

    public static SearchResult getSearchMoreItemsResult() {
        return new Gson().fromJson(getJsonStringFromAssets("search_more_items.json"), SearchResult.class);
    }

    public static Item getProductItem() {
        return new Gson().fromJson(getJsonStringFromAssets("item_details_extended.json"), Item.class);
    }

    public static String getJsonStringFromAssets(String fileName) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
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
