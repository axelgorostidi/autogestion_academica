package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.Token;
import com.tpf_gorostidi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Authentication_activity extends AppCompatActivity {

    private EditText et_mail_ingresar;
    private EditText et_pass_ingresar;
    private Integer GOOGLE_SIGN_IN = 100;
    private CallbackManager callFace = CallbackManager.Factory.create();
    private String emailFace = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticacion_layout);

        et_mail_ingresar = findViewById(R.id.et_mail_ingresar);
        et_pass_ingresar = findViewById(R.id.et_pass_ingresar);

        session();
    }

    public void session(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);

        String email = prefs.getString("email", null);
        String provider = prefs.getString("provider", null);

        if(email != null && provider != null){
            inicioActivity(email, ProviderType.valueOf(provider));
        }

    }

    public void onClickRegistrarse(View v){

        Intent intent = new Intent(getApplicationContext(), registrarse_activity.class);
        startActivity(intent);

//        if(et_mail_ingresar.getText().toString().isEmpty() || et_pass_ingresar.getText().toString().isEmpty()){
//            Toast.makeText(this, getString(R.string.ingreseDatos), Toast.LENGTH_SHORT).show();
//            return;
//        }
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(et_mail_ingresar.getText().toString(), et_pass_ingresar.getText().toString());
//        inicioActivity(et_mail_ingresar.getText().toString(), ProviderType.valueOf("BASIC"));
    }

    public void onClickIngresar(View v){
        if(et_mail_ingresar.getText().toString().trim().isEmpty() || et_pass_ingresar.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getString(R.string.ingreseDatos), Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(et_mail_ingresar.getText().toString().trim(), et_pass_ingresar.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            inicioActivity(et_mail_ingresar.getText().toString(), ProviderType.valueOf("BASIC"));
                        } else {
                            loginIncorrecto();
                        }
                    }
                });

    }

    public void loginIncorrecto(){
        Toast.makeText(this, getString(R.string.problemaLogin), Toast.LENGTH_SHORT).show();
    }

    public void onClickGoogle(View v){
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();

        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    public void onClickFacebook(View v){
        ArrayList<String> perm = new ArrayList();
        perm.add("email");

        LoginManager.getInstance().logInWithReadPermissions(this, perm);
        LoginManager.getInstance().registerCallback(callFace, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                FirebaseAuth.getInstance().signInWithCredential(credential);
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        final JSONObject json = response.getJSONObject();
                        try {
                            if(json != null){
                               // String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                                emailFace = json.getString("email");
                            }
                        } catch (JSONException e) {}
                    }
                });
                    preCargarDatosCuenta(emailFace);
                    inicioActivity(emailFace, ProviderType.FACEBOOK);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callFace.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential);
                    preCargarDatosCuenta(account.getEmail());
                    inicioActivity(account.getEmail(), ProviderType.GOOGLE);
                }
            } catch (ApiException e) {}
        }
    }

    public void inicioActivity(String email, ProviderType provider){
        Intent i = new Intent(this, inicio_activity.class);
        i.putExtra("email", email);
        i.putExtra("provider", provider);
        startActivity(i);
    }

    public void preCargarDatosCuenta(String email){

        db.collection("Users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Object nombre = documentSnapshot.get("firstName");
                        Object apellido = documentSnapshot.get("lastName");
                        Object fecha = documentSnapshot.get("born");
                        if(nombre == null && apellido == null && fecha == null){

                            Map<String, String> data = new HashMap<>();
                            data.put("born", "");
                            data.put("firstName", "");
                            data.put("lastName", "");
                            data.put("provider", ProviderType.GOOGLE.toString());

                            DocumentReference newUserRef = db.collection("Users").document(email);
                            newUserRef
                                    .set(data)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            errorFirebase();
                                        }
                                    });

                        }
                    }
                });

    }

    public void errorFirebase(){
        Toast.makeText(this, getString(R.string.problemaBD), Toast.LENGTH_SHORT).show();
    }

}