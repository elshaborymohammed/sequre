package com.ocs.sequre.app;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ocs.sequre.R;

import java.util.Calendar;

public class CompactDatePicker {
    private final DatePickerDialog picker;

    private CompactDatePicker(@NonNull Context context, @Nullable DatePickerDialog.OnDateSetListener onDateSetListener, long minDate, long maxDate) {
        Calendar calendar = Calendar.getInstance();
        picker = new DatePickerDialog(
                context,
                R.style.App_Widget_DatePicker,
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if (maxDate > 0) {
            picker.getDatePicker().setMaxDate(maxDate);
        }
        if (minDate > 0) {
            picker.getDatePicker().setMinDate(minDate);
        }
        picker.show();
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public DatePickerDialog build() {
        return this.picker;
    }

    public static class Builder {

        private final Context context;
        private DatePickerDialog.OnDateSetListener onDateSetListener;
        private long minDate;
        private long maxDate;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder minDate(long minDate) {
            this.minDate = minDate;
            return this;
        }

        public Builder maxDate(long maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public Builder onDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
            this.onDateSetListener = onDateSetListener;
            return this;
        }

        public DatePickerDialog build() {
            return new CompactDatePicker(context, onDateSetListener, minDate, maxDate).build();
        }
    }
}
