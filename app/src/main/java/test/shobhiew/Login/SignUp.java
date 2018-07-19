package test.shobhiew.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import test.shobhiew.MainActivity;
import test.shobhiew.R;

public class SignUp extends Fragment{
    private static final String TAG = "RegisterActivity";
    private Vibrator vib;
    Animation animShake;
    private EditText firstname,lastname,phone,email,password,conpass;
    private TextInputLayout firstnameInput,lastnameInput,phoneInput,emailInput,passInput,conpassInput;
    private Button btn_signup;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    View x;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
         x =  inflater.inflate(R.layout.activity_sign_up,null);

        firstnameInput =  (TextInputLayout) x.findViewById(R.id.signup_input_layout_firstname);
        lastnameInput = (TextInputLayout) x.findViewById(R.id.signup_input_layout_lastname);
        phoneInput = (TextInputLayout) x.findViewById(R.id.signup_input_layout_phone);
        emailInput = (TextInputLayout) x.findViewById(R.id.signup_input_layout_email);
        passInput = (TextInputLayout) x.findViewById(R.id.signup_input_layout_pass);
        conpassInput = (TextInputLayout) x.findViewById(R.id.signup_input_layout_passcon);

        firstname = (EditText) x.findViewById(R.id.First_signup);
        lastname = (EditText) x.findViewById(R.id.Last_signup);
        phone = (EditText) x.findViewById(R.id.Phoe_signup);
        email = (EditText) x.findViewById(R.id.Email_signup);
        password = (EditText) x.findViewById(R.id.Pass_signup);
        password.setTransformationMethod(new PasswordTransformationMethod());
        conpass = (EditText) x.findViewById(R.id.ConPass_signup);
        conpass.setTransformationMethod(new PasswordTransformationMethod());
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(getActivity());

        animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.check);
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        btn_signup = (Button) x.findViewById(R.id.btn_register);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        return x;

    }
    public void submitForm(){
        final String Firstname = firstname.getText().toString();
        final String Lastnanme = lastname.getText().toString();
        final String Phone = phone.getText().toString();
        final String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();
        String ConPass = conpass.getText().toString().trim();
        if(!checkfirst()){
            firstname.setAnimation(animShake);
            firstname.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkLast()){
            lastname.setAnimation(animShake);
            lastname.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkPhone()){
            phone.setAnimation(animShake);
            phone.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkEmail()){
            email.setAnimation(animShake);
            email.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkPass()){
            password.setAnimation(animShake);
            password.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkPassCon()){
            conpass.setAnimation(animShake);
            conpass.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }

        if(!ConPass.equals(Pass)){
            Toast.makeText(getActivity(),"Password not match",Toast.LENGTH_SHORT).show();
            return ;
        }
        firstnameInput.setErrorEnabled(false);
        lastnameInput.setErrorEnabled(false);
        phoneInput.setErrorEnabled(false);
        emailInput.setErrorEnabled(false);
        mProgress.setMessage("Sign Up.....");
        mProgress.show();

        mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String user_email = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = mDatabase.child(user_email);
                    current_user_db.child("Name_User").setValue(Firstname+" "+Lastnanme);
                    current_user_db.child("Phone_User").setValue(Phone);
                    current_user_db.child("Email_User").setValue(Email);

                    Toast.makeText(getActivity(),"You are successfull Registered !!",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    mProgress.dismiss();
                    Toast.makeText(getContext(),"Current,Exist This Email",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

        });

    }
    private boolean checkfirst(){
        if(firstname.getText().toString().trim().isEmpty()){
            firstnameInput.setErrorEnabled(true);
            firstnameInput.setError("Please enter field");
            firstname.setError("Valid Input Required");
            return false;
        }
        firstnameInput.setErrorEnabled(false);
        return true;
    }
    private boolean checkLast(){
        if(lastname.getText().toString().trim().isEmpty()){
            lastnameInput.setErrorEnabled(true);
            lastnameInput.setError("Please enter field");
            lastname.setError("Valid Input Required");
            return  false;
        }
        lastnameInput.setErrorEnabled(false);
        return  true;
    }
    private boolean checkPhone(){
        if(phone.getText().toString().trim().isEmpty()){
            phoneInput.setErrorEnabled(true);
            phoneInput.setError("Please enter field");
            phone.setError("Valid Input Required");
            return  false;
        }
        phoneInput.setErrorEnabled(false);
        return true;
    }
    private boolean checkEmail(){
        if(email.getText().toString().trim().isEmpty()){
            emailInput.setErrorEnabled(true);
            emailInput.setError("Please enter field");
            email.setError("Valid Input Required");
            return  false;
        }
        emailInput.setErrorEnabled(false);
        return true;
    }
    private boolean checkPass(){
        if(password.getText().toString().trim().isEmpty()){
            passInput.setError("Please enter a Valid Password");
            requertFocust(passInput);
            return false;
        }
        return true;
    }
    private boolean checkPassCon(){
        if(conpass.getText().toString().trim().isEmpty()){
            conpassInput.setError("Please enter a Valid Retype Password");
            requertFocust(conpassInput);
            return false;
        }
        return true;
    }
    private void requertFocust(View view){
        if(view.requestFocus()){
           getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
