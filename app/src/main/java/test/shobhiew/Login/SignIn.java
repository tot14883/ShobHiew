package test.shobhiew.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import test.shobhiew.MainActivity;
import test.shobhiew.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignIn extends Fragment {
    private static final int RC_SIGN_IN = 234;
    private TextView Forget;
    private static final String TAG ="LoginActivity";
    private TextInputLayout email_input,pass_input;
    private EditText email_user,pass_user;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private Vibrator vib;
    Animation animShake;
    View x;
    private GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    private ProgressDialog mProgress;
    private Button btn_login;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button btn_facebook;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        x =  inflater.inflate(R.layout.activity_sign_in,null);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());


        btn_facebook = (Button) x.findViewById(R.id.btn_facebook_login);
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btn_facebook_login){
                    loginButton.performClick();
                }
            }
        });


        email_input = (TextInputLayout) x.findViewById(R.id.signin_input_layout_Email);
        pass_input = (TextInputLayout) x.findViewById(R.id.signin_input_layout_Password);

        email_user = (EditText) x.findViewById(R.id.Email_User);
        pass_user = (EditText) x.findViewById(R.id.Password_User);
        pass_user.setTransformationMethod(new PasswordTransformationMethod());

        mProgress = new ProgressDialog(getActivity());
        animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.check);
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        signInButton = (SignInButton) x.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setMessage("Sign In......");
                mProgress.show();
                signIn();
            }
        });
        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Log.d("TG","SIGNED OUT");
                }
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);

        loginButton = (LoginButton) x.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");

        loginButton.setFragment(this);
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mProgress.setMessage("Sign In...");
                mProgress.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.d("","facebook:onSuccess"+loginResult);
            }

            @Override
            public void onCancel() {
                Log.d("TAG","facebook:onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG","facebook:onError",exception);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
       /* mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(),"You Got an Error",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/
        btn_login = (Button) x.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        Forget = (TextView) x.findViewById(R.id.Forget_password);
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return x;

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void handleFacebookAccessToken(AccessToken token){
        Log.d("","handleFacebookAccessTokem"+token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                                Log.d("", "signWithCredential:onComplete:" + task.isSuccessful());
                                String user_id = mAuth.getCurrentUser().getUid();
                                String phone = mAuth.getCurrentUser().getPhoneNumber();
                                String email = mAuth.getCurrentUser().getEmail();
                                DatabaseReference current_user = mDatabase.child(user_id);
                                current_user.child("Phone_User").setValue(phone);
                                current_user.child("Email_User").setValue(email);
                                mProgress.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            if(!task.isSuccessful()){
                                Log.w("","signInWithCredentail",task.getException());
                                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                        }
                    }
                });
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                           /* String user_id = mAuth.getCurrentUser().getUid();
                            String phone = mAuth.getCurrentUser().getPhoneNumber();
                            String email = mAuth.getCurrentUser().getEmail();
                            DatabaseReference current_user = mDatabase.child(user_id);
                            current_user.child("Phone_User").setValue(phone);
                            current_user.child("Email_User").setValue(email);*/
                            mProgress.dismiss();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(),"Authentication Failed.",Toast.LENGTH_SHORT).show();;

                        }

                        // ...
                    }
                });
    }
    public void LoginUser(){
        final String Email = email_user.getText().toString().trim();
        String Pass = pass_user.getText().toString().trim();
        if(!checkEmail()){
            email_user.setAnimation(animShake);
            email_user.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if(!checkPass()){
            pass_user.setAnimation(animShake);
            pass_user.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        email_input.setErrorEnabled(false);
        pass_input.setErrorEnabled(false);
        mProgress.setMessage("Sign In......");
        mProgress.show();
        mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkUserExists();
                }
                else{
                    Toast.makeText(getActivity(),"Email or PassWord Incorrect !!!!",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
            }
        });

    }



    public boolean checkEmail(){
        String email = email_user.getText().toString();
        if(email.isEmpty()){
            email_input.setErrorEnabled(true);
            email_input.setError("Please enter a Valid Email");
            email_user.setError("Valid Input Required");
            requertFocust(email_input);
            return false;
        }
        email_input.setErrorEnabled(false);
        return true;
    }
    public boolean checkPass(){
        if(pass_user.getText().toString().trim().isEmpty()){
            pass_input.setError("Please enter a Valid Password");
            requertFocust(pass_input);
            return false;
        }
        return true;
    }
    private void requertFocust(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public void checkUserExists(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().finish();
                    startActivity(intent);
                    mProgress.dismiss();
                    Toast.makeText(getActivity(),"ล๊อคอินรีบร้อย",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mProgress != null && mProgress.isShowing()){
            mProgress.cancel();
        }
    }

}
