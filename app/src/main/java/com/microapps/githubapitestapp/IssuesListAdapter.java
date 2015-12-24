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
public class IssuesListAdapter extends ArrayAdapter<String[]> {
    int layoutResID;
    LayoutInflater layoutinflater;
    Context context;
    ArrayList<String[]> issues;


    public IssuesListAdapter(Context context, int resource, ArrayList<String[]> issues) {
        super(context, resource, issues);
        this.context = context;
        this.issues = issues;
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
            viewHolder.itemName = (TextView) view.findViewById(R.id.issuesTitleTextView);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String[] issue = issues.get(position);
        viewHolder.itemName.setText(issue[0]);

        return view;
    }

    private static class ViewHolder {
        TextView itemName;
    }

}
