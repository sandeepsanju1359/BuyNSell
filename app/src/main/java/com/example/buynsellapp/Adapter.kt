package com.example.buynsellapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class Adapter(var ctx : Context, var res : Int, var items : List<Data>) : ArrayAdapter<Data>(ctx,res,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view : View =layoutInflater.inflate(res,null)

        val imgView : ImageView =view.findViewById(R.id.lvImg)
        val nameTxt : TextView =view.findViewById(R.id.lvName)
        val priceTxt : TextView =view.findViewById(R.id.lvPrice)
        val phoneTxt : TextView =view.findViewById(R.id.lvPhone)

        val mItem : Data = items[position]
        imgView.setImageDrawable(ctx.resources.getDrawable(mItem.img))
        nameTxt.text = mItem.name
        priceTxt.text =mItem.price
        phoneTxt.text =mItem.phone



        return view
    }
}