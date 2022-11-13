package com.example.ltechapp.utils

import com.example.ltechapp.data.models.AuthAnswerResponse
import com.example.ltechapp.data.models.MaskResponse
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask

object LoginMapper {
    fun MaskResponse.toMask(): Mask =
        Mask(
         phoneMasks = this.phoneMasks
        )

    fun AuthAnswerResponse.toAuthAnswer(): AuthAnswer =
        AuthAnswer(
            success = this.success
        )
}
