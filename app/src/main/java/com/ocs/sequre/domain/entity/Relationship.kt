package com.ocs.sequre.domain.entity

import android.content.Context
import androidx.annotation.StringRes
import com.ocs.sequre.R

enum class Relationship(val flag: Int, @field:StringRes @param:StringRes val stringRes: Int) {
    HUSBAND(1, R.string.relationship_husband),
    WIFE(2, R.string.relationship_wife),
    SON(3, R.string.relationship_son),
    DAUGHTER(4, R.string.relationship_daughter),
    OTHER(5, R.string.relationship_other);

    companion object {
        fun valueOf(flag: Int): Relationship {
            return when (flag) {
                1 -> HUSBAND
                2 -> WIFE
                3 -> SON
                4 -> DAUGHTER
                5 -> OTHER
                else -> OTHER
            }
        }

        fun values(context: Context): ArrayList<String> {
            val list = ArrayList<String>()
            values().forEach {
                list.add(context.getString(it.stringRes))
            }
            return list
        }
    }
}