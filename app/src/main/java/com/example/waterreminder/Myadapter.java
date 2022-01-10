package com.example.waterreminder;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    Context context;
    RealmResults<timekeeper> timelist;

    public Myadapter(Context context, RealmResults<timekeeper> timelist) {
        this.context = context;
        this.timelist = timelist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.timedispaly, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        timekeeper timekeeper1 = timelist.get(position);
        holder.time.setText(timekeeper1.getTime());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("Delete");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){

                            String[] convert_time = timekeeper1.time.split(":");
                            String[] min_convert = convert_time[1].split(" ");
                            int hours = Integer.parseInt(convert_time[0]);
                            int min = Integer.parseInt(min_convert[0]);

                            Intent intent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                            intent.putExtra(AlarmClock.EXTRA_HOUR,hours);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES,min);



                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            timekeeper1.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Reminder Deleted",Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return timelist.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.recycle_time);
        }
    }
}
