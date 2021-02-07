package com.hungama.android.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class MovieCredits {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("cast")
    @Expose
    var cast: List<Cast?>? = null

    @SerializedName("crew")
    @Expose
    var crew: List<Crew?>? = null

    class Cast {
        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null

        @SerializedName("gender")
        @Expose
        var gender: Int? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("known_for_department")
        @Expose
        var knownForDepartment: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("original_name")
        @Expose
        var originalName: String? = null

        @SerializedName("popularity")
        @Expose
        var popularity: Double? = null

        @SerializedName("profile_path")
        @Expose
        var profilePath: String? = null

        @SerializedName("cast_id")
        @Expose
        var castId: Int? = null

        @SerializedName("character")
        @Expose
        var character: String? = null

        @SerializedName("credit_id")
        @Expose
        var creditId: String? = null

        @SerializedName("order")
        @Expose
        var order: Int? = null

    }

    class Crew {
        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null

        @SerializedName("gender")
        @Expose
        var gender: Int? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("known_for_department")
        @Expose
        var knownForDepartment: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("original_name")
        @Expose
        var originalName: String? = null

        @SerializedName("popularity")
        @Expose
        var popularity: Double? = null

        @SerializedName("profile_path")
        @Expose
        var profilePath: Any? = null

        @SerializedName("credit_id")
        @Expose
        var creditId: String? = null

        @SerializedName("department")
        @Expose
        var department: String? = null

        @SerializedName("job")
        @Expose
        var job: String? = null

    }

}