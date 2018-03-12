package com.mlsdev.mlsdevstore.data.local;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSessionRequest;
import com.mlsdev.mlsdevstore.data.model.user.Address;
import com.mlsdev.mlsdevstore.data.model.user.CreditCard;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource implements DataSource {

    private RemoteDataSource remoteDataSource;
    private AppDatabase database;

    @Inject
    public LocalDataSource(RemoteDataSource remoteDataSource, AppDatabase database) {
        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }

    @Override
    public Single<String> getDefaultCategoryTreeId() {
        Single<String> single = database.categoriesDao().queryDefaultCategoryTree()
                .flatMap(list -> !list.isEmpty()
                        ? Single.just(list.get(0).getCategoryTreeId())
                        : remoteDataSource.getDefaultCategoryTreeId());
        return remoteDataSource.prepareSingle(single);
    }

    @Override
    public Single<CategoryTree> getRootCategoryTree() {
        Single<List<CategoryTreeNode>> listSingle = database.categoriesDao().queryCategoryTreeNode();
        return remoteDataSource.prepareSingle(listSingle)
                .flatMap(nodes -> {
                    CategoryTree categoryTree = new CategoryTree();
                    CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
                    categoryTree.setCategoryTreeNode(categoryTreeNode);
                    categoryTreeNode.setChildCategoryTreeNodes(nodes);

                    return !nodes.isEmpty()
                            ? Single.just(categoryTree)
                            : remoteDataSource.getRootCategoryTree();
                });
    }

    @Override
    public Single<CategoryTree> refreshRootCategoryTree() {

        return Single.fromCallable(() -> {
            database.categoriesDao().deleteAllCategoryTreeNodel();
            database.categoriesDao().deleteAllCategoryTrees();
            return 1;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(integer -> remoteDataSource.refreshRootCategoryTree());
    }

    @Override
    public Single<SearchResult> searchItemsByCategoryId(Map<String, String> queries) {
        return remoteDataSource.searchItemsByCategoryId(queries);
    }

    @Override
    public Single<SearchResult> searchItemsByRandomCategory() {
        return getRootCategoryTree()
                .flatMap(categoryTree -> remoteDataSource.searchItemsByRandomCategory());
    }

    @Override
    public Single<SearchResult> searchMoreItemsByRandomCategory() {
        return remoteDataSource.searchMoreItemsByRandomCategory();
    }

    @Override
    public Single<Item> getItem(String itemId) {
        return remoteDataSource.getItem(itemId);
    }

    @Override
    public void resetSearchResults() {
        remoteDataSource.resetSearchResults();
    }

    public Single<PersonalInfo> getPersonalInfo() {
        return remoteDataSource
                .prepareSingle(database.personalInfoDao().queryPersonalInfo())
                .map(personalInfoList -> !personalInfoList.isEmpty() ? personalInfoList.get(0) : new PersonalInfo());
    }

    public Single<GuestCheckoutSessionRequest> getGuestCheckoutSession() {
        final Single<Address> shippingAddressSource = remoteDataSource
                .prepareSingle(database.addressDao().queryByType(Address.Type.SHIPPING))
                .map(addresses -> !addresses.isEmpty() ? addresses.get(0) : new Address());

        final Single<Address> billingAddressSource = remoteDataSource
                .prepareSingle(database.addressDao().queryByType(Address.Type.BILLING))
                .map(addresses -> !addresses.isEmpty() ? addresses.get(0) : new Address());

        final Single<CreditCard> creditCardSource = remoteDataSource
                .prepareSingle(database.creditCardDao().queryCard())
                .map(creditCards -> !creditCards.isEmpty() ? creditCards.get(0) : new CreditCard());

        return Single.zip(
                shippingAddressSource,
                billingAddressSource,
                creditCardSource,
                getPersonalInfo(),
                (shippingAddress, billingAddress, creditCard, personalInfo) -> {
                    creditCard.setBillingAddress(billingAddress);
                    GuestCheckoutSessionRequest guestCheckoutSession = new GuestCheckoutSessionRequest();
                    guestCheckoutSession.setShippingAddress(shippingAddress);
                    guestCheckoutSession.setCreditCard(creditCard);
                    guestCheckoutSession.setContactFirstName(personalInfo.getContactFirstName());
                    guestCheckoutSession.setContactLastName(personalInfo.getContactLastName());
                    guestCheckoutSession.setContactEmail(personalInfo.getContactEmail());
                    return guestCheckoutSession;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updatePersonalInfo(String email, String firstName, String lastName) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setContactEmail(email);
        personalInfo.setContactFirstName(firstName);
        personalInfo.setContactLastName(lastName);

        return Completable.fromRunnable(() -> {
            List<PersonalInfo> personalInfoList = database.personalInfoDao().queryPersonalInfoSync();

            if (!personalInfoList.isEmpty()) personalInfo.setId(personalInfoList.get(0).getId());

            database.personalInfoDao().insert(personalInfo);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
