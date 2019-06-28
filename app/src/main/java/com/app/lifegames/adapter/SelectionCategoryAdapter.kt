package com.app.lifegames.adapter

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.lifegames.R
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.CategoriesTable
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.loadImg
import kotlinx.android.synthetic.main.item_categories.view.*

class SelectionCategoryAdapter (private val myDataset: List<CategoriesTable>?,val updateValues: () -> Unit): RecyclerView.Adapter<SelectionCategoryAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.

    private var conext: Context?=null

    inner class MyViewHolder(val textView: ConstraintLayout) : RecyclerView.ViewHolder(textView)
    {



        init {
            textView.setOnClickListener {
                var data=myDataset!!.get(adapterPosition)

                if(myDataset.get(adapterPosition).selectedCat==0)
                {
                    PreferenceClass.setStringPreference(conext, Constants.CATEGORY, data.catValue.toString())
                    itemView.li_category.setBackgroundColor(Color.parseColor(data.catColor))
                    itemView.tv_category.setTextColor(Color.parseColor("#000000"))
                    //itemView.iv_category.backgroundTintMode
                    data.icon?.let { conext?.let { it1 -> itemView.iv_category.loadImg(it, it1) } }
                    itemView.iv_category.setColorFilter(ContextCompat.getColor(conext!!,
                           R.color.blackColor));
                    UpdateALL(conext!!,1,myDataset.get(adapterPosition).catValue,myDataset).execute()


                }
                else
                {
                    itemView.li_category.setBackgroundResource(android.R.color.transparent)
                    itemView.tv_category.setTextColor(Color.parseColor(data.catColor))
                    //itemView.iv_category.backgroundTintMode
                    data.icon?.let { conext?.let { it1 -> itemView.iv_category.loadImg(it, it1) } }
                    itemView.iv_category.setColorFilter(null);
                    UpdateALL(conext!!,0,myDataset.get(adapterPosition).catValue,myDataset).execute()

                }
            }

        }
        fun bind()
        {

            var data=myDataset!!.get(adapterPosition)
            if(data.selectedCat==0)
            {
                itemView.li_category.setBackgroundResource(android.R.color.transparent)
                itemView.tv_category.setTextColor(Color.parseColor(data.catColor))
                //itemView.iv_category.backgroundTintMode
                data.icon?.let { conext?.let { it1 -> itemView.iv_category.loadImg(it, it1) } }
                itemView.iv_category.setColorFilter(null);
            }
            else
            {
                PreferenceClass.setStringPreference(conext, Constants.CATEGORY, data.catValue.toString())
                itemView.li_category.setBackgroundColor(Color.parseColor(data.catColor))
                itemView.tv_category.setTextColor(Color.parseColor("#000000"))
                //itemView.iv_category.backgroundTintMode
                data.icon?.let { conext?.let { it1 -> itemView.iv_category.loadImg(it, it1) } }
                itemView.iv_category.setColorFilter(ContextCompat.getColor(conext!!,
                        R.color.blackColor));

            }
            itemView.tv_category.setText(data.catName)
           /* if(data.catColor==null)
            {
                itemView.tv_category.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else
            {
                itemView.tv_category.setTextColor(Color.parseColor(data.catColor))
            }*/

           data.icon?.let { conext?.let { it1 -> itemView.iv_category.loadImg(it, it1) } }

        }
    }




    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SelectionCategoryAdapter.MyViewHolder {
        conext=parent.context
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categories, parent, false) as ConstraintLayout
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
    inner class UpdateALL// only retain a weak reference to the activity
    internal constructor(val context: Context, val check: Int, val id: String,val myDataset: List<CategoriesTable>) : AsyncTask<Void, Void, Boolean>()
    {


        override fun doInBackground(vararg voids: Void):Boolean
        {



            DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .updateAll(0)


            return true
        }

        override fun onPostExecute(tasks: Boolean)
        {
            super.onPostExecute(tasks)

            if (tasks !!)
            {
                UpdateTask(conext!!,check,id,myDataset).execute()


            }
        }
    }
    //===================Update Categories==============================//
    inner class UpdateTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val check: Int, val id: String,val myDataset: List<CategoriesTable>) : AsyncTask<Void, Void, Boolean>()
    {


        override fun doInBackground(vararg voids: Void):Boolean
        {

            DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .update(check,id)

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
                updateValues()


            }
        }
    }

}