package me.passos.talks.aerogear.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import me.passos.talks.aerogear.R;
import me.passos.talks.aerogear.activities.ProductActivity;
import me.passos.talks.aerogear.model.Product;
import me.passos.talks.aerogear.util.Constants;

import java.math.BigDecimal;

public class ProductFormFragment extends Fragment {

    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ProductActivity activity = (ProductActivity) getActivity();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_form, null);

        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText price = (EditText) view.findViewById(R.id.price);
        final Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
        final Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);

        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(Constants.PRODUCT);
            name.setText(product.getName());
            price.setText(product.getPrice().toString());
        } else {
            product = new Product();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( isEmptyFields(name, price) ) { return; }

                product.setName(name.getText().toString());
                product.setPrice(new BigDecimal(price.getText().toString()));

                activity.saveProjectOnServer(product);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.retrieveProductListFromServer();
            }
        });

        return view;
    }

    private boolean isEmptyFields(EditText... field) {
        boolean empty = false;

        for (EditText editText : field) {
            if( editText.getText().toString().trim().equals("") ) {
                editText.setError(getString(R.string.required));
                empty = true;
            }
        }

        return empty;
    }

}
