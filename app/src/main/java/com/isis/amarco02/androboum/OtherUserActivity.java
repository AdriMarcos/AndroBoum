package com.isis.amarco02.androboum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OtherUserActivity extends AppCompatActivity {
    MyArrayAdapter adapter = null;
    final List<Profil> userList = new ArrayList<>();

    private class MyArrayAdapter extends ArrayAdapter<Profil> {
        List<Profil> liste;
        private MyArrayAdapter(Context context, int resource, List<Profil> liste) {
            super(context, resource, liste);
            this.liste = liste;
        }
        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

        }
        @Override
        public int getCount() {
            return liste.size();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<Profil> userList = new ArrayList<>();
        final MyPagerAdapter adapter = new MyPagerAdapter(this, userList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        // on obtient l'intent utilisé pour l'appel
        Intent intent = getIntent();
        // on va chercher la valeur du paramètre position, et on
        // renvoie zéro si ce paramètre n'est pas positionné (ce qui ne devrait
        // pas arriver dans notre cas).
        final int position = intent.getIntExtra("position",0);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    userList.add(child.getValue(Profil.class));
                }
                adapter.notifyDataSetChanged();
                pager.setCurrentItem(position);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.v("AndroBoum", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);
        pager.setAdapter(adapter);

    }

    private void RemplirDonnees()
    {
        //On rempli les données
        ImageView imageProfilView = (ImageView)  findViewById(R.id.imageView2);
        TextView textView = (TextView) findViewById(R.id.textView4);
        TextView score = (TextView) findViewById(R.id.score);
        ImageView imageConnectedView = (ImageView) findViewById(R.id.verified_image);

        // on obtient l'intent utilisé pour l'appel
        Intent intent = getIntent();
        // on va chercher la valeur du paramètre position, et on
        // renvoie zéro si ce paramètre n'est pas positionné (ce qui ne devrait
        // pas arriver dans notre cas).
        int position = intent.getIntExtra("position",0);

        Log.v("AndroBoum", "taille liste : " + userList.size());
        // on va chercher le bon profil dans la liste
        Profil p = userList.get(position);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference photoRef = storage.getReference().child(p.getEmail() + "/photo.jpg");
        if (photoRef != null) {
            GlideApp.with(this /*Context*/)
                    .load(photoRef)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(imageProfilView);
        }
        // on positionne le email dans le TextView
        textView.setText(p.getEmail());
        score.setText(String.valueOf(p.getScore()));
        // si l'utilisateur n'est pas connecté, on rend invisible le troisième
        // composant
        if (!p.isConnected) {
            imageConnectedView.setVisibility(View.GONE);
        }
    }

}
