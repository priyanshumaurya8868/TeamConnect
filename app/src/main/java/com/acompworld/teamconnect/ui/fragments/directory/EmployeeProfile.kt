package com.acompworld.teamconnect.ui.fragments.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acompworld.teamconnect.R
import com.acompworld.teamconnect.databinding.FragmentEmployeesBinding
import com.acompworld.teamconnect.utils.Constants
import com.acompworld.teamconnect.utils.Constants.EMPLYOYEE_ID
import com.acompworld.teamconnect.utils.Constants.PROJECT_Code
import com.acompworld.teamconnect.utils.load

class EmployeeProfile : Fragment(R.layout.fragment_employees) {

    private lateinit  var viewModel: DirectoryViewModel
    private  var empID : String?= null
    private var projectCode:String?= null
    private var binding: FragmentEmployeesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DirectoryViewModel::class.java)
     //TODO : Override directory ViewModel's constructor
         arguments?.apply {
             empID = getString(EMPLYOYEE_ID).toString()
             projectCode =getString(PROJECT_Code).toString()
         }
        projectCode?.let { code-> empID?.let { empID -> viewModel.getEmployeeByID(code, empID) } }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.employee.observe({lifecycle}){
             binding?.apply {
                 ivPfp.load(it.profilepic)
                 tvName.text = it.empname
                 tvDesignation.text = it.designation
                 tvAddress.text= it.location
                 tvEmployeeNo.text = it.empno
                 tvEmail.text= it.email
                 tvMobile.text = it.mobile
                 tvIntercomO.text = it.intercomoffice
                 tvIntercomR.text = it.alternatemobile //TODO : confused
             }
        }

    }
}