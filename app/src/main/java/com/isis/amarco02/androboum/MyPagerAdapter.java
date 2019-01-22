package com.isis.amarco02.androboum;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

class MyPagerAdapter extends PagerAdapter {
    List<Profil> liste;
    Context context;
    public MyPagerAdapter(Context context, List<Profil> liste) {
        this.liste = liste;
        this.context = context;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // on va chercher la layout
        ViewGroup layout = (ViewGroup) View.inflate(context, R.layout.other_user_fragment,
                null);
        // on l'ajoute à la vue
        container.addView(layout);
        // on le remplit en fonction du profil
        remplirLayout(layout, liste.get(position));
        // et on retourne ce layout
        return layout;
    }
    @Override
    public int getCount() {
        return liste.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    private void remplirLayout(ViewGroup layout,final Profil p) {
        ImageView imageProfilView = (ImageView)  layout.findViewById(R.id.imageView2);
        TextView textView = (TextView)  layout.findViewById(R.id.textView4);
        TextView score = (TextView) layout.findViewById(R.id.score);
        ImageView imageConnectedView = (ImageView)  layout.findViewById(R.id.verified_image);
        Button bouton = (Button) layout.findViewById(R.id.bomb_btn);

        // on télécharge dans le premier composant l'image du profil
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference photoRef = storage.getReference().child(p.getEmail() + "/photo.jpg");
        if (photoRef != null) {
            GlideApp.with(context)
                    .load(photoRef)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(imageProfilView);
        }
        if (!p.isConnected()) {
            imageConnectedView.setVisibility(View.GONE);
        }
        // on positionne le email dans le TextView
        textView.setText(p.getEmail());
        //On renseigne le score
        score.setText("Score : " + String.valueOf(p.getScore()));
        Log.v("Androboum","bingo"+p.getEmail()) ;

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AndroBoumApp.getBomber().setBomb(p, new Bomber.BomberInterface() {
                    @Override
                    public void userBombed() {
                    }
                    @Override
                    public void userBomber() {
                        // on lance l'activité de contrôle de la bombe
                        Intent intent = new Intent(context, BombActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }
}
