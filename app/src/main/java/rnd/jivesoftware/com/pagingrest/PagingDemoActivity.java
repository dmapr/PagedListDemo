package rnd.jivesoftware.com.pagingrest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rnd.jivesoftware.com.pagingrest.rest.models.ActivityModel;
import rnd.jivesoftware.com.pagingrest.rest.models.PersonModel;
import rnd.jivesoftware.com.pagingrest.rest.services.JiveService;

public class PagingDemoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private JiveService jiveService;
    private ActivitiesViewModel activitiesViewModel;
    private PeopleViewModel peopleViewModel;
    private JiveActivitiesAdapter jiveActivitiesAdapter;
    private JivePeopleAdapter jivePeopleAdapter;
    private RecyclerView recyclerView;

    private int currentNavSelection = R.id.nav_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch(currentNavSelection) {
                    case R.id.nav_activity:
                        activitiesViewModel.reload();
                        break;
                    case R.id.nav_people:
                        peopleViewModel.reload();
                        break;
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initJiveService();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        jiveActivitiesAdapter = new JiveActivitiesAdapter();
        jivePeopleAdapter = new JivePeopleAdapter();

        activitiesViewModel = ViewModelProviders.of(this, new ActivitiesViewModelFactory(jiveService)).get(ActivitiesViewModel.class);
        peopleViewModel = ViewModelProviders.of(this, new PeopleViewModelFactory(jiveService)).get(PeopleViewModel.class);

        activitiesViewModel.activitiesList.observe(this, new Observer<PagedList<ActivityModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<ActivityModel> activityModels) {
                jiveActivitiesAdapter.setList(activityModels);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        peopleViewModel.peopleList.observe(this, new Observer<PagedList<PersonModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<PersonModel> personModels) {
                jivePeopleAdapter.setList(personModels);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setAdapter(jiveActivitiesAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.paging_demo, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_activity:
                currentNavSelection = R.id.nav_activity;
                recyclerView.setAdapter(jiveActivitiesAdapter);
                break;
            case R.id.nav_people:
                currentNavSelection = R.id.nav_people;
                recyclerView.setAdapter(jivePeopleAdapter);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initJiveService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build();

        jiveService = new Retrofit.Builder()
                .baseUrl("https://community.jivesoftware.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JiveService.class);
    }
}
