package ua.blacksea.myreminder.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.blacksea.myreminder.R;
import ua.blacksea.myreminder.adapter.CurrentTaskAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {

    /*private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CurrentTaskAdapter currentTaskAdapter;*/

    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        currentTaskAdapter = new CurrentTaskAdapter(this);
        recyclerView.setAdapter(currentTaskAdapter);

        return rootView;
    }

    /*public void addTask(ModelTask newTask){
        int pos = -1;

        for(int i = 0; i < currentTaskAdapter.getItemCount(); i++){
            if(currentTaskAdapter.getItem(i).isTask()){
                ModelTask task = (ModelTask) currentTaskAdapter.getItem(i);
                if(newTask.getDate() < task.getDate()){
                    pos = i;
                    break;
                }
            }
        }
        if(pos != -1){
            currentTaskAdapter.addItem(pos, newTask);
        }else {
            currentTaskAdapter.addItem(newTask);
        }
    }*/

}
