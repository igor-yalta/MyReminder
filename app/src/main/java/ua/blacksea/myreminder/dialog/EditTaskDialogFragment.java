package ua.blacksea.myreminder.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import ua.blacksea.myreminder.R;
import ua.blacksea.myreminder.Utils;
import ua.blacksea.myreminder.alarm.AlarmHelper;
import ua.blacksea.myreminder.model.ModelTask;

import static android.R.id.mask;

/**
 * Created by Игорь on 12/18/2016.
 */

public class EditTaskDialogFragment extends DialogFragment{
    public static EditTaskDialogFragment newInstance(ModelTask task){
        EditTaskDialogFragment editTaskDialogFragment = new EditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString("title",task.getTitle());
        args.putLong("date", task.getDate());
        args.putLong("time_stamp", task.getTimeStamp());
        args.putInt("priority", task.getPriority());

        editTaskDialogFragment.setArguments(args);
        return editTaskDialogFragment;
    }

    private EditTaskListener editTaskListener;
    public interface EditTaskListener {
        void onTaskEdit(ModelTask updatedTask);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            editTaskListener = (EditTaskListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement EditTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        String title = args.getString("title");
        Long date = args.getLong("date", 0);
        Long timeStamp = args.getLong("time_stamp", 0);
        int priority = args.getInt("priority", 0);
        final ModelTask task = new ModelTask(title, date, priority, 0, timeStamp);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_edit_title);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View container = layoutInflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tTitle = (TextInputLayout) container.findViewById(R.id.dTaskTitle);
        final EditText edTitle = tTitle.getEditText();
        edTitle.setHint(getResources().getString(R.string.task_title));

        TextInputLayout tDate = (TextInputLayout) container.findViewById(R.id.dTaskDate);
        final EditText edDate = tDate.getEditText();
        edDate.setHint(getResources().getString(R.string.task_date));

        final TextInputLayout tTime = (TextInputLayout) container.findViewById(R.id.dTaskTime);
        final EditText edTime = tTime.getEditText();
        edTime.setHint(getResources().getString(R.string.task_time));

        Spinner sPriority = (Spinner) container.findViewById(R.id.spDialogTaskPriority);

        edTitle.setText(task.getTitle());
        edTitle.setSelection(edTitle.length());
        if(task.getDate() != 0){
            edDate.setText(Utils.getTime(task.getDate()));
            edDate.setText(Utils.getDate(task.getDate()));
        }

        builder.setView(container);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, ModelTask.PRIORITY_LEVELS);

        sPriority.setAdapter(priorityAdapter);
        sPriority.setSelection(task.getPriority());

        sPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.setPriority(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
        if(edDate.length() != 0 || edTime.length() != 0){
            calendar.setTimeInMillis(task.getDate());
        }

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edDate.length() == 0) {
                    edDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        edDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTime.length() == 0) {
                    edTime.setText(" ");
                }

                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        calendar.set(Calendar.SECOND, 0);
                        edTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });
        builder.setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                task.setTitle(edTitle.getText().toString());
                task.setStatus(ModelTask.STATUS_CURRENT);
                if (edDate.length() != 0 || edTime.length() != 0) {
                    task.setDate(calendar.getTimeInMillis());

                    AlarmHelper alarmHelper = AlarmHelper.getInstance();
                    alarmHelper.setAlarm(task);
                }
                task.setStatus(task.STATUS_CURRENT);
                editTaskListener.onTaskEdit(task);
                dialogInterface.dismiss();

            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                if (edTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tTitle.setError(getResources().getString(R.string.dialog_eror_empty_title));
                }
                edTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
                            positiveButton.setEnabled(false);
                            tTitle.setError(getResources().getString(R.string.dialog_eror_empty_title));
                        } else {
                            positiveButton.setEnabled(true);
                            tTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
