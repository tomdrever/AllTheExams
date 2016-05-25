package tomdrever.alltheexams.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import tomdrever.alltheexams.R;
import tomdrever.alltheexams.data.Exam;

public class ExamListActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ExamListAdapter mExamListAdapter;

    private TextView mSearchDescription;

    private int tuning = 2;

    public ArrayList<Exam> examList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        mSearchDescription = (TextView) findViewById(R.id.search_description);

        RecyclerView examRecyclerView = (RecyclerView) findViewById(R.id.exam_list);

        // Todo - get JSON from file, selected by user
        String json = "[\n" + " {\n" + " \"dayOfMonth\":17,\n" + " \"details\":\"Listening\",\n" + " \"durationInMinutes\":35,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"French\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":17,\n" + " \"details\":\"Reading\",\n" + " \"durationInMinutes\":50,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"French\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":17,\n" + " \"details\":\"Unit 1\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Biology\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":18,\n" + " \"details\":\"Religion and Life Issues\",\n" + " \"durationInMinutes\":90,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Religious Studies\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":19,\n" + " \"details\":\"Unit 1\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Chemistry\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":23,\n" + " \"details\":\"English Literature Unit 1 \",\n" + " \"durationInMinutes\":90,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"English\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":23,\n" + " \"details\":\"Religion and Morality\",\n" + " \"durationInMinutes\":90,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Religious Studies\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":25,\n" + " \"details\":\"Unit 1\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Physics\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":26,\n" + " \"details\":\"Non-Calculator\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"Mathematics\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":27,\n" + " \"details\":\"English Literature Unit 2 \",\n" + " \"durationInMinutes\":75,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":5,\n" + " \"name\":\"English\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":6,\n" + " \"details\":\"Unit 1 - Perception and Dreaming\",\n" + " \"durationInMinutes\":75,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Psychology\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":7,\n" + " \"details\":\"English Language\",\n" + " \"durationInMinutes\":205,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"English\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":9,\n" + " \"details\":\"Calculator\",\n" + " \"durationInMinutes\":105,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Mathematics\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":9,\n" + " \"details\":\"Unit 2 - Social and Biological\",\n" + " \"durationInMinutes\":105,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Psychology\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":10,\n" + " \"details\":\"Unit 2\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Biology\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":10,\n" + " \"details\":\"Unit 3\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":10,\n" + " \"minuteOfHour\":5,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Biology\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":15,\n" + " \"details\":\"Unit 2\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":13,\n" + " \"minuteOfHour\":40,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Chemistry\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":15,\n" + " \"details\":\"Unit 3\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":14,\n" + " \"minuteOfHour\":45,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Chemistry\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":17,\n" + " \"details\":\"Unit 2\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":9,\n" + " \"minuteOfHour\":0,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Physics\",\n" + " \"year\":2016\n" + " },\n" + " {\n" + " \"dayOfMonth\":17,\n" + " \"details\":\"Unit 3\",\n" + " \"durationInMinutes\":60,\n" + " \"hourOfDay\":10,\n" + " \"minuteOfHour\":5,\n" + " \"monthOfYear\":6,\n" + " \"name\":\"Physics\",\n" + " \"year\":2016\n" + " }\n" + "]";

        try {
            examList = Exam.getExamsFromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mExamListAdapter = new ExamListAdapter(examList);

        assert examRecyclerView != null;
        examRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        examRecyclerView.setAdapter(mExamListAdapter);
        examRecyclerView.addItemDecoration(new ExamListDividerItemDecoration(this));

        // Set up swipe to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Delete then immediately add the timetable again - this
                        // will trigger the re-creation of each view, with updated
                        // details

                        // Todo - reload timetable from specified JSON file here
                        mExamListAdapter.clear();
                        mExamListAdapter.setAllExams(examList);

                        // Reset the search description
                        mSearchDescription.setText("");
                        collapse(mSearchDescription);

                        // Reset tuning to default
                        tuning = 2;

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        // Set the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchDescription.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam_list, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mExamListAdapter.getFilter().filter(query);
                    mSearchDescription.setText("Showing results for: " + query);
                    expand(mSearchDescription);
                    return true;
                }
            });

            MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Clear filter
                    mExamListAdapter.getFilter().filter("");

                    mSearchDescription.setText("");
                    collapse(mSearchDescription);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_tune:
                //Tune
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setSingleChoiceItems(new String[]{"Show only upcoming exams", "Show only finished exams", "Show both"},
                        tuning, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        // Upcoming only
                                        tuning = which;
                                        break;
                                    case 1:
                                        // Finished only
                                        tuning = which;
                                        break;
                                    case 2:
                                        // Both (default)
                                        tuning = which;
                                        break;
                                }
                            }
                        }).setPositiveButton("Tune", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mSearchDescription.setText("");
                        collapse(mSearchDescription);

                        switch (tuning) {
                            case 0:
                                mExamListAdapter.getFilter().filter("UPCOMING_ONLY");
                                break;
                            case 1:
                                mExamListAdapter.getFilter().filter("FINISHED_ONLY");
                                break;
                            default:
                                mExamListAdapter.getFilter().filter("BOTH_FINISHED_AND_UPCOMING");
                                break;
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 6);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 6dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 6);
        v.startAnimation(a);
    }
}
