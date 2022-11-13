package com.example.ltechapp.domain.usecases.shared_preferences

import com.example.ltechapp.data.login_repository.ILoginRepository

class SharedPref(private val repository: ILoginRepository) {
    var sharedPhone : String? = repository.phone


    var sharedPassword : String? = repository.password

}