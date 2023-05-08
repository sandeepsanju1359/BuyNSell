package com.example.buynsellapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*


class Adapter(private val itemListFull: List<Data>) :
    RecyclerView.Adapter<Adapter.ViewHolder>(),Filterable {
    private var itemList = itemListFull
    private var iuri: Uri ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ResultPage::class.java).apply {
                putExtra("img", Uri.parse(item.img))
                putExtra("name", item.name)
                putExtra("price", item.price)
                putExtra("contact",item.phone)
                putExtra("des", item.des)

            }
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = itemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.lvImg)
        private val nameTextView: TextView = itemView.findViewById(R.id.lvName)
        private val priceTextView: TextView = itemView.findViewById(R.id.lvPrice)
        private val phoneTextView: TextView = itemView.findViewById(R.id.lvPhone)
        val buy: Button = itemView.findViewById(R.id.buy)

        fun bind(item: Data) {
            nameTextView.text = item.name
            priceTextView.text = item.price
            phoneTextView.text = item.des

            Glide.with(itemView.context).load(item.img).into(imageView)

            buy.setOnClickListener {
                val context = itemView.context
                val options = arrayOf<CharSequence>("Make a call", "Send a message")
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Choose an option")
                builder.setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:${item.phone}")
                            context.startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(Intent.ACTION_SENDTO)
                            val message = "Hi, I am interested in buying your ${item.name}. I want to know more about the product." +
                                    " Please let me know if it is still available."
                            intent.data = Uri.parse("smsto:${item.phone}")
                            intent.putExtra("sms_body",message)
                            context.startActivity(intent)
                        }
                    }
                }
                builder.show()
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Data>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(itemListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    for (item in itemListFull) {
                        if (item.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemList = results?.values as List<Data>
                notifyDataSetChanged()
            }
        }
    }
}






