package com.blackbirds.nfcposapp.Model

data class LoginResponse(
    val data: Data,
    val message: String,
    val status_code: Int,
    val success: Boolean,
    val token: String
) {
    data class Data(
        val birth_date: Any,
        val first_name: String,
        val groups: List<String>,
        val id: Int,
        val is_active: Boolean,
        val last_name: String,
        val user_address: UserAddress,
        val user_phone: UserPhone,
        val usr_email: String,
        val usr_profile_pic: String
    ) {
        data class UserAddress(
            val address_line_1: String,
            val address_line_2: String,
            val city: String,
            val country: String,
            val post_code: String,
            val state: String,
            val upazila: String
        )

        data class UserPhone(
            val phn_business: Any,
            val phn_cell: String,
            val phn_home: Any
        )
    }
}