package me.passos.talks.aerogear;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;
import me.passos.talks.aerogear.model.Product;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.pipeline.LoaderPipe;
import org.jboss.aerogear.android.unifiedpush.PushConfig;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.Registrations;

import java.net.URI;
import java.net.URISyntaxException;

import static me.passos.talks.aerogear.util.Constants.*;

public class AeroProductsApplication extends Application {

    private Pipeline pipeline;

    @Override
    public void onCreate() {
        super.onCreate();
        registerDeviceOnPushServer();

        pipeline = new Pipeline(BASE_BACKEND_URL);
        pipeline.pipe(Product.class);
    }

    public LoaderPipe<Product> getProductPipe(Activity activity) {
        return pipeline.get("product", activity);
    }

    public void registerDeviceOnPushServer() {

        try {
            PushConfig config = new PushConfig(new URI(UNIFIED_PUSH_URL), GCM_SENDER_ID);
            config.setVariantID(VARIANT_ID);
            config.setSecret(SECRET);

            Registrations registrations = new Registrations();
            PushRegistrar registrar = registrations.push("registrar", config);
            registrar.register(getApplicationContext(), new Callback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}
