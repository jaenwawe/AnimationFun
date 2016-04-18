package cse600.jaenwawe5;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.HashMap;

//pg 9 animation.pdf Fragment Transiton


/**
 * Created by Jae Nwawe on 2/25/2016.
 */
public class FragmentRecyclerView extends Fragment {
    RecyclerView mRecyclerView; //Jae added
    MovieData movieData; //Jae Added to accomplish pg 19

    RecyclerView.LayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;

    private static final String ARG_SECTION_NUMBER = "sectionNumber";

    public static FragmentRecyclerView newInstance(int sectionNumber) {
        FragmentRecyclerView fragment = new FragmentRecyclerView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private OnRowClick mDetailViewListener;

    public interface OnRowClick {
        void onItemSelected(HashMap<String, ?> movie, View sharedImage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRowClick) {
            mDetailViewListener = (OnRowClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRowClick");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        movieData = new MovieData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) == null)
            inflater.inflate(R.menu.menu_recycle_search, menu); //ActionBar pg 13

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView search= (SearchView) MenuItemCompat.getActionView(item);

        if (search != null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query){
                    int position = movieData.findFirst(query);
                    System.out.println("Found! :" + position);
                    if(position >= 0)
                        mRecyclerView.scrollToPosition(position);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query){
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.trash_can:


                Toast.makeText(getActivity(), "Clicked trash can", Toast.LENGTH_LONG).show();
                return true;
            case R.id.fragment_item:
                Toast.makeText(getActivity(), "Clicked Fragment Item", Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recycleview, container, false);
        //card goes into mRecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager((getActivity()));
        mRecyclerView.setLayoutManager(mLayoutManager);

        //bind MovieData populate cards in the current activity
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //return control to Activity

                int id = (Integer) view.getId();
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.moviesList.get(position);
                ImageView movieImage = (ImageView) view.findViewById(R.id.icon);

                mDetailViewListener.onItemSelected(movie, movieImage);

            }
            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        return rootView;
    }
}

