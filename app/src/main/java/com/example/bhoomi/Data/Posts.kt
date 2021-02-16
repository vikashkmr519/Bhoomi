package com.example.bhoomi.Data

import android.net.Uri
import java.sql.Timestamp

data class Posts(
        val userId : String? = null,
        val username : String?= null,
        val image : String? = null,
        val timestamp: Timestamp?= null,
        val likesCount :  Number?= null
        )
