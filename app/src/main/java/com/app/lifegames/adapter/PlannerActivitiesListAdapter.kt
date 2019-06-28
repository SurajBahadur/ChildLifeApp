package com.app.lifegames.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.app.lifegames.R
import com.app.lifegames.database.Entities.PlannerActivitiesTable

import com.app.lifegames.utils.loadImg

class PlannerActivitiesListAdapter(val openFragment: (String) -> Unit, val activitiesList: List<PlannerActivitiesTable>, val editPlanerActivity: (PlannerActivitiesTable) -> Unit,val removePlanerActivity: (String) -> Unit) : RecyclerView.Adapter<PlannerActivitiesListAdapter.SectionViewHolder>() {
    private var conext: Context?=null

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {

        val data= activitiesList.get(position)
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
        holder.tv_set_for_time.setText(data.schedule.toString())
        // holder.compaintText.setOnClickListener(clickListener)

        holder.tv_activity_title.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.playBtn.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.itemView.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.edtLay.setOnClickListener {
        //    PreferenceClass.setStringPreference(context,Constants.ACTIVIY_ID,data.actID!!)
            editPlanerActivity(data)
        }

        holder.deleteLay.setOnClickListener {
            removePlanerActivity(data.actID!!)
        }
        holder.clickView.setOnClickListener {
            holder.detail.setMaxLines(Integer.MAX_VALUE);
            /*val readMoreView = ReadMoreView.Builder()
                    .textLength(8, TextLengthType.TYPE_LINE) //OR
//.textLength(300, ReadMoreView.TYPE_CHARACTER)
                    .labelUnderLine(true)
                    .expandAnimation(true)
                    .build()
            readMoreView.addReadMoreTo(  holder.detail, data!!.overview)*/

            holder.play.visibility= View.VISIBLE
            holder.fav.visibility= View.VISIBLE
          //  holder.planner.visibility= View.VISIBLE
            notifyDataSetChanged()
            holder.clickView.visibility= View.GONE
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        var v: View? = null
        this.context = parent.context
        v = LayoutInflater.from(context)
                .inflate(R.layout.item_planner_activities, parent, false)
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
        internal  val tv_set_for_time:TextView
        internal val  edtLay:LinearLayout
        internal val  deleteLay:LinearLayout

        init {

            mainLay = itemView.findViewById<View>(R.id.main_lay) as ConstraintLayout
            tv_activity_title= itemView.findViewById<View>(R.id.tv_planner_activity_title) as TextView
            clickView = itemView.findViewById<View>(R.id.li_planner_click) as LinearLayout
            play= itemView.findViewById<View>(R.id.li_planner_play) as LinearLayout
            playBtn=itemView.findViewById<View>(R.id.tv_planner_play_btn) as TextView
            fav= itemView.findViewById<View>(R.id.li_planner_fav) as LinearLayout
            planner= itemView.findViewById<View>(R.id.li_planner_planner) as LinearLayout
            detail=itemView.findViewById<View>(R.id.tv_brief_detail_planner_activity) as TextView
            iconImage=itemView.findViewById<View>(R.id.iv_planner_category_icon)as ImageView
            ratingBar=itemView.findViewById<View>(R.id.tv_planner_rate_activity)as RatingBar
            tv_set_for_time=itemView.findViewById<View>(R.id.tv_set_for_time)as TextView
            edtLay=itemView.findViewById<View>(R.id.li_edit)as LinearLayout
            deleteLay=itemView.findViewById<View>(R.id.li_cacel)as LinearLayout


        }
    }


}
