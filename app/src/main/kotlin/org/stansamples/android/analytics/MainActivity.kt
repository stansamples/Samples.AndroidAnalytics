package org.stansamples.android.analytics

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        LinearLayout(context).also { root ->
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            root.orientation = LinearLayout.VERTICAL
            root.gravity = Gravity.CENTER_VERTICAL
            Button(context).also { view ->
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                view.text = "save event"
                view.setOnClickListener { _ ->
                    // todo
                }
                root.addView(view)
            }
            setContentView(root)
        }
    }
}
