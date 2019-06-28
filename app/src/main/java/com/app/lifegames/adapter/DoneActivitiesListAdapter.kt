package com.app.lifegames.adapter

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.app.lifegames.R
import com.app.lifegames.database.Entities.DoneActivitiesTable
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.loadImg

class DoneActivitiesListAdapter (val openFragment: (String) -> Unit,
                                 val activitiesList: List<DoneActivitiesTable>, val addAsFav: (String) -> Unit,val addToPlanner: (String) -> Unit) : RecyclerView.Adapter<DoneActivitiesListAdapter.SectionViewHolder>() {
    private var conext: Context?=null

    override fun getItemCount(): Int {
        return activitiesList!!.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {

        val data= activitiesList!!.get(position)
        var detail=data.overview.toString()
        // String to ByteArray
        val byteArray = detail.toByteArray(Charsets.UTF_8)
        // ByteArray to String
        val output = String(byteArray, Charsets.UTF_8)


        holder.detail.text=output


        holder.tv_activity_title.text=data?.title
        holder.ratingBar.rating=data.ratings!!.toFloat()
        Log.d("Click"," "+data.cat_icon);
        data.cat_icon?.let {  this.context?.let { it1 -> holder.iconImage.loadImg(it, it1) } }
        // holder.compaintText.setOnClickListener(clickListener)
        holder.tv_activity_title.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.playBtn.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.planner.setOnClickListener {
            addToPlanner(data.actID!!)
        }
        holder.itemView.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.fav.setOnClickListener {
            if (data.favourite.equals("1")) {
                Toast.makeText(context,"Already added to favourite list.", Toast.LENGTH_SHORT).show()

            } else
            {
                addAsFav(data.actID!!)
            }

            holder.fav.setBackgroundColor(Color.parseColor("#D8D8D8"))
        }

        holder.clickView.setOnClickListener {
            holder.detail.setMaxLines(Integer.MAX_VALUE);
           /* val readMoreView = ReadMoreView.Builder()
                    .textLength(8, TextLengthType.TYPE_LINE) //OR
//.textLength(300, ReadMoreView.TYPE_CHARACTER)
                    .labelUnderLine(true)
                    .expandAnimation(true)
                    .build()
            readMoreView.addReadMoreTo(  holder.detail, data!!.overview)*/
            val userType: String? = PreferenceClass.getStringPreferences(context, Constants.USER_TYPE)
            holder.play.visibility= View.VISIBLE
            holder.fav.visibility= View.VISIBLE
            if (userType.equals("0")) {
                holder.planner.visibility=View.GONE
            }
            else
            {
                holder.planner.visibility=View.VISIBLE
            }
            notifyDataSetChanged()
            holder.clickView.visibility= View.GONE
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        var v: View? = null
        this.context = parent.context
        v = LayoutInflater.from(context)
                .inflate(R.layout.item_done_activities, parent, false)
        return SectionViewHolder(v)
    }


    var context: Context?=null


    // SectionViewHolder Class for Sections
    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val mainLay: ConstraintLayout
        internal val clickView: LinearLayout
        internal val play: LinearLayout
        internal val playBtn: TextView
        internal val fav: LinearLayout
        internal val planner: LinearLayout
        internal val tv_activity_title: TextView
        internal  val detail: TextView
        internal  val iconImage: ImageView
        internal  val ratingBar: RatingBar

        init {

            mainLay = itemView.findViewById<View>(R.id.main_lay) as ConstraintLayout
            tv_activity_title= itemView.findViewById<View>(R.id.tv_done_activity_title) as TextView
            clickView = itemView.findViewById<View>(R.id.li_done_click) as LinearLayout
            play= itemView.findViewById<View>(R.id.li_done_play) as LinearLayout
            playBtn=itemView.findViewById<View>(R.id.tv_done_play_btn) as TextView
            fav= itemView.findViewById<View>(R.id.li_done_fav) as LinearLayout
            planner= itemView.findViewById<View>(R.id.li_done_planner) as LinearLayout
            detail=itemView.findViewById<View>(R.id.tv_brief_detail_done_activity) as TextView
            iconImage=itemView.findViewById<View>(R.id.iv_done_category_icon)as ImageView
            ratingBar=itemView.findViewById<View>(R.id.tv_done_rate_activity)as RatingBar


        }
    }


}
