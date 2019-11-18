package vn.edu.csc.librarycustomviewapp.CreateNote.View;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.edu.csc.librarycustomviewapp.Base.BaseActivity;
import vn.edu.csc.librarycustomviewapp.R;
import vn.edu.csc.librarycustomviewapp.Utilities.Constant;

public class NoteActivity extends BaseActivity {
    @BindView(R.id.btn_save)
    ImageView btn_save;
    @BindView(R.id.btn_close)
    ImageView btn_close;
    @BindView(R.id.edt_note_tittle)
    EditText edt_note_tittle;
    @BindView(R.id.edt_note_description)
    EditText edt_note_description;
    @BindView(R.id.number_picker_priority)
    NumberPicker number_picker_priority;
    @BindView(R.id.layout_create_note)
    LinearLayout layout_create_note;
    @BindView(R.id.toolbar_note_activity)
    Toolbar toolbar_note_activity;
    @BindView(R.id.tv_note_action)
    TextView tv_note_action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        setContentView(R.layout.note_activity);

        ButterKnife.bind(this);
        number_picker_priority.setMaxValue(20);
        number_picker_priority.setMinValue(1);

        getDataFromNote();

    }

    private void getDataFromNote() {
        Intent data = getIntent();
        if (data != null) {
            Bundle bundle = data.getExtras();

            if (bundle != null) {
                Bundle bundleExtra = data.getBundleExtra(Constant.EXTRA_BUNDLE_NOTE_INFO);

                if (bundleExtra != null) {
                    edt_note_tittle.setText(bundleExtra.getString(Constant.EXTRA_TITTLE, ""));
                    edt_note_description.setText(bundleExtra.getString(Constant.EXTRA_DESCRIPTION, ""));
                    number_picker_priority.setValue(bundleExtra.getInt(Constant.EXTRA_PRIORITY, 1));
                    tv_note_action.setText(Constant.EDIT_NOTE);
                }
            }
        }
    }

    @OnClick({R.id.btn_save, R.id.btn_close, R.id.layout_create_note, R.id.toolbar_note_activity})
    public void handleClickEvents(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveNote();
                Log.d("asdcer", "save");
                hideSoftKeyBoard();
                break;
            case R.id.btn_close:
                onBackPressed();
                hideSoftKeyBoard();
                break;
            case R.id.layout_create_note:
                hideSoftKeyBoard();
                break;
            case R.id.toolbar_note_activity:
                hideSoftKeyBoard();
                break;
        }
    }

    private void saveNote() {
        String tittle = edt_note_tittle.getText().toString().trim();
        String description = edt_note_description.getText().toString().trim();
        int priority = number_picker_priority.getValue();

        if (tittle.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please insert tittle and description", Toast.LENGTH_SHORT).show();
        }

        if (tv_note_action.getText().toString().equalsIgnoreCase(Constant.EDIT_NOTE)) {
            Intent data = getIntent();
            Bundle bundle = data.getBundleExtra(Constant.EXTRA_BUNDLE_NOTE_INFO);

            if (bundle != null) {
                int id = bundle.getInt(Constant.EXTRA_ID, -1);
                if (id != -1) {
                    updateResultRightToLeft(data, bundle, tittle, description, priority, id);
                }
            }
        }

        else {
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            insertResultTransition(data, bundle, tittle, description, priority);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        overridePendingTransition(R.anim.enter_left, R.anim.exit_right);
        finish();
    }


}
