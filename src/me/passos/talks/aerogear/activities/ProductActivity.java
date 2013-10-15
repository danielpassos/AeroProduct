package me.passos.talks.aerogear.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import me.passos.talks.aerogear.AeroProductsApplication;
import me.passos.talks.aerogear.R;
import me.passos.talks.aerogear.fragments.ProductFormFragment;
import me.passos.talks.aerogear.fragments.ProductListFragment;
import me.passos.talks.aerogear.model.Product;
import me.passos.talks.aerogear.util.Constants;
import org.jboss.aerogear.android.pipeline.AbstractActivityCallback;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends Activity {

    private AeroProductsApplication application;
    private Display display;
    private ProgressDialog dialog = null;

    private enum Display {
        PRODUCT_LIST, PRODUCT_FORM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        application = (AeroProductsApplication) getApplication();

        retrieveProductListFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                displayProductForm(null);
                break;
            case R.id.menu_refresh:
                retrieveProductListFromServer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_group_product_list, Display.PRODUCT_LIST.equals(display));
        return super.onPrepareOptionsMenu(menu);
    }

    private void displayFragment(Display display, Fragment fragment) {
        this.display = display;
        this.invalidateOptionsMenu();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

    public void displayProductList(List<Product> data) {
        displayFragment(Display.PRODUCT_LIST, new ProductListFragment(data));
    }

    public void displayProductForm(Product product) {
        ProductFormFragment fragment = new ProductFormFragment();
        if( product != null ) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.PRODUCT, product);
            fragment.setArguments(bundle);
        }
        displayFragment(Display.PRODUCT_FORM, fragment);
    }

    public ProgressDialog showProgressDialog(String message) {
        return ProgressDialog.show(this, getString(R.string.wait), message, true, true);
    }

    private void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void displayErrorMessage(Exception e) {
        Log.e(Constants.TAG, e.getMessage(), e);
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    // ---------------------------------------- AeroGear ----------------------------------------


    // -- GET

    public void retrieveProductListFromServer() {
        Toast.makeText(this, "Ok, They was retrieved ;)", Toast.LENGTH_SHORT).show();
        displayProductList(new ArrayList<Product>());
    }

    private static class ProcutListCallback extends AbstractActivityCallback<List<Product>> {

        private static final long serialVersionUID = 1L;

        public ProcutListCallback(Object... params) {
            super(serialVersionUID);
        }

        @Override
        public void onSuccess(List<Product> data) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.displayProductList(data);
        }

        @Override
        public void onFailure(Exception e) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.displayErrorMessage(e);
        }

    }

    // -- DELETE

    public void removeProjectOnServer(Product product) {
        Toast.makeText(this, "Ok, It was removed ;)", Toast.LENGTH_SHORT).show();
    }

    private static class ProductDeleteCallback extends AbstractActivityCallback<Void> {

        private static final long serialVersionUID = 1L;

        public ProductDeleteCallback(Object... params) {
            super(serialVersionUID);
        }

        @Override
        public void onSuccess(Void ignore) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.retrieveProductListFromServer();
        }

        @Override
        public void onFailure(Exception e) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.displayErrorMessage(e);
        }

    }

    // -- POST & PUT

    public void saveProjectOnServer(Product product) {
        Toast.makeText(this, "Ok, It was saved ;)", Toast.LENGTH_SHORT).show();
    }

    private static class ProductSaveCallback extends AbstractActivityCallback<Product> {

        private static final long serialVersionUID = 1L;

        public ProductSaveCallback(Object... params) {
            super(serialVersionUID);
        }

        @Override
        public void onSuccess(Product product) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.retrieveProductListFromServer();
        }

        @Override
        public void onFailure(Exception e) {
            ProductActivity activity = (ProductActivity) getActivity();
            activity.displayErrorMessage(e);
        }

    }

}