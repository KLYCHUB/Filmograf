package com.example.filmograf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText signupEmail, signupPassword, signupConfirm, signupFirstName, signupLastName;
    Button signupButton;
    TextView loginRedirectText;
    DatabaseHelper databaseHelper;
    RadioGroup ageRadioGroup;
    RadioButton selectedRadioButton;
    CheckBox agreeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirm = findViewById(R.id.signup_confirm);
        signupFirstName = findViewById(R.id.signup_first_name);
        signupLastName = findViewById(R.id.signup_last_name);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        databaseHelper = new DatabaseHelper(this);
        ageRadioGroup = findViewById(R.id.ageRadioGroup);
        agreeCheckbox = findViewById(R.id.agreeCheckbox);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String confirmPassword = signupConfirm.getText().toString();
                String firstName = signupFirstName.getText().toString();
                String lastName = signupLastName.getText().toString();

                boolean isValid = true; // Flag to track validation

                if (email.equals("") || password.equals("") || confirmPassword.equals("") || firstName.equals("") || lastName.equals("")) {
                    showToast("Tüm alanlar zorunludur");
                    isValid = false;
                }

                if (!password.equals(confirmPassword)) {
                    showToast("Geçersiz Şifre!");
                    isValid = false;
                }

                int selectedId = ageRadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    showToast("Lütfen yaş aralığınızı seçin");
                    isValid = false;
                } else {
                    selectedRadioButton = findViewById(selectedId); // Get the selected RadioButton
                }

                if (!agreeCheckbox.isChecked()) {
                    showToast("Lütfen kullanım koşullarını onaylayın");
                    isValid = false;
                }

                if (isValid) {
                    Boolean checkUserEmail = databaseHelper.checkEmail(email);

                    if (!checkUserEmail) {
                        Log.d("SignupActivity", "Attempting to insert user with email: " + email);
                        Boolean insert = databaseHelper.insertData(email, password, firstName, lastName);

                        if (insert) {
                            Log.d("SignupActivity", "User inserted successfully");
                            showSuccessDialog();
                        } else {
                            Log.e("SignupActivity", "User insertion failed");
                            showToast("Kayıt Başarısız!");
                        }
                    } else {
                        showToast("Kullanıcı zaten mevcut! Lütfen giriş yapın");
                    }
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearEditTexts();
    }

    private void showToast(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_success_dialog, null);
        builder.setView(dialogView);

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        Button okButton = dialogView.findViewById(R.id.okButton);

        messageTextView.setText("Kaydınız başarıyla gerçekleşti!");

        AlertDialog alertDialog = builder.create();

        // OK buttonuna tıklama işlevselliği ekleme
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // Dialog'u kapat
                // İşlem tamamlandığında yapılacaklar...
            }
        });

        alertDialog.show();
    }

    private void clearEditTexts() {
        signupEmail.setText("");
        signupPassword.setText("");
        signupConfirm.setText("");
        signupFirstName.setText("");
        signupLastName.setText("");
        ageRadioGroup.clearCheck();
        agreeCheckbox.setChecked(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearEditTexts();
    }
}

