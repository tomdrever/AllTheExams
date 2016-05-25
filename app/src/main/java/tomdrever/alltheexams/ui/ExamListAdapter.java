package tomdrever.alltheexams.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tomdrever.alltheexams.R;
import tomdrever.alltheexams.data.Exam;
import tomdrever.alltheexams.databinding.ExamItemBinding;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamViewHolder> implements Filterable {

    private List<Exam> allExams;
    private List<Exam> displayExams;
    private ExamFilter examFilter;

    public ExamListAdapter(List<Exam> exams) {
        this.allExams = exams;
        this.displayExams = exams;
    }

    public void clear() {
        allExams = new ArrayList<>();
        displayExams = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setAllExams(List<Exam> exams) {
        this.allExams = exams;
        this.displayExams = exams;
        notifyDataSetChanged();
    }

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamViewHolder examViewHolder, int position) {
        Exam exam = displayExams.get(position);

        examViewHolder.nameTextView.setText(exam.getName());
        examViewHolder.detailsTextView.setText(exam.getDetails());
        examViewHolder.dateTextView.setText(exam.getDateString());
        examViewHolder.timeTextView.setText(exam.getTimeString());
        examViewHolder.durationTextView.setText(exam.getDurationString());
        examViewHolder.timeLeftTextView.setText(exam.getDaysLeftString());

        examViewHolder.getExamItemBinding().setIsExamDone(exam.getIsExamDone());
        examViewHolder.getExamItemBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return displayExams.size();
    }

    @Override
    public Filter getFilter() {
        if (examFilter == null)
            examFilter = new ExamFilter();
        return examFilter;
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView detailsTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView durationTextView;
        private TextView timeLeftTextView;

        private ExamItemBinding examItemBinding;

        public ExamViewHolder(View view) {
            super(view);

            nameTextView = (TextView) view.findViewById(R.id.exam_item_name);
            detailsTextView = (TextView) view.findViewById(R.id.exam_item_details);
            dateTextView = (TextView) view.findViewById(R.id.exam_item_date);
            timeTextView = (TextView) view.findViewById(R.id.exam_item_time);
            durationTextView = (TextView) view.findViewById(R.id.exam_item_duration);
            timeLeftTextView = (TextView) view.findViewById(R.id.exam_item_time_until);

            examItemBinding = DataBindingUtil.bind(view);
        }

        public ExamItemBinding getExamItemBinding() {
            return examItemBinding;
        }
    }

    public class ExamFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter - return all exams
                results.values = allExams;
                results.count = allExams.size();
            }
            else {

                // Filter
                List<Exam> nExamList = new ArrayList<>();

                for (Exam exam : allExams) {
                    // Check against name
                    if (exam.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                        nExamList.add(exam);

                    // Check against date
                    if (exam.getDateString().toLowerCase().contains(constraint.toString().toLowerCase()))
                        nExamList.add(exam);

                    // Check against details
                    if (exam.getDetails().toLowerCase().contains(constraint.toString().toLowerCase()))
                        nExamList.add(exam);

                    // Check "FINISHED_ONLY", "UPCOMING_ONLY" and "BOTH_FINISHED_AND_UPCOMING" requests
                    switch (constraint.toString()){
                        case "FINISHED_ONLY":
                            if (exam.getIsExamDone())
                                nExamList.add(exam);
                            break;
                        case "UPCOMING_ONLY":
                            if (!exam.getIsExamDone())
                                nExamList.add(exam);
                            break;
                        case "BOTH_FINISHED_AND_UPCOMING":
                            nExamList.add(exam);
                            break;
                    }
                }

                results.values = nExamList;
                results.count = nExamList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered

            displayExams = (List<Exam>) results.values;
            notifyDataSetChanged();
        }
    }
}
