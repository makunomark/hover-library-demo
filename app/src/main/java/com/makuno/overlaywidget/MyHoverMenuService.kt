package com.makuno.overlaywidget

import android.content.Context
import android.content.Intent
import android.view.View
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.mattcarroll.hover.Content
import io.mattcarroll.hover.HoverMenu
import io.mattcarroll.hover.HoverView
import io.mattcarroll.hover.window.HoverMenuService

class MyHoverMenuService : HoverMenuService() {

    var mHoverView: HoverView? = null

    override fun onHoverMenuLaunched(intent: Intent, hoverView: HoverView) {
        this.mHoverView = hoverView
        this.mHoverView?.setMenu(createHoverMenu())
        this.mHoverView?.collapse()
    }

    private fun createHoverMenu(): HoverMenu {
        return SingleSectionHoverMenu(applicationContext)
    }

    private inner class SingleSectionHoverMenu(private val mContext: Context) :
        HoverMenu() {
        private val mSection: Section
        private fun createTabView(): View {
            val imageView = CircleImageView(mContext)
            Picasso.get()
                .load("https://i.picsum.photos/id/864/600/600.jpg?hmac=j1q530cDLbrckxJmU37FlobQudVXYi2EQ_BTakugEAg")
                .placeholder(R.drawable.tab_background)
                .into(imageView)
            return imageView
        }

        private fun createScreen(): Content {
            return HoverMenuScreen(mContext, mHoverView!!)
        }

        override fun getId(): String {
            return "singlesectionmenu"
        }

        override fun getSectionCount(): Int {
            return 1
        }

        override fun getSection(index: Int): Section? {
            return if (0 == index) {
                mSection
            } else {
                null
            }
        }

        override fun getSection(sectionId: SectionId): Section? {
            return if (sectionId == mSection.id) {
                mSection
            } else {
                null
            }
        }

        override fun getSections(): List<Section> {
            return listOf(mSection)
        }

        init {
            mSection = Section(
                SectionId("1"),
                createTabView(),
                createScreen()
            )
        }
    }

    companion object {
        private const val TAG = "SingleSectionHoverMenuService"
    }
}