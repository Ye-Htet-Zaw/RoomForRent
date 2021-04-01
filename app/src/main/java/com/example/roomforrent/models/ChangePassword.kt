package com.example.roomforrent.models

data class ChangePassword ( var user_id: String = "",
                            var password: String = "",
                            var new_password: String? = "",
                            var confirm_password: String? = "")