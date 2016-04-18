package cse600.jaenwawe5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;


public class Activity_MasterDetails extends AppCompatActivity implements FragmentRecyclerView.OnRowClick {

    android.support.v4.app.Fragment mFragment;
    MovieData movie;
    private FragmentRecyclerView.OnRowClick mDetailViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__master_details);

        movie = new MovieData();

        Intent intent = getIntent();
        HashMap<String, ?> movie = (HashMap<String, ?>) intent.getSerializableExtra("hashMap");
        Integer movieImageNumber = (Integer) movie.get("image");
        ImageView sharedImage = new ImageView(getApplicationContext());

        sharedImage.setImageResource(movieImageNumber);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, Fragment_MasterDetail.newInstance(movie, sharedImage))
                    .commit();
        }
    }

    //onRowClick Interface
    @Override
    public void onItemSelected(HashMap<String, ?> movie, View sharedImage) {
        mFragment = Fragment_MasterDetail.newInstance(movie, sharedImage);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mFragment)
                .addToBackStack(null)
                .commit();
    }
}