package com.example.dfpreport;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private List<Profile> profiles;
    private final LayoutInflater inflater;
    private final SetProfileHandler profileHandler;
    private final Context mContext;
    int selectedProfile = -1;
    int item;



    static class ProfileViewHolder extends RecyclerView.ViewHolder {

        private final Button profileItemView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profileItemView = itemView.findViewById(R.id.profileItem);
        }
    }

    public ProfileAdapter(Context context, SetProfileHandler profileHandler) {
        inflater = LayoutInflater.from(context);
        this.profileHandler = profileHandler;
        mContext = context;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View profileLayoutView = inflater
                .inflate(R.layout.profile_recyclerview_item, parent, false);
        return new ProfileViewHolder(profileLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        if (selectedProfile==position){
            holder.profileItemView.setBackgroundColor(Color.parseColor("#ffff00"));
        }
        else{
            holder.profileItemView.setBackgroundColor(Color.parseColor("#44bcda"));
        }

        if (profiles != null) {
            Profile current = profiles.get(position);

            holder.profileItemView.setText(current.getFullName());
            holder.profileItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedProfile = position;

                    notifyDataSetChanged();

                    profileHandler.setCurrentProfile(current);
                }
            });
        }
    }




    @Override
    public int getItemCount() {
        if (profiles != null) {
            return profiles.size();
        } else return 0;
    }

    void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
        profileHandler.noProfileCheck(profiles == null || profiles.size() == 0);
        notifyDataSetChanged();
    }
}
