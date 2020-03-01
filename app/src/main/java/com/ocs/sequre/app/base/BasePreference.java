package com.ocs.sequre.app.base;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class BasePreference<T> {
    private final TypeToken<T> typeToken = new TypeToken<T>() {
    };
    T type;
    //private final Type type = typeToken.getType(); // or getRawType() to return Class<? super T>

    protected abstract String key();

    protected abstract SharedPreferences sharedPreferences();

    public boolean set(T t) {
        return sharedPreferences().edit().putString(key(), new Gson().toJson(t)).commit();
    }

    protected String defValue() {
        return "";
    }

    public T get() {
        return sharedPreferences().getString(key(), defValue()).isEmpty() ? null :
                (T) new Gson().fromJson(sharedPreferences().getString(key(), defValue()), getType());
    }

    public boolean clear() {
        return sharedPreferences().edit().remove(key()).commit();
    }

    public Type getType() {
        return new TypeToken<T>() {

        }.getType();
    }
}