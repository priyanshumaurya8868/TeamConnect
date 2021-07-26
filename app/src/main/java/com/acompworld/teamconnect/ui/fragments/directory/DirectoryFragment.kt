package com.acompworld.teamconnect.ui.fragments.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acompworld.teamconnect.R
import com.acompworld.teamconnect.api.model.entities.Empdirectory
import com.acompworld.teamconnect.api.model.entities.Teldirectory
import com.acompworld.teamconnect.databinding.DirectoryItemBinding
import com.acompworld.teamconnect.databinding.FragmentDirectoryBinding
import com.acompworld.teamconnect.ui.MainViewModel
import com.acompworld.teamconnect.utils.Constants.EMPLYOYEE_ID
import com.acompworld.teamconnect.utils.Constants.PROJECT_Code
import com.acompworld.teamconnect.utils.Constants.TELEPHONE_CONTACT_ID
import com.acompworld.teamconnect.utils.GenericListAdapter
import com.acompworld.teamconnect.utils.load

class DirectoryFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var projectCode: String? = null

    private var binding: FragmentDirectoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.directory.observe({ lifecycle }) { directory ->
            projectCode = directory.projectCode
            binding?.apply {

//                To freeze:
//                recyclerView.suppressLayout(true)
//                To unfreeze:
//                recyclerView.suppressLayout(false)

                rvEmp.apply {
                    adapter = directory.empdirectory?.let { getEmployeesAdapter(it) }
                    suppressLayout(true)
                }

                rvTele.apply {
                    adapter = directory.teldirectory?.let { getTelePhoneContactAdapter(it) }
                    suppressLayout(true)
                }
            }
        }

    }

    private fun getEmployeesAdapter(list: List<Empdirectory>) =
        object : GenericListAdapter<Empdirectory>(
            layoutId = R.layout.directory_item,
            bind = { item, holder, itemCount ->
                val itemBinding = DirectoryItemBinding.bind(holder.itemView)
                itemBinding.apply {
                    tvName.text = item.empname
                    tvDesignation.text = item.designation
                    ivPfp.load(item.profilepic.toString())

                    root.setOnClickListener {
                        val bundle = android.os.Bundle()
                        bundle.apply {
                            putString( EMPLYOYEE_ID, item.empno )
                            putString(
                                PROJECT_Code,
                                projectCode
                            )
                        }
                        findNavController().navigate(
                            R.id.action_nav_directory_to_nav_emp_profile,
                            bundle
                        )
                    }
                }
            }
        ) {}.apply {
            submitList(list)
        }

    private fun getTelePhoneContactAdapter(list: List<Teldirectory>) =
        object : GenericListAdapter<Teldirectory>(
            layoutId = R.layout.directory_item,
            bind = { item, holder, itemCount ->
                val itemBinding = DirectoryItemBinding.bind(holder.itemView)
                itemBinding.apply {
                    tvName.text = item.location
                    tvDesignation.text = item.category
                    ivPfp.load(item.icon.toString())

                    root.setOnClickListener {
                        val bundle = Bundle()
                        bundle.apply {
                            putString(TELEPHONE_CONTACT_ID, item.id.toString())
                            putString(PROJECT_Code, projectCode)
                        }
                        findNavController().navigate(
                            R.id.action_nav_directory_to_nav_tele_directory,
                            bundle
                        )
                    }
                }
            }
        ) {}.apply {
            submitList(list)
        }


}