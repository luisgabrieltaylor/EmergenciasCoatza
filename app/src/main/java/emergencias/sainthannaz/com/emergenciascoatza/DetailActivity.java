/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emergencias.sainthannaz.com.emergenciascoatza;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.model.SubNumbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    public static final String PHONE = "number_phone";
    public String url;
    public Integer id;
    public String title, description, picture, category, update;
    TextView descriptionTxt, updateTxt, categoryTxt;
    private RecyclerView recyclerViewSubData;
    DatabaseHandler db;
    private SubNumbersAdapter mSubAdapter;
    private List<SubNumbers> SubNumberList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        db = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            title = extras.getString("title");
            description = extras.getString("description");
            picture = extras.getString("picture");
            category = extras.getString("category");
            update = extras.getString("update");
        } else {
            System.out.println("¡No pasaron datos o no existen!");
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        descriptionTxt = (TextView) findViewById(R.id.description);
        descriptionTxt.setFocusable(true);
        descriptionTxt.requestFocus();
        categoryTxt = (TextView) findViewById(R.id.category);
        updateTxt = (TextView) findViewById(R.id.update);
        mSubAdapter = new SubNumbersAdapter(SubNumberList, getApplication());
        recyclerViewSubData = (RecyclerView) findViewById(R.id.recycler_view_subdata);
        recyclerViewSubData.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSubData.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewSubData.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSubData.setAdapter(mSubAdapter);


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);

        loadData();
        loadSubdata();
        loadBackdrop();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadData(){
        updateTxt.setText(update);
        descriptionTxt.setText(description);
        categoryTxt.setText(category);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this)
                .load(picture)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    private void loadSubdata(){
        List<SubNumbers> subNumbers = db.getAllSpecificSubData(id);
        mSubAdapter = new SubNumbersAdapter(subNumbers, getApplicationContext());
        recyclerViewSubData.setAdapter(mSubAdapter);
        System.out.println(subNumbers);
    }

}
