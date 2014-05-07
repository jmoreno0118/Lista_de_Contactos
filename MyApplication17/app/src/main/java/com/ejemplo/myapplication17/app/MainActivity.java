package com.ejemplo.myapplication17.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};

    private final static int[] TO_IDS = {android.R.id.text1};

    ListView mContactsList;

    private SimpleCursorAdapter mCursorAdapter;

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION ={ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsList = (ListView) findViewById(R.id.listContact);
        mCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        mContactsList.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        Uri baseUri = ContactsContract.Contacts.CONTENT_URI;
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                select,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        mContactsList.setAdapter(mCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}
