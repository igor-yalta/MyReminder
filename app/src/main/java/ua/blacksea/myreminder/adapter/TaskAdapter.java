package ua.blacksea.myreminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.blacksea.myreminder.fragment.TaskFragment;
import ua.blacksea.myreminder.model.Item;
import ua.blacksea.myreminder.model.ModelTask;

/**
 * Created by Igor on 19.09.2016.
 */

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> items;
    TaskFragment taskFragment;

    public TaskAdapter(TaskFragment taskFragment){
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }
    public Item getItem(int pos){
        return items.get(pos);
    }

    public void addItem(Item item){
        items.add(item);
        notifyItemInserted(getItemCount()-1);
    }

    public void addItem(int pos, Item item){
        items.add(pos, item);
        notifyItemInserted(pos);
    }

    public void updateTask(ModelTask newTask){
        for (int i=0;i<getItemCount();i++){
            if(getItem(i).isTask()){
                ModelTask task = (ModelTask) getItem(i);
                if (newTask.getTimeStamp()== task.getTimeStamp()){
                    removeItem(i);
                    getTaskFragment().addTask(newTask, false);
                }
            }
        }
    }

    public void removeItem(int pos){
        if(pos >= 0 && pos <= getItemCount()-1){
            items.remove(pos);
            notifyItemRemoved(pos);}
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(View itemview, TextView title, TextView date, CircleImageView priority){
            super(itemview);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    public TaskFragment getTaskFragment(){
        return taskFragment;
    }
}
