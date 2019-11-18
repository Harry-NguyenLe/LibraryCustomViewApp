package vn.edu.csc.librarycustomviewapp.ActivityCamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.edu.csc.librarycustomviewapp.R;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.btn_take_photo)
    Button btn_take_phto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_take_photo)
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                ImagePicker.create(this)
                        .start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("asdascewf1", "here" + requestCode);
        Log.d("asdascewf2", "here" + resultCode);
        Log.d("asdascewf3", "here" + data);
        if (ImagePicker.shouldHandle(requestCode, RESULT_OK, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            Bitmap bmImg = BitmapFactory.decodeFile(image.getPath());
            Log.d("asdascewf", bmImg.toString());

            camera.setImageBitmap(bmImg);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
