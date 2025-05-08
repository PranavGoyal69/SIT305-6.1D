package com.example.personalizedlearningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;

import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

    private List<String> interests;
    private List<String> selectedInterests;

    public InterestsAdapter(List<String> interests, List<String> selectedInterests) {
        this.interests = interests;
        this.selectedInterests = selectedInterests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String interest = interests.get(position);
        holder.cbInterest.setText(interest);
        holder.cbInterest.setChecked(selectedInterests.contains(interest));

        holder.cbInterest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !selectedInterests.contains(interest)) {
                selectedInterests.add(interest);
            } else if (!isChecked) {
                selectedInterests.remove(interest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbInterest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbInterest = itemView.findViewById(R.id.cbInterest);
        }
    }
}