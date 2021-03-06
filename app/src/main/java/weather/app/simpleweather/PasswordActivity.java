package weather.app.simpleweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText EmailResetPassword;
    private Button btnreset;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);



        firebaseAuth = FirebaseAuth.getInstance();

        EmailResetPassword = (EditText) findViewById(R.id.resetEmailPassword);
        btnreset = (Button) findViewById(R.id.btnResetPassword);

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = EmailResetPassword.getText().toString().trim();
                String email = getString(R.string.email);
                final String passreset = getString(R.string.passreset);
                final String linkerror = getString(R.string.linkerror);

                if(useremail.equals("")){

                    Toast.makeText(PasswordActivity.this,email, Toast.LENGTH_SHORT).show();

                }else{

                   firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){

                               Toast.makeText(PasswordActivity.this, passreset, Toast.LENGTH_SHORT).show();
                               finish();
                               startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                           }else {

                               Toast.makeText(PasswordActivity.this, linkerror, Toast.LENGTH_SHORT).show();

                           }
                       }
                   });
                }
            }
        });

    }



}