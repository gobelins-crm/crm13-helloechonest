package fr.louisbl.helloechonest.playlist;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;

import fr.louisbl.helloechonest.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link onSongClickedListener}
 * interface.
 */
public class SongFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<Playlist> {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESULTS = "results";
    private static final String ARG_ARTIST = "artist";
    private static final int LOADER_ID = 12;

    private int mResults;
    private String mArtist;

    private OnSongClickedListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static SongFragment newInstance(int results, String artist) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESULTS, results);
        args.putString(ARG_ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mResults = getArguments().getInt(ARG_RESULTS);
            mArtist = getArguments().getString(ARG_ARTIST);
        }

        mAdapter = new PlayListAdapter(getActivity());
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSongClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onSongClicked((Song) mAdapter.getItem(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public Loader<Playlist> onCreateLoader(int id, Bundle args) {
        PlayListLoader loader = new PlayListLoader(getActivity());
        loader.setResults(mResults);
        loader.setArtist(mArtist);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Playlist> loader, Playlist playlist) {
        ((PlayListAdapter) mAdapter).addAll(playlist.getSongs());
    }

    @Override
    public void onLoaderReset(Loader<Playlist> loader) {
        ((PlayListAdapter) mAdapter).clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSongClickedListener {
        public void onSongClicked(Song song);
    }

}
