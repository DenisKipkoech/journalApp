package com.example.denis.journalapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denis.journalapp.Database.JournalEntry;
import com.example.denis.journalapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by denis on 29/06/18.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder>{
    private List<JournalEntry> mjournalEntries;
    final private ItemClickListener mItemclickListener;
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public JournalAdapter(Context context, ItemClickListener mItemclickListener) {
        this.mItemclickListener = mItemclickListener;
        this.context = context;
    }

    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                    .inflate(R.layout.journal_list_layout, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        JournalEntry journalEntry = mjournalEntries.get(position);
        String journalDesc = journalEntry.getThoughts();
        String journalDate = dateFormat.format(journalEntry.getDateOfEntry());

        holder.journalView.setText(journalDesc);
        holder.dateView.setText(journalDate);


    }

    @Override
    public int getItemCount() {
        if (mjournalEntries != null){
            return mjournalEntries.size();
        }
        return 0;
    }

    public List<JournalEntry> getMjournalEntries(){
        return mjournalEntries;
    }

    public void setJournals(List<JournalEntry> journalEntries){
        mjournalEntries = journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView dateView, journalView;
        public JournalViewHolder(View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.tv_date);
            journalView = itemView.findViewById(R.id.tv_journal_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mjournalEntries.get(getAdapterPosition()).getId();
            mItemclickListener.onItemClickListener(elementId);
        }
    }
}
