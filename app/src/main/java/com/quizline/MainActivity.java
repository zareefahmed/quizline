package com.quizline;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.edit_image) ImageView edit_image;
    @BindView(R.id.iv_profile_image) ImageView set_image;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_number) EditText et_number;
    @BindView(R.id.et_email) EditText et_email;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.et_confirm_password) EditText et_confirm_pass;
    @BindView(R.id.til_name) TextInputLayout til_name;
    @BindView(R.id.til_mobile) TextInputLayout til_number;
    @BindView(R.id.til_email) TextInputLayout til_email;
    @BindView(R.id.til_password) TextInputLayout til_password;
    @BindView(R.id.til_confirm_pass) TextInputLayout til_confirm_pass;
    @BindView(R.id.btn_register) Button bt_register;
    EditText editText;
    String userChoosenTask;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        initView();
        setListener();
    }

    private void setListener() {
        edit_image.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        til_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 2) {
                    til_name.setErrorEnabled(true);
                    til_name.setError("Invalid Name");
                    til_name.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 2) {
                    til_name.setErrorEnabled(false);
                    til_name.setError(null);
                }

            }
        });
        til_number.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number = et_number.getText().toString().trim();
                Pattern p = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$");
                Matcher matcher = p.matcher(number);
                if (number.length() < 10 && !matcher.matches()) {
                    til_number.setErrorEnabled(true);
                    til_number.setError("Invalid nummber");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                number = et_number.getText().toString().trim();
                Pattern p = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$");
                Matcher matcher = p.matcher(number);
                if (!TextUtils.isEmpty(number) && number.length() == 10 && matcher.matches()) {
                    til_number.setErrorEnabled(false);
                    til_number.setError(null);
                }
            }
        });
        til_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_email.getText().toString().length() < 3) {
                    til_email.setErrorEnabled(true);
                    til_email.setError("Invalid Email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_email.getText().toString().length() > 3) {
                    til_email.setErrorEnabled(false);
                    til_email.setError(null);
                }
            }
        });
        til_confirm_pass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_password.getText().toString() != null && !et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_pass.getText().toString().trim())) {
                    til_confirm_pass.setErrorEnabled(true);
                    til_confirm_pass.setError("Password Does not Match");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_password.getText().toString() != null && et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_pass.getText().toString().trim())) {

                    til_confirm_pass.setErrorEnabled(false);
                    til_confirm_pass.setError(null);
                }
            }
        });
    }

    public void initView() {
        edit_image = findViewById(R.id.edit_image);
        set_image = findViewById(R.id.iv_profile_image);
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_pass = findViewById(R.id.et_confirm_password);
        bt_register = findViewById(R.id.btn_register);
        til_name = findViewById(R.id.til_name);
        til_number = findViewById(R.id.til_mobile);
        til_email = findViewById(R.id.til_email);
        til_password = findViewById(R.id.til_password);
        til_confirm_pass = findViewById(R.id.til_confirm_pass);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 7 && resultCode == RESULT_OK) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            set_image.setImageBitmap(bitmap);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_image:
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 7);
                selectImage();
                break;
            case R.id.btn_register:

                if (!TextUtils.isEmpty(et_name.getText().toString().trim()) && !TextUtils.isEmpty(et_email.getText().toString()) &&
                        !TextUtils.isEmpty(et_password.getText().toString().trim()) && !TextUtils.isEmpty(et_confirm_pass.getText().toString().trim())) {
                    if (et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_pass.getText().toString().trim())) {
                        FragmentManager fragmentManager=getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        CustomDialog customDialog=new CustomDialog();
                      fragmentTransaction.add(customDialog,"dialog");

                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(this, "Password does not match.", Toast.LENGTH_SHORT).show();
                    }

                }  else {
                    Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Permission.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
      //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Uri photoUri = Uri.fromFile(getOutputPhotoFile());
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//        /// intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
//        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
//        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
//        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permission.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        set_image.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        set_image.setImageBitmap(bm);
    }

}

