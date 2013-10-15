package me.passos.talks.aerogear.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import me.passos.talks.aerogear.R;
import me.passos.talks.aerogear.activities.ProductActivity;
import me.passos.talks.aerogear.adapter.ProductAdapater;
import me.passos.talks.aerogear.model.Product;

import java.util.List;

public class ProductListFragment extends Fragment {

    private final List<Product> productList;

    public ProductListFragment(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ProductActivity activity = (ProductActivity) getActivity();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_list, null);

        ProductAdapater adapter = new ProductAdapater(getActivity(), productList);

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product selectedProduct = (Product) adapterView.getItemAtPosition(position);
                activity.displayProductForm(selectedProduct);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product selectedProduct = (Product) adapterView.getItemAtPosition(position);
                showDeleteConfirmation(selectedProduct);
                return true;
            }
        });

        return view;
    }

    private void showDeleteConfirmation(final Product product) {
        final ProductActivity activity = (ProductActivity) getActivity();

        new AlertDialog.Builder(getActivity())
                .setMessage("Delete '" + product.getName() + "'?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activity.removeProjectOnServer(product);
                            }
                        }).setNegativeButton("Cancel", null)
                .show();
    }

}
