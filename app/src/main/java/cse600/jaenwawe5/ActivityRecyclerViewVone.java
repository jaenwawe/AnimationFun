package cse600.jaenwawe5;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;

public class ActivityRecyclerViewVone extends AppCompatActivity implements FragmentRecyclerView.OnRowClick {

    private Toolbar toolbar;
    private SearchView search;
    RecyclerView mRecyclerView;
    MovieData movieData;

    ImageButton movieImage;

    @Override
    public void onItemSelected(HashMap<String, ?> movie, View sharedImage) {

        movieData = new MovieData();
        Fragment_MasterDetail details = Fragment_MasterDetail.newInstance(movie, sharedImage);
        details.setSharedElementEnterTransition(new DetailsTransition());
        details.setEnterTransition(new Fade());
        details.setExitTransition(new Fade());
        details.setSharedElementReturnTransition(new DetailsTransition());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_container_recyclerview, details)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_recyclerview);
        //initialize toolbar as actionbar
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            FragmentRecyclerView fragment = FragmentRecyclerView.newInstance(0);
            fragment.setEnterTransition(new Slide(Gravity.BOTTOM));
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                    R.animator.fancy_animation);
            set.setTarget(movieImage);
            set.start();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_container_recyclerview, fragment)
                    .commit();
        }

    }
}