package com.mlsdev.mlsdevstore.data.local;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class SharedPreferencesManagerTest {

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    private SharedPreferencesManager sharedPreferencesManager;
    private Object data;
    private String emptyJsonObject = "{}";
    private String emptyJsonArray = "[{}]";
    private String keyObject = "key_object";
    private String keyArray = "key_array";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(sharedPreferences.getString(keyObject, null)).thenReturn(emptyJsonObject);
        when(sharedPreferences.getString(keyArray, null)).thenReturn(emptyJsonArray);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        when(editor.remove(anyString())).thenReturn(editor);
        sharedPreferencesManager = new SharedPreferencesManager(new Gson(), sharedPreferences);
        data = new AppAccessToken();
    }

    @Test
    public void testSave() {
        sharedPreferencesManager.save(Key.APPLICATION_ACCESS_TOKEN, data);
        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1)).putString(Key.APPLICATION_ACCESS_TOKEN, emptyJsonObject);
        verify(editor, times(1)).apply();
    }

    @Test
    public void testRemove() {
        sharedPreferencesManager.remove(Key.APPLICATION_ACCESS_TOKEN);
        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1)).remove(Key.APPLICATION_ACCESS_TOKEN);
        verify(editor, times(1)).apply();
    }

    @Test
    public void testGetByClass() {
        sharedPreferencesManager.get(keyObject, AppAccessToken.class);
        verify(sharedPreferences, times(1)).getString(keyObject, null);
    }

    @Test
    public void testGetByType() {
        TypeToken<List<Object>> list = new TypeToken<List<Object>>() {
        };
        sharedPreferencesManager.get(keyArray, list.getType());
        verify(sharedPreferences, times(1)).getString(keyArray, null);
    }

}
