package vn.edu.csc.librarycustomviewapp.ActivityCamera;

import android.content.Context;
import android.widget.ImageView;

import com.esafirm.imagepicker.features.imageloader.ImageLoader;
import com.esafirm.imagepicker.features.imageloader.ImageType;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader {
    Context context;

    public PicassoImageLoader(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String path, ImageView imageView, ImageType imageType) {
        Picasso.with(context)
                .load("file://" + path)
                .resize(500, 500).centerCrop()
                .into(imageView);
    }
}
