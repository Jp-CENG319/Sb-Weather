package weather.app.simpleweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import weather.app.simpleweather.Fragments.Room2Fragment;
import weather.app.simpleweather.Fragments.Room3Fragment;

public class Humid_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    static View.OnClickListener myOnClickListener;
    DatabaseReference ref;
    Humidity_Adapter humidity_adapter;
    Button humid_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humid_);
        firebaseAuth = FirebaseAuth.getInstance();
        humid_button = findViewById(R.id.humid_button);

        recyclerView =(RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ref = FirebaseDatabase.getInstance().getReference().child("JeremySensor");

        FirebaseRecyclerOptions<Humidity_Model> options =
                new FirebaseRecyclerOptions.Builder<Humidity_Model>()
                        .setQuery(ref.limitToLast(1), Humidity_Model.class)
                        .build();



        humidity_adapter = new Humidity_Adapter(options,Humid_Activity.this);
        recyclerView.setAdapter(humidity_adapter);
        humidity_adapter.startListening();

        humid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Humid_Activity.this,Humid_Graph.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView1 = (BottomNavigationView) findViewById(R.id.bottom_navi);
        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(Humid_Activity.this, Temperature_Activity.class));
                        break;

                    case R.id.action_Room2:
                        startActivity(new Intent(Humid_Activity.this, Humid_Activity.class));
                        break;

                    case R.id.action_Room3:
                        startActivity(new Intent(Humid_Activity.this, Pressure_Activity.class));
                        break;


                }
                return true;
            }
        });





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(new Intent(Humid_Activity.this, Temperature_Activity.class));
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){

            case R.id.logoutMenu: {
                firebaseAuth.signOut();
                /* mGoogleSignInClient.signOut();*/
                finish();
                startActivity(new Intent(Humid_Activity.this, LoginActivity.class));
                break;
            }


            case R.id.refreshMenu:{
                startActivity(new Intent(Humid_Activity.this, Temperature_Activity.class));
                showsnackbar();
                break;
            }



            case R.id.settings: {

                startActivity(new Intent(Humid_Activity.this, SettingsActivity.class));
                break;
            }


            case R.id.aboutUsMenu: {
                startActivity(new Intent(Humid_Activity.this, AboutActivity.class));
                break;

            }

            case R.id.contactUsMenu: {
                startActivity(new Intent(Humid_Activity.this, ContactUsActivity.class));
                break;
            }


        }

        return super.onOptionsItemSelected(item);
    }


    public void showsnackbar()
    {
        String refresh = getString(R.string.refresh);
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),refresh,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}



