package com.example.fetchtakehome;

import android.content.Context;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private ActivityScenario<MainActivity> scenario;
    private MainActivity activity;
    private String badFileName = "sdsfeijsdfn";

    private final String goodFileName = "hiring.json";

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity mainActivity) {
                activity = mainActivity;
            }
        });
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fetchtakehome", appContext.getPackageName());
    }

    @Test
    public void testDataLoading() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity mainActivity) {
                ArrayList<Data> jsonDataArray = activity.getJsonDataArray();

                assertNotNull(jsonDataArray);
                assertNotEquals(0, jsonDataArray.size());

                ListView dataListView = activity.findViewById(R.id.dataListView);
                assertNotNull(dataListView.getAdapter());
            }
        });
    }

    @Test
    public void testBadFileName() {
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity mainActivity) {
                ArrayList<Data> jsonData = new JsonParser(badFileName, activity.getApplicationContext()).getDataArrayList();
                assertEquals(0, jsonData.size());
            }
        });
    }

    @Test
    public void testFilteredData(){
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ArrayList<Data> jsonData = new JsonParser(goodFileName, activity.getApplicationContext()).getDataArrayList();
                for(int i = 0; i < jsonData.size(); i++){
                    assertNotEquals("null", jsonData.get(i).getName());
                    assertNotEquals("", jsonData.get(i).getName());
                }
            }
        });
    }
    @Test
    public void testValidData(){
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ArrayList<Data> jsonData = new JsonParser(goodFileName, activity.getApplicationContext()).getDataArrayList();
                for(int i = 0; i < jsonData.size(); i++){
                    assertNotEquals(null, jsonData.get(i).getName());
                    assertTrue(jsonData.get(i).getId() >= 0);
                    assertTrue(jsonData.get(i).getListId() >= 0);
                }
            }
        });
    }
    @Test
    public void testSortedData(){
        scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                ArrayList<Data> jsonData = new JsonParser(goodFileName, activity.getApplicationContext()).getDataArrayList();
                final int jsonDataMin = 0;
                int prevIndex = -1;

                for(int i = 0; i < jsonData.size(); i++){
                    if(prevIndex >= jsonDataMin){
                        assertTrue(jsonData.get(prevIndex).getListId() <= jsonData.get(i).getListId());
                        if(jsonData.get(prevIndex).getListId() == jsonData.get(i).getListId()){
                            int prevNameNum = Integer.parseInt(jsonData.get(prevIndex).getName().split(" ")[1]);
                            int currentNameNum = Integer.parseInt(jsonData.get(i).getName().split(" ")[1]);
                            assertTrue(prevNameNum < currentNameNum);
                        }
                    }
                    prevIndex = i;
                }
            }
        });
    }


}
