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
import com.app.lifegames.database.Entities.FavouriteActivitiesTable
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass

import com.app.lifegames.utils.loadImg

class FavouriteActivitiesListAdapter (val openFragment: (String) -> Unit,
                                      val activitiesList: List<FavouriteActivitiesTable>?
                                      ,val addToPlanner: (String) -> Unit,val removefromFav: (String) -> Unit) : RecyclerView.Adapter<FavouriteActivitiesListAdapter.SectionViewHolder>() {
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
        holder.itemView.setOnClickListener {
            openFragment(data.actID!!)
        }
        holder.planner.setOnClickListener {
            addToPlanner(data.actID!!)
        }
        holder.fav.setOnClickListener {
            removefromFav(data.actID!!+","+position)
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
                .inflate(R.layout.item_favourite_activity, parent, false)
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
            tv_activity_title= itemView.findViewById<View>(R.id.tv_fav_activity_title) as TextView
            clickView = itemView.findViewById<View>(R.id.li_fav_click) as LinearLayout
            play= itemView.findViewById<View>(R.id.li_fav_play) as LinearLayout
            playBtn=itemView.findViewById<View>(R.id.tv_fav_play_btn) as TextView
            fav= itemView.findViewById<View>(R.id.li_remove_fav) as LinearLayout
            planner= itemView.findViewById<View>(R.id.li_fav_planner) as LinearLayout
            detail=itemView.findViewById<View>(R.id.tv_brief_detail_fav_activity) as TextView
            iconImage=itemView.findViewById<View>(R.id.iv_fav_category_icon)as ImageView
            ratingBar=itemView.findViewById<View>(R.id.tv_fav_rate_activity)as RatingBar


        }
    }


}
