package ua.blacksea.myreminder.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.app.AlertDialog;
import android.view.View;

import ua.blacksea.myreminder.R;
import ua.blacksea.myreminder.MainActivity;
import ua.blacksea.myreminder.adapter.TaskAdapter;
import ua.blacksea.myreminder.model.Item;
import ua.blacksea.myreminder.model.ModelTask;

/**
 * Created by Igor on 19.09.2016.
 */

public abstract class TaskFragment extends Fragment {
    public MainActivity activity;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected TaskAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            activity = (MainActivity) getActivity();
        }
        addTaskFromDB();
    }

    public void updateTask(ModelTask task) {
        adapter.updateTask(task);
    }

    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = adapter.getItem(location);
        if (item.isTask()) {

            ModelTask removingTask = (ModelTask) item;

            final long timeStamp = removingTask.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.removeItem(location);
                    isRemoved[0] = true;
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                            R.string.removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addTask(activity.dbHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            if (isRemoved[0]) {
                               // alarmHelper.removeAlarm(timeStamp);
                                activity.dbHelper.removeTask(timeStamp);

                            }
                        }
                    });

                    snackbar.show();


                    dialog.dismiss();

                }
            });

            dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        }

        dialogBuilder.show();
    }


    public void addTask(ModelTask newTask, boolean saveToDB){
        int pos = -1;

        for(int i = 0; i < adapter.getItemCount(); i++){
            if(adapter.getItem(i).isTask()){
                ModelTask task = (ModelTask) adapter.getItem(i);
                if(newTask.getDate() < task.getDate()){
                    pos = i;
                    break;
                }
            }
        }

        if(pos != -1){
            adapter.addItem(pos, newTask);
        }else {
            adapter.addItem(newTask);
        }

        if(saveToDB){
            activity.dbHelper.saveTask(newTask);
        }
    }
    public abstract void findTasks(String title);
    public abstract void addTaskFromDB();
    public abstract void moveTask(ModelTask task);

}
