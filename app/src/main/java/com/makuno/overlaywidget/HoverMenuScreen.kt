/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makuno.overlaywidget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import io.mattcarroll.hover.Content
import io.mattcarroll.hover.HoverView


/**
 * A screen that is displayed in our Hello World Hover Menu.
 */
class HoverMenuScreen(
    context: Context,
    private val hoverView: HoverView
) : Content {
    private val mContext: Context = context.applicationContext
    private val rootView: View
    private fun createScreenView(): View {
        return LayoutInflater.from(
            ContextThemeWrapper(
                mContext,
                R.style.AppTheme
            )
        ).inflate(R.layout.content_hover_view, null)
    }

    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.
    override fun getView(): View {
        return rootView
    }

    override fun isFullscreen(): Boolean {
        return false
    }

    override fun onShown() {
        // No-op.
    }

    override fun onHidden() {
        // No-op.
    }

    init {
        rootView = createScreenView()

        Picasso.get()
            .load("https://i.picsum.photos/id/864/600/600.jpg?hmac=j1q530cDLbrckxJmU37FlobQudVXYi2EQ_BTakugEAg")
            .placeholder(R.drawable.tab_background)
            .into(rootView.findViewById<ImageView>(R.id.imageView))

        rootView.findViewById<MaterialButton>(R.id.btnCallVendor).setOnClickListener {
            val intent =
                Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+254 722 222 222", null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
            hoverView.collapse()
        }
    }
}