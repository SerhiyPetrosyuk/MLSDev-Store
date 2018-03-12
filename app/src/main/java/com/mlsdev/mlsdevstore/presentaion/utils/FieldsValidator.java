package com.mlsdev.mlsdevstore.presentaion.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Patterns;

import com.mlsdev.mlsdevstore.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;

import static com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator.Field.EMAIL;

public class FieldsValidator {
    private Context context;
    private final Map<String, String> fieldsForValidation;
    private final Map<String, String> invalidFields;

    @Inject
    public FieldsValidator(Context context) {
        this.context = context;
        fieldsForValidation = new HashMap<>();
        invalidFields = new ArrayMap<>();
    }

    public FieldsValidator putField(String key, String value) {
        fieldsForValidation.put(key, value);
        return this;
    }

    public Completable validateFields() {
        return Completable.create(emitter -> {
            invalidFields.clear();

            for (Map.Entry<String, String> entry : fieldsForValidation.entrySet())
                if (TextUtils.isEmpty(entry.getValue()))
                    invalidFields.put(entry.getKey(), context.getString(R.string.error_empty_field));

            if (!invalidFields.containsKey(EMAIL) && fieldsForValidation.containsKey(EMAIL) && !validateEmail(fieldsForValidation.get(EMAIL)))
                invalidFields.put(EMAIL, context.getString(R.string.error_incorrect_field));

            fieldsForValidation.clear();

            if (invalidFields.isEmpty())
                emitter.onComplete();
            else
                emitter.onError(new ValidationError(invalidFields));
        });
    }

    private boolean validateEmail(@Nullable String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public @interface Field {
        String EMAIL = "email";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
    }

    public class ValidationError extends Throwable {
        private final Map<String, String> invalidFields;

        public ValidationError(Map<String, String> invalidFields) {
            this.invalidFields = invalidFields;
        }

        public Map<String, String> getInvalidFields() {
            return invalidFields;
        }
    }
}
