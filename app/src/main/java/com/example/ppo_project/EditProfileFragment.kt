package com.example.ppo_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.ppo_project.Utils.PictureUtil
import com.example.ppo_project.Utils.UserUtil
import java.io.File

class ProfileEditFragment : ProfileFragment() {

    override val layoutId: Int = R.layout.fragement_edit_profile

    private var photoFile: File? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        val saveProfileButton = v?.findViewById<AppCompatButton>(R.id.saveProfileButton)
        saveProfileButton?.setOnClickListener{saveProfileClick()}
        photoFile = UserUtil.instance.currentPhotoFile
        updateUI()
        return v
    }

    private fun updateUI(){
        val user = UserUtil.instance.currentUser
        nameTextEdit?.setText(user?.name)
        surnameTextEdit?.setText(user?.surname)
        phoneTextEdit?.setText(user?.phone)
        emailTextEdit?.setText(user?.email)
        updatePhotoView()
    }

    private fun saveProfileClick(){
        if(isInputCorrect()) {
            UserUtil.instance.currentUser?.apply {
                name = nameTextEdit?.text.toString()
                surname = surnameTextEdit?.text.toString()
                email = emailTextEdit?.text.toString()
                phone = phoneTextEdit?.text.toString()
                save()
            }
            if (photoFile?.path != UserUtil.instance.currentPhotoFile?.path)
                PictureUtil.saveUserPicture(context, UserUtil.instance.currentPhotoFile)
            findNavController().navigate(R.id.profileViewFragment)
        }
    }
}