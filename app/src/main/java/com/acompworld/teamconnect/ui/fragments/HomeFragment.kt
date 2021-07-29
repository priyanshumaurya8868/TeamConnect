package com.acompworld.teamconnect.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acompworld.teamconnect.R
import com.acompworld.teamconnect.api.model.entities.Bannerlist
import com.acompworld.teamconnect.api.model.entities.FeedItem
import com.acompworld.teamconnect.api.model.responses.MyWallResponse
import com.acompworld.teamconnect.databinding.FragmentHomeBinding
import com.acompworld.teamconnect.databinding.HomeRvItem1Binding
import com.acompworld.teamconnect.databinding.RvItem3Binding
import com.acompworld.teamconnect.ui.MainViewModel
import com.acompworld.teamconnect.utils.GenericListAdapter
import com.acompworld.teamconnect.utils.Resource
import com.acompworld.teamconnect.utils.load
import com.acompworld.teamconnect.utils.timeStamp
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var bannerBinding: HomeRvItem1Binding? = null
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: MainViewModel
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
            handleResources(view, myWall)
        }

        _binding!!.toggleBtnsGrp.selectButton(viewModel.HOME_TOGGLE_BTN_ID)
    }
    private fun handleResources(view: View, res: Resource<MyWallResponse?>) {
        when (res) {
            is Resource.Loading -> {
                _binding?.progressCircular?.isVisible = true

            }
            is Resource.Error -> {
                _binding?.progressCircular?.isVisible = false
                res.data?.let { setUpViews(it) }
                Snackbar.make(view, res.msg ?: "Something went Wrong", Snackbar.LENGTH_LONG).apply {
                    setAction("Retry") {
                        viewModel.getMyWall()
                    }
                }.show()
            }
            is Resource.Success -> {
                setUpViews(res.data!!)
                _binding?.progressCircular?.isVisible = false
            }
        }
    }
    private fun setUpViews(myWall: MyWallResponse) {
        //performing initial moves...
        defaultSetUp(myWall)
        setUpBanners(myWall)
        initializeGenerationData(myWall)
        setUpToggleBtns(myWall)
    }
    private fun defaultSetUp(myWall: MyWallResponse) {
        binding.apply {
            if (rv3.adapter == null) {
                toggleBtnsGrp.selectButton(R.id.tag_all)
                rv3.adapter = myWall.latesupdates?.let { getFeedAdapter(it) }
            }
            projectName.text = myWall.projectname
        }
    }
    private fun initializeGenerationData(myWall: MyWallResponse) {
        _binding?.apply {
            containerGenerationData.isVisible = !myWall.generationData.isNullOrEmpty()
            myWall.generationData?.let { list ->
                val item = list[0]
                tvRunningUnit.text = item.runningunits
                tvTotalUnit.text = "/${item.totalunits}"
                splitString(item.generation, tvGenrationDataUnit, tvGenrationData)
                tvPlf.text = item.plf.removeSuffix("%")
                tvFreq.text = item.freq
            }
        }
    }

    private fun splitString(str: String, tvAlphabetic: TextView, tvNumeric: TextView) {
        val alpha = StringBuffer()
        val num = StringBuffer()
        val special = StringBuffer()
        for (i in str.indices) {
            when {
                Character.isDigit(str[i]) -> num.append(str[i])
                Character.isAlphabetic(str[i].code) -> alpha.append(str[i])
                else -> special.append(str[i])
            }
        }
        tvNumeric.text = num
        tvAlphabetic.text = if (alpha.isNotBlank()) alpha.toString().lowercase() else special
    }

    private fun setUpBanners(myWall: MyWallResponse) {
        _binding?.apply {
            rv1.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rv1.adapter = getBannerAdapter(myWall.bannerlist)
        }
    }
    private fun setUpToggleBtns(myWall: MyWallResponse) {
        _binding?.apply {
            toggleBtnsGrp.setOnSelectListener { button: ThemedButton ->
                viewModel.HOME_TOGGLE_BTN_ID = button.id
                when (button.id) {
                    R.id.tag_all -> {
                        Log.d("omega", "all tag pressed")
                        rv3.adapter = myWall.latesupdates?.let { getFeedAdapter(it) }
                    }
                    R.id.tag_circular ->
                        rv3.adapter = myWall.circulars?.let { getFeedAdapter(it) }

                    R.id.tag_notices ->
                        rv3.adapter = myWall.notices?.let { getFeedAdapter(it) }

                    // News -> Actuall response body Not visible
                    R.id.tag_news -> myWall.news?.let { getFeedAdapter(it) }

                    // others-> Actuall response body Not visible
                    R.id.tag_others -> rv3.adapter = myWall.others?.let { getFeedAdapter(it) }
                }
            }
        }
    }
    private fun getFeedAdapter(list: List<FeedItem>) =
        object : GenericListAdapter<FeedItem>(
            layoutId = R.layout.rv_item_3,
            bind = { item, holder, itemCount ->
                val feedBinding = RvItem3Binding.bind(holder.itemView)
                feedBinding.apply {
                    tvTitle.text = item.titleEng ?: item.titleEnglish_v2
                    (item.postedon ?: item.postedOn_v2)?.let { tvDate.timeStamp = it }
                }
            }
        ){}.apply {
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



}