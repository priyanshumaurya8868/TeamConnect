package com.acompworld.teamconnect.ui.fragments.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acompworld.teamconnect.databinding.FragmentEmployeesBinding
import com.acompworld.teamconnect.databinding.FragmentTelephoneContactBinding
import com.acompworld.teamconnect.utils.Constants
import com.acompworld.teamconnect.utils.Constants.PROJECT_Code
import com.acompworld.teamconnect.utils.Constants.TELEPHONE_CONTACT_ID
import com.acompworld.teamconnect.utils.load

class TelePhoneContact : Fragment() {
    private lateinit  var viewModel: DirectoryViewModel
    private  var empID : String?= null
    private var projectCode:String?= null
    private var  binding  : FragmentTelephoneContactBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DirectoryViewModel::class.java)
        //TODO : Override directory ViewModel's constructor
        arguments?.apply {
            empID = getString(TELEPHONE_CONTACT_ID).toString()
            projectCode =getString(PROJECT_Code).toString()
        }
        projectCode?.let { code-> empID?.let { empID -> viewModel.getContactByID(code, empID) } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTelephoneContactBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.contact.observe({lifecycle}){
            binding?.apply {
                tvName.text = it.location
                tvAbout.text = it.category
                tvAddress.text = it.address
                tvEmail.text = it.email
                tvIntercome.text = it.intercome
                tvPhone.text = it.phone
                ivPfp.load(it.icon)
            }
        }

    }
}