package com.example.sweepstakeapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.sweepstakeapp.R
import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawEntity
import com.squareup.picasso.Picasso

class DrawAdapter(private val context : Activity, private val list : MutableList<DrawEntity>) : ArrayAdapter<DrawEntity>(context,
    R.layout.list_item,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rootView = context.layoutInflater.inflate(R.layout.list_item,null,true)

        val sweepstake = list.get(position)

        val drawImage = rootView.findViewById<ImageView>(R.id.drawItemImage)
        val drawTitle = rootView.findViewById<TextView>(R.id.drawItemTitle)
        val drawDeadline = rootView.findViewById<TextView>(R.id.drawItemDeadline)
        val drawPrize = rootView.findViewById<TextView>(R.id.drawItemMoney)
        val drawCond = rootView.findViewById<TextView>(R.id.drawItemCondition)

        drawTitle.text = sweepstake.name
        drawDeadline.text = sweepstake.deadline
        drawPrize.text = sweepstake.totalPrizeCount
        drawCond.text = sweepstake.condition
        Picasso.get().load(sweepstake.imgURL).into(drawImage)

        return rootView
    }
}