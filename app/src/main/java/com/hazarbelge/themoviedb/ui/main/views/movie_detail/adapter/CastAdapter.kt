package com.hazarbelge.themoviedb.ui.main.views.movie_detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hazarbelge.themoviedb.R
import com.hazarbelge.themoviedb.common.ItemClickListener
import com.hazarbelge.themoviedb.network.model.Actor

class CastAdapter(
    private var callback: ItemClickListener<Actor>,
    private val actorList: List<Actor>,
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastAdapter.CastViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.actor_items, null)
        return CastViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    override fun onBindViewHolder(viewHolder: CastViewHolder, position: Int) {
        val actor: Actor = actorList[position]

        viewHolder.tvActorCharacter.apply {
            text = actor.character
        }
        viewHolder.tvActorName.apply {
            text = actor.name
        }

        viewHolder.profilePath.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(context.getString(R.string.w220h330) + actor.profile_path)
        }

        viewHolder.cardViewActor.setOnClickListener {
            callback.onItemClicked(
                viewHolder.cardViewActor,
                actor,
            )
        }
    }


    inner class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardViewActor: CardView = itemView.findViewById(R.id.cardViewActor)
        val profilePath: ImageView = itemView.findViewById(R.id.profile_path)
        val tvActorCharacter: TextView = itemView.findViewById(R.id.character)
        val tvActorName: TextView = itemView.findViewById(R.id.name)
    }
}
