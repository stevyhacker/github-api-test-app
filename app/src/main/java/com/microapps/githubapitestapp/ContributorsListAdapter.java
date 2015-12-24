package com.microapps.githubapitestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stevyhacker on 24.12.15..
 */
public class ContributorsListAdapter extends ArrayAdapter<String[]> {
    int layoutResID;
    LayoutInflater layoutinflater;
    Context context;
    ArrayList<String[]> contributors;


    public ContributorsListAdapter(Context context, int resource, ArrayList<String[]> contributors) {
        super(context, resource, contributors);
        this.context = context;
        this.contributors = contributors;
        layoutinflater = LayoutInflater.from(context);
        this.layoutResID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {

            viewHolder = new ViewHolder();
            view = layoutinflater.inflate(layoutResID, parent, false);
            viewHolder.itemName = (TextView) view.findViewById(R.id.contributorNameTextView);
            viewHolder.itemContributions = (TextView) view.findViewById(R.id.contributionsTextView);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String[] contributor = contributors.get(position);
        viewHolder.itemName.setText(contributor[0]);
        viewHolder.itemContributions.setText(contributor[1]);

        return view;
    }

    private static class ViewHolder {
        TextView itemName;
        TextView itemContributions;
    }

}
