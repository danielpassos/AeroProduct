package me.passos.talks.aerogear;

import android.app.Activity;
import android.app.Application;
import me.passos.talks.aerogear.model.Product;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.pipeline.LoaderPipe;

import static me.passos.talks.aerogear.util.Constants.BASE_BACKEND_URL;

public class AeroProductsApplication extends Application {

    private Pipeline pipeline;

    @Override
    public void onCreate() {
        super.onCreate();

        pipeline = new Pipeline(BASE_BACKEND_URL);
        pipeline.pipe(Product.class);
    }

    public LoaderPipe<Product> getProductPipe(Activity activity) {
        return pipeline.get("product", activity);
    }

}