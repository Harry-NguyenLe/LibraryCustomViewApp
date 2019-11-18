package vn.edu.csc.librarycustomviewapp.Base;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.csc.librarycustomviewapp.R;
import vn.edu.csc.librarycustomviewapp.Utilities.Constant;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void moveToNextActivityWithResult(Class classToMove) {
        Intent intent = new Intent(this, classToMove);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, Constant.ADD_NOTE_REQUEST_CODE, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void hideSoftKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void moveToActivityLeftToRightBundleWithResult(Class classToMove, Bundle bundle) {
        Intent data = new Intent(this, classToMove);
        data.putExtra(Constant.EXTRA_BUNDLE_NOTE_INFO, bundle);
        data.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(data, Constant.EDIT_NOTE_REQUEST_CODE);
        overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
    }

    public void updateResultRightToLeft(Intent data, Bundle bundle, String tittle, String description, int priority, int id) {
        bundle.putString(Constant.EXTRA_TITTLE, tittle);
        bundle.putString(Constant.EXTRA_DESCRIPTION, description);
        bundle.putInt(Constant.EXTRA_PRIORITY, priority);
        bundle.putInt(Constant.EXTRA_ID, id);
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        finish();
    }

    public void insertResultTransition(Intent data, Bundle bundle, String tittle, String description, int priority){
        bundle.putString(Constant.EXTRA_TITTLE, tittle);
        bundle.putString(Constant.EXTRA_DESCRIPTION, description);
        bundle.putInt(Constant.EXTRA_PRIORITY, priority);
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        finishAfterTransition();
    }
}
