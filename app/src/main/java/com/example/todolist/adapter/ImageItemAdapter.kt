
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todolist.R
import com.example.todolist.models.Photo

class ImageItemAdapter(private var photos: MutableList<Photo>, private val  context: Context) :
    RecyclerView.Adapter<ImageItemAdapter.ViewHolder>() {

private lateinit var mListener : onItemClickListener

interface onItemClickListener{
    fun onItemClick(position: Int)
}

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener

    }

    class ViewHolder(itemView : View, listener: onItemClickListener ) : RecyclerView.ViewHolder(itemView) {
        val photo : ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.title)
        val desc : TextView = itemView.findViewById(R.id.description);
        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }

        fun bindView(photo: Photo){
            name.text = photo.user.username;
            desc.text = photo.created_at;

        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false)
        return ViewHolder(inflatedView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(photos[position].url.regular)
            .placeholder(ColorDrawable(Color.parseColor(photos[position].color)))
            .into(holder.photo)
        holder.bindView(photos[position]);
    }



    override fun getItemCount(): Int {
        return photos.size
    }

    fun updateList(list: MutableList<Photo>){
        photos = list
        notifyDataSetChanged()
    }


}