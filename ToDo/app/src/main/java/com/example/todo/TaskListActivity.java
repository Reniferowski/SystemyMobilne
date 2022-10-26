package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class TaskListActivity extends SingleFragmentActivity {

    public Fragment createFragment(){
        return new TaskListFragment();
    }

}