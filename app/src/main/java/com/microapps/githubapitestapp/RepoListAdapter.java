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
public class RepoListAdapter extends ArrayAdapter<RepoItem> {
    int layoutResID;
    LayoutInflater layoutinflater;
    Context context;
    ArrayList<RepoItem> repoItems;


    public RepoListAdapter(Context context, int resource, ArrayList<RepoItem> repoItems) {
        super(context, resource, repoItems);
        this.context = context;
        this.repoItems = repoItems;
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
            viewHolder.itemName = (TextView) view.findViewById(R.id.repoNameTextView);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RepoItem repoItem = repoItems.get(position);
        viewHolder.itemName.setText(repoItem.name);

        return view;
    }

    private static class ViewHolder {
        TextView itemName;
    }

}
