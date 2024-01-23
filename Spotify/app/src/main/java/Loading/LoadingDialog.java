package Loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.example.spotify.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     /*   super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading_dialog);  // Puedes crear un layout personalizado para el diálogo de carga
        setCancelable(false);*/

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);

        // Configura la animación del GIF directamente
        ImageView imageView = findViewById(R.id.progressImageView);
        imageView.setImageResource(R.drawable.loading_gif);

        // Si el GIF es una animación, inicia la animación
        if (imageView.getDrawable() instanceof AnimatedImageDrawable) {
            ((AnimatedImageDrawable) imageView.getDrawable()).start();
        }
    }
}
