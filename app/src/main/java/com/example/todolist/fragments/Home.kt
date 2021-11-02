import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todolist.MainActivity
import com.example.todolist.R
import com.example.todolist.SecondActivity
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.models.Photo
import com.example.todolist.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : ImageItemAdapter

    private var page : Int = 1
    private var photos : MutableList<Photo> = ArrayList()

    private var sort : String = "popular"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        binding = FragmentHomeBinding.bind(view)
        initRecyclerView()
        getImages()
        return view
    }


    private fun getImages() {
        page = 1
        val getPost = RetrofitInstance.api.getRecentPhotos(page,30,sort)
        getPost.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
                if(response.isSuccessful)
                {
                    photos.clear()
                    Log.d("response",response.body().toString())
                    response.body()?.let { photos.addAll(it)}
                    adapter.notifyDataSetChanged()
                }
                else
                    Log.d("response",response.body().toString())
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                Log.d("Response","Failed")
            }

        })

    }


    private fun initRecyclerView() {
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.mySearchView.addTextChangedListener(object : TextWatcher{


            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        adapter = ImageItemAdapter(photos,requireContext())
        binding.homeRecyclerView.adapter = adapter

      adapter.setOnItemClickListener(object : ImageItemAdapter.onItemClickListener{
          override fun onItemClick(position: Int) {

              var intent = Intent(requireContext(),SecondActivity::class.java)

              intent.putExtra("username",photos[position].user.username);

              intent.putExtra("profile_image",photos[position].user.profileImage.large);

              intent.putExtra("photo",photos[position].url.regular)

              intent.putExtra("description",photos[position].description)

              intent.putExtra("otherDescription",photos[position].description)

              intent.putExtra("created_at",photos[position].created_at)

              intent.putExtra("updated_at",photos[position].updated_at)

              startActivity(intent)

          }
      })
    }

    private fun filterList(filterItem: String) {
        var templeList : MutableList<Photo> = ArrayList()

        for(d in photos){
            if(filterItem in d.user.username.lowercase(Locale.getDefault())){
                templeList.add(d)
            }

        }
        adapter.updateList(templeList)

    }


}