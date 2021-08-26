package Adapter

import Model.NoteModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vohidov.notes.R
import kotlinx.android.synthetic.main.item_rv.view.*

class RvAdapter(var context: Context, var list: ArrayList<NoteModel>, var itemClick: ItemClick) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    inner class MyViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: NoteModel, position: Int) {
            //Picasso.get().load(list[position]).into(itemView2.item_image)
            itemView.txt_title.text = list[position].title
            itemView.txt_number.text = (position + 1).toString()

            itemView.setOnClickListener {
                itemClick.onClick(list, position)
            }
            itemView.btn_delete.setOnClickListener {
                itemClick.deleteItem(model)
            }
            itemView.btn_edit.setOnClickListener {
                itemClick.editUser(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filterList(filteredList: ArrayList<NoteModel>) {
        list = filteredList
        notifyDataSetChanged()
    }
}

interface ItemClick {
    fun onClick(list: ArrayList<NoteModel>, position: Int)
    fun deleteItem(noteModel: NoteModel)
    fun editUser(noteModel: NoteModel)

}