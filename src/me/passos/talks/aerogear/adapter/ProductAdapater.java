package me.passos.talks.aerogear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import me.passos.talks.aerogear.R;
import me.passos.talks.aerogear.model.Product;

import java.util.List;

public class ProductAdapater extends BaseAdapter {

    private final Context context;
    private final List<Product> products;

    public ProductAdapater(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = products.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.product, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(product.getName());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(String.valueOf(product.getPrice()));

        return view;
    }
}
