package com.app.coinmarket.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.coinmarket.R
import com.app.coinmarket.model.CryptoListData
import com.squareup.picasso.Picasso


class ListAdapter(
    private var context: Context,
    list: ArrayList<CryptoListData?>,
    map: Map<String, String>,
    clickCallback: ItemClickCallback
) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var list: List<CryptoListData?>
    private var map: Map<String, String>
    private var clickCallback: ItemClickCallback

    init {
        this.list = list
        this.map = map
        this.clickCallback = clickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model: CryptoListData = list[position]!!
        val color: Int
        val s: String
        if (model.quote?.USD?.volume_change_24h!! > 0) {
            color = R.color.green
            s = "+"
        } else {
            color = R.color.red
            s = ""
        }
        holder.tvVolumeChange.text =
            s + java.lang.String.format("%.2f", model.quote!!.USD?.volume_change_24h ?: 0) + ""
        holder.ivGraph.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, color))
        holder.tvVolumeChange.setTextColor(ContextCompat.getColor(context, color))
        holder.tvName.setText(model.name)
        holder.tvSymbol.setText(model.symbol)
        holder.tvPrice.text = "$ " + java.lang.String.format("%.2f", model.quote!!.USD!!.price) + " USD"
        Picasso.get().load(map[java.lang.String.valueOf(model.id)])
            .placeholder(R.drawable.baseline_image_24).error(R.drawable.baseline_image_24)
            .into(holder.ivLogo)
        holder.itemView.setOnClickListener { view: View? ->
            clickCallback.onClick(
                model
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSymbol: TextView
        var tvName: TextView
        var tvPrice: TextView
        var tvVolumeChange: TextView
        var ivGraph: ImageView
        var ivLogo: ImageView

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvSymbol = itemView.findViewById(R.id.tv_symbol)
            tvPrice = itemView.findViewById(R.id.tv_price)
            tvVolumeChange = itemView.findViewById(R.id.tv_volume_change)
            ivGraph = itemView.findViewById(R.id.iv_graph)
            ivLogo = itemView.findViewById(R.id.iv_logo)
        }
    }
}