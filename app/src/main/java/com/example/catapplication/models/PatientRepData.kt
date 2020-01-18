package com.example.catapplication.models

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin com.example.catapplication.models.Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class PatientRepData (
	@SerializedName("id") val id : Int,
	@SerializedName("user_id") val user_id : Int,
	@SerializedName("category_id") val category_id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("hospital_id") val hospital_id : Int,
	@SerializedName("region_id") val region_id : Int,
	@SerializedName("doctor_id") val doctor_id : Int,
	@SerializedName("city_id") val city_id : Int,
	@SerializedName("dropped") val dropped : Int,
	@SerializedName("reason_id") val reason_id : String,
	@SerializedName("notes") val notes : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("category_name") val category_name : String,
	@SerializedName("product_name") val product_name : String,
	@SerializedName("dose_name") val dose_name : String,
	@SerializedName("doctor_name") val doctor_name : String,
	@SerializedName("hospital_name") val hospital_name : String
)