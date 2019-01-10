package com.mlsdev.mlsdevstore.presentation.account;

import android.app.Instrumentation;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import android.util.ArrayMap;

import com.mlsdev.mlsdevstore.MockMLSDevStoreApplication;
import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.local.LocalDataSource;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;
import com.mlsdev.mlsdevstore.presentaion.account.EditPersonalInfoActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.mockito.Mockito.*;

@LargeTest
public class EditPersonalInfoActivityTest {

    @Inject
    LocalDataSource localDataSource;

    @Inject
    FieldsValidator fieldsValidator;

    @Rule
    public ActivityTestRule<EditPersonalInfoActivity> rule = new ActivityTestRule<>(
            EditPersonalInfoActivity.class, false, false);

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        MockMLSDevStoreApplication mockApplication = (MockMLSDevStoreApplication) instrumentation.getTargetContext().getApplicationContext();
        mockApplication.getComponent().inject(this);
    }

    @Test
    public void init() {
        when(localDataSource.getPersonalInfo()).thenReturn(Single.just(new PersonalInfo()));
        rule.launchActivity(new Intent());
        onView(withId(R.id.submit_button)).check(matches(isDisplayed()));
        onView(withId(R.id.email_input_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.first_name_input_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.last_name_input_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.email_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.first_name_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.last_name_edit_text)).check(matches(isDisplayed()));
    }

    @Test
    public void submit_EmailIsIncorrect() {
        String incorrectEmail = "incorrect email";
        Map<String, String> incorrectFields = new ArrayMap<>(1);
        incorrectFields.put(FieldsValidator.Field.Companion.getEMAIL(), "is incorrect");
        FieldsValidator.ValidationError validationError = new FieldsValidator.ValidationError(incorrectFields);
        when(localDataSource.getPersonalInfo()).thenReturn(Single.just(new PersonalInfo()));
        when(fieldsValidator.validateFields()).thenReturn(Completable.error(validationError));
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getEMAIL(), incorrectEmail)).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getFIRST_NAME(), eq(anyString()))).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getLAST_NAME(), eq(anyString()))).thenReturn(fieldsValidator);

        rule.launchActivity(new Intent());
        onView(withId(R.id.email_edit_text)).perform(typeText(incorrectEmail));
        onView(withId(R.id.submit_button)).perform(click());
        onView(withText(R.string.error_incorrect_field)).check(matches(isDisplayed()));
    }

    @Test
    public void submit_EmptyFields() {
        Map<String, String> incorrectFields = new ArrayMap<>(1);
        incorrectFields.put(FieldsValidator.Field.Companion.getEMAIL(), "is empty");
        incorrectFields.put(FieldsValidator.Field.Companion.getFIRST_NAME(), "is empty");
        incorrectFields.put(FieldsValidator.Field.Companion.getLAST_NAME(), "is empty");
        FieldsValidator.ValidationError validationError = new FieldsValidator.ValidationError(incorrectFields);
        when(localDataSource.getPersonalInfo()).thenReturn(Single.just(new PersonalInfo()));
        when(fieldsValidator.validateFields()).thenReturn(Completable.error(validationError));
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getEMAIL(), eq(anyString()))).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getFIRST_NAME(), eq(anyString()))).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getLAST_NAME(), eq(anyString()))).thenReturn(fieldsValidator);

        rule.launchActivity(new Intent());

        onView(withId(R.id.submit_button)).perform(click());
        onView(Matchers.allOf(
                withText("is empty"),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                isDescendantOfA(withId(R.id.email_input_layout))))
                .check(matches(isDisplayed()));

        onView(Matchers.allOf(
                withText("is empty"),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                isDescendantOfA(withId(R.id.first_name_input_layout))))
                .check(matches(isDisplayed()));

        onView(Matchers.allOf(
                withText("is empty"),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                isDescendantOfA(withId(R.id.last_name_input_layout))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void submit() {
        String email = "some@email.com";
        String firstName = "First name";
        String lastName = "Last name";
        when(localDataSource.getPersonalInfo()).thenReturn(Single.just(new PersonalInfo()));
        when(localDataSource.updatePersonalInfo(email, firstName, lastName)).thenReturn(Completable.complete());
        when(fieldsValidator.validateFields()).thenReturn(Completable.complete());
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getEMAIL(), email)).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getFIRST_NAME(), firstName)).thenReturn(fieldsValidator);
        when(fieldsValidator.putField(FieldsValidator.Field.Companion.getLAST_NAME(), lastName)).thenReturn(fieldsValidator);

        rule.launchActivity(new Intent());

        onView(withId(R.id.email_edit_text)).perform(typeText(email));
        onView(withId(R.id.first_name_edit_text)).perform(typeText(firstName));
        onView(withId(R.id.last_name_edit_text)).perform(typeText(lastName));
        onView(withId(R.id.submit_button)).perform(click());

        verify(localDataSource, times(1)).updatePersonalInfo(email, firstName, lastName);
    }

}
