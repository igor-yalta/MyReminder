package ua.blacksea.myreminder.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import ua.blacksea.myreminder.fragment.CurrentTaskFragment;
import ua.blacksea.myreminder.fragment.DoneTaskFragment;


/**
 * Created by Igor on 07.09.2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;
    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    public TabAdapter(FragmentManager fragmentManager, int number_tabs){
        super(fragmentManager);
        this.number_tabs = number_tabs;

        currentTaskFragment = new CurrentTaskFragment();
        doneTaskFragment = new DoneTaskFragment();
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return currentTaskFragment;
            case 1:
                return doneTaskFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount(){
        return number_tabs;
    }
}
