package com.acompworld.teamconnect.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acompworld.teamconnect.R
import com.acompworld.teamconnect.api.model.entities.Bannerlist
import com.acompworld.teamconnect.api.model.entities.Circular
import com.acompworld.teamconnect.api.model.entities.Latesupdate
import com.acompworld.teamconnect.api.model.entities.Notice
import com.acompworld.teamconnect.databinding.FragmentHomeBinding
import com.acompworld.teamconnect.databinding.HomeRvItem1Binding
import com.acompworld.teamconnect.databinding.RvItem3Binding
import com.acompworld.teamconnect.ui.MainViewModel
import com.acompworld.teamconnect.utils.GenericListAdapter
import com.acompworld.teamconnect.utils.load
import com.acompworld.teamconnect.utils.timeStamp
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class HomeFragment : Fragment() {

    private var bannerBinding: HomeRvItem1Binding? = null
    private var feedBinding: RvItem3Binding? = null
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: MainViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.myWall.observe({ lifecycle }) { myWall ->
            binding.apply {

                if (rv3.adapter == null) {
                    toggleBtnsGrp.selectButton(R.id.tag_all)
                    rv3.adapter = getAllfeedAdapter(myWall.latesupdates)
                }

                projectName.text = myWall.projectname
                //setup rv 1
                rv1.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rv1.adapter = getBannerAdapter(myWall.bannerlist)

                //set up rv 3
                toggleBtnsGrp.setOnSelectListener { button: ThemedButton ->
                    // handle selected button
                    when (button.id) {
                        R.id.tag_all -> {
                            Log.d("omega", "all tag pressed")
                            rv3.adapter = getAllfeedAdapter(myWall.latesupdates)
                        }
                        R.id.tag_circular ->
                            rv3.adapter = getCircularAdapter(myWall.circulars)

                        R.id.tag_notices ->
                            rv3.adapter = getNoticesAdapter(myWall.notices)

                        // News -> Actuall response body Not visible
                        R.id.tag_news -> rv3.adapter = null

                        // others-> Actuall response body Not visible
                        R.id.tag_others -> rv3.adapter = null
                    }
                }
            }
        }
        _binding!!.toggleBtnsGrp.selectButton(R.id.tag_all)
    }

    private fun getNoticesAdapter(list: List<Notice>) = object : GenericListAdapter<Notice>(
        layoutId = R.layout.rv_item_3,
        bind = { item, holder, itemCount ->
           val  feedBinding = RvItem3Binding.bind(holder.itemView)
                feedBinding?.apply {
                    tvTitle.text = item.titleEng?:"_"

                }
        }
    ) {}.apply {
        submitList(list)
    }

    private fun getCircularAdapter(list: List<Circular>) = object : GenericListAdapter<Circular>(
        layoutId = R.layout.rv_item_3,
        bind = { item, holder, itemCount ->
           val feedBinding = RvItem3Binding.bind(holder.itemView)
                feedBinding.apply {
                    tvTitle.text = item.titleEnglish
                    item.postedOn?.let { tvDate.timeStamp = it }
                }
        }
    ) {}.apply {
        submitList(list)
    }


    private fun getAllfeedAdapter(list: List<Latesupdate>) =
        object : GenericListAdapter<Latesupdate>(
            layoutId = R.layout.rv_item_3,
            bind = { item, holder, itemCount ->
               val feedBinding = RvItem3Binding.bind(holder.itemView)
                feedBinding.apply {
                    tvTitle.text = item.titleEnglish
                    item.postedOn?.let { tvDate.timeStamp = it }
                    Log.d("omega", "fun called all  tag")
                }
            }
        ) {}.apply {
            submitList(list)
        }

    private fun getBannerAdapter(bannerlist: List<Bannerlist>) =
        object : GenericListAdapter<Bannerlist>(
            layoutId = R.layout.home_rv_item1,
            bind = { item, holder, itemCount ->
                bannerBinding = HomeRvItem1Binding.bind(holder.itemView)
                bannerBinding?.apply {
                    ivThumbnail.load(item.bannerfile.toString())
                    tvDesp.text = item.captionEng
                }
            }
        ) {}.apply {
            submitList(bannerlist)
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}