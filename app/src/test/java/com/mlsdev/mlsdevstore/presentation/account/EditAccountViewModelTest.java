package com.mlsdev.mlsdevstore.presentation.account;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.local.LocalDataSource;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;
import com.mlsdev.mlsdevstore.presentaion.account.EditAccountViewModel;
import com.mlsdev.mlsdevstore.presentaion.utils.FieldsValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.reactivex.Single;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 26)
public class EditAccountViewModelTest {

    @Mock
    LocalDataSource dataSource;

    @Mock
    FieldsValidator fieldsValidator;

    private EditAccountViewModel viewModel;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        viewModel = spy(new EditAccountViewModel(RuntimeEnvironment.application, dataSource, fieldsValidator));
    }

    @Test
    public void start() {
        when(dataSource.getPersonalInfo()).thenReturn(Single.just(new PersonalInfo()));

        viewModel.start();

        verify(dataSource, atLeastOnce()).getPersonalInfo();
    }

}
