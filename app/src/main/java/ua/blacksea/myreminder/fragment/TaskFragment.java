package ua.blacksea.myreminder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import ua.blacksea.myreminder.MainActivity;
import ua.blacksea.myreminder.adapter.CurrentTaskAdapter;
import ua.blacksea.myreminder.model.ModelTask;

/**
 * Created by Igor on 19.09.2016.
 */

public abstract class TaskFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected CurrentTaskAdapter currentTaskAdapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            activity = (MainActivity) getActivity();
        }
        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB){
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
        if(saveToDB){
            activity.dbHelper.saveTask(newTask);
        }
    }
    public abstract void addTaskFromDB();
    public abstract void moveTask(ModelTask task);

}
