package com.app.lifegames.adapter

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.lifegames.R
import com.app.lifegames.database.Entities.PlannerActivitiesTable
import com.app.lifegames.database.Entities.ScheduleDatesTable
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import kotlinx.android.synthetic.main.item_schedule_date.view.*

class EditScheduleDateListAdapter (private val myDataset: List<ScheduleDatesTable>?, val date: (String) -> Unit,
                                   val updateValues: (String) -> Unit, val list: PlannerActivitiesTable, var edit: Boolean): RecyclerView.Adapter<EditScheduleDateListAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.

    private var conext: Context?=null

    var check:Boolean=false
    var pos:Int=0

    inner class MyViewHolder(val textView: ConstraintLayout) : RecyclerView.ViewHolder(textView)
    {

        fun bind()
        {
            var data=myDataset!!.get(adapterPosition)


                var str=list.schedule.split(",")

            if(edit==true)
            {
                if(str[1].toString().equals(data.date.toString()))
                {

                    itemView.tv_date.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    itemView.tv_date.setTextColor(Color.parseColor("#000000"))
                }else
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))
                    itemView.tv_date.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
            else
            {
                check=PreferenceClass.getBooleanPreferences(conext,Constants.DATE_CHECK);
                pos=PreferenceClass.getIntegerPreferences(conext,Constants.DATE_POS)


                if(check==false && adapterPosition==pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    itemView.tv_date.setTextColor(Color.parseColor("#000000"))
                    check=true
                }
                else if(check==false && adapterPosition!=pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))
                    itemView.tv_date.setTextColor(Color.parseColor("#FFFFFF"))
                    check=true

                }
                else if(check!=false && adapterPosition==pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    itemView.tv_date.setTextColor(Color.parseColor("#000000"))
                    check=false
                }
                else
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))
                    itemView.tv_date.setTextColor(Color.parseColor("#FFFFFF"))
                    check=false
                }

            }




            itemView.tv_date.setText(data.date.toString())
            itemView.tv_date.setOnClickListener {
                edit=false
                if(check==false && adapterPosition==pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    check=true
                    pos=adapterPosition;
                }
                else if(check==false && adapterPosition!=pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))
                    check=true
                    pos=adapterPosition;
                }

                else if(check!=false && adapterPosition!=pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    check=false
                    pos=adapterPosition;
                }
                else  if(check!=false && adapterPosition==pos)
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))
                    check=false
                    pos=adapterPosition;
                }
                else
                {
                    itemView.tv_date.setBackgroundColor(Color.parseColor("#000000"))

                    check=false
                }
                pos=adapterPosition;

                PreferenceClass.setBooleanPreference(conext, Constants.DATE_CHECK,check)
                PreferenceClass.setIntegerPreference(conext, Constants.DATE_POS,adapterPosition)

                notifyDataSetChanged()
                date(data.date.toString()+","+data.date_new.toString())
            }
        }
    }




    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EditScheduleDateListAdapter.MyViewHolder {
        conext=parent.context
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_schedule_date, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        // holder.textView.textView48.text= myDataset.get(position).name

        holder.bind()


        /*  holder.textView.setOnClickListener {

              myDataset.get(position).selected = !myDataset.get(position).selected!!
              if(myDataset.get(position).selected!!)
              {
                  holder.textView.selection.visibility=View.VISIBLE
              }
              else
              {
                  holder.textView.selection.visibility=View.GONE
              }
          }
  */
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset!!.size


    //===================Update Categories==============================//
    /*inner class UpdateALL// only retain a weak reference to the activity
    internal constructor(val context: Context, val check: String, val id: String) : AsyncTask<Void, Void, Boolean>()
    {


        override fun doInBackground(vararg voids: Void):Boolean
        {
            DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getPartDate(id,"0")


            return true
        }

        override fun onPostExecute(tasks: Boolean)
        {
            super.onPostExecute(tasks)

            if (tasks !!)
            {
                UpdateTask(conext!!,check,id).execute()


            }
        }
    }
    //===================Update Categories==============================//
    inner class UpdateTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val check: String, val id: String) : AsyncTask<Void, Void, Boolean>()
    {


        override fun doInBackground(vararg voids: Void):Boolean
        {

            DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getPartDate(id,check)



            Log.e("Updatef"," "+ DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getAll())
            return true
        }

        override fun onPostExecute(tasks: Boolean)
        {
            super.onPostExecute(tasks)

            if (tasks !!)
            {
                updateValues(actvityID!!)
            }
        }
    }
*/
}