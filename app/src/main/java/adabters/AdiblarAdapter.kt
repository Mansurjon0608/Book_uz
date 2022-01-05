package adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemRvBinding

import com.squareup.picasso.Picasso
import models.Adib
import utils.MySharedPreferance
import java.io.Serializable


class AdiblarAdapter(
    val context: Context?,
    var list: ArrayList<Adib>,
    var rvClick: RvClick,
    var like: Int = 0
) : RecyclerView.Adapter<AdiblarAdapter.Vh>(), Serializable {

    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun onBind(adib: Adib) {
            itemRv.txtBookname.text = adib.bookName
            itemRv.txtDate.text = adib.data
            itemRv.txtAdibname.text = adib.authorName

            //itemRv.txtCategory.text = adib.category.toString()
            itemRv.txtTil.text = adib.til
            itemRv.txtJanr.text = adib.kitobJanr
            Picasso.get().load(adib.imageUri).into(itemRv.imageAdib)
            when(adib.category){
                 1-> itemRv.txtCategory.text = "O'zbek adabiyoti"
                2-> itemRv.txtCategory.text = "Jahon adabiyoti"
                3-> itemRv.txtCategory.text = "Boshqa adabiyot"
            }

            MySharedPreferance.init(context)
            var index = -1

            var mList = MySharedPreferance.objectString
            for (n in mList.indices) {
                if (mList[n].imageUri == adib.imageUri) {
                    index = n
                    break
                }
            }
                if (index != -1){
                    itemRv.liked.setImageResource(R.drawable.ic_baseline_bookmark_24)
                } else {
                    itemRv.liked.setImageResource(R.drawable.ic_baseline_unbookmark_border_24)
                }

            itemRv.root.setOnClickListener {
                rvClick.onClick(adib)
            }

            itemRv.liked.setOnClickListener {

                if(index != -1){
                    val l = MySharedPreferance.objectString
                    l.add(adib)
                    MySharedPreferance.objectString = l
                }

                if (like == 1){
                    list.remove(adib)
                    notifyItemRemoved(position)
                    notifyItemRangeRemoved(position, mList.size)

                } else {
                    notifyItemChanged(position)
                }
            }
        }


    }

    interface RvClick {
        fun onClick(adib: Adib)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

