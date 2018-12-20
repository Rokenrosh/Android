package com.example.ppo_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import android.content.Context
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.findNavController
import com.example.ppo_project.Models.User
import com.example.ppo_project.Utils.UserUtil
import com.orm.SugarContext
import com.orm.SugarRecord
import java.lang.Exception

class ProfileViewFragment : Fragment() {

    private var textName: AppCompatTextView? = null
    private var textSurname: AppCompatTextView? = null
    private var textPhone: AppCompatTextView? = null
    private var textPassword: AppCompatTextView? = null
    private var textEmail: AppCompatTextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragement_profile, container, false)
        val editButton = v.findViewById<AppCompatButton>(R.id.profile_edit_button)
        editButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.profileEditFragment))
        textName = v.findViewById(R.id.textViewName)
        textSurname = v.findViewById(R.id.textViewSurname)
        textPhone = v.findViewById(R.id.textViewPhoneNumber)
        textEmail = v.findViewById(R.id.textViewEmail)
        updateUI()
        return v
    }

    private fun updateUI(){
        val user = UserUtil.instance.loadUser(context)
        textName?.text = user.name
        textSurname?.text = user.surname
        textPhone?.text = user.phone
        textEmail?.text = user.email
    }
}