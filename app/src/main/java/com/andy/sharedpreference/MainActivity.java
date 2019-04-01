package com.andy.sharedpreference;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.andy.sharedpreference.Fragment.LoginFragment;
import com.andy.sharedpreference.Fragment.NoteFragment;
import com.andy.sharedpreference.Fragment.SettingFragment;
import com.andy.sharedpreference.adapters.NoteAdapter;
import com.andy.sharedpreference.models.User;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentListener, NoteFragment.OnNoteFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Settings settings;
    private Session session;

    public Settings getSettings() {
        return settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = new Settings(this);
        session = new Session(settings);

        addFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            createSettingFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment() {
        Fragment fragment = (session.isLogin()) ? new NoteFragment() : new LoginFragment();
        changeFragment(fragment, false);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void createSettingFragment() {
        Fragment settingFragment = new SettingFragment();
        changeFragment(settingFragment, true);
    }

    @Override
    public void onLoginButtonClicked(final View view, final String username, final String password) {
        User user = session.doLogin(username, password);
        String message = "Authentication failed";
        if (user != null) {
            message = "Welcome " + username;
            session.setUser(username);
            addFragment();
        }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterLinkClicked() {

    }


    @Override
    public void onNotesLoad(NoteAdapter adapter) {
        adapter.setNotes(Data.getNotes());
    }

    @Override
    public void onAddButtonClicked() {

    }

    @Override
    public void onLogoutMenuClicked() {
        session.doLogout();
        addFragment();
    }


}
