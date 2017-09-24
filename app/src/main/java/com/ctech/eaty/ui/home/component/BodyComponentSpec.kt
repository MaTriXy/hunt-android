package com.ctech.eaty.ui.home.component

import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import com.ctech.eaty.R
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.*
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalGravity
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaPositionType

@LayoutSpec
class BodyComponentSpec {

    companion object {


        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewModel: ProductItemViewModel): ComponentLayout {
            val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(viewModel.imageUrl)
                    .build()
            val outValue = TypedValue()
            c.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)

            return Column.create(c)
                    .child(
                            FrescoImage
                                    .create(c)
                                    .controller(controller)
                                    .actualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                                    .withLayout()
                                    .heightPx(c.resources.getDimensionPixelSize(R.dimen.feed_product_height))
                    )
                    .child(
                            Text
                                    .create(c, 0, R.style.TextAppearance_HomeProductTitle)
                                    .text(viewModel.name)
                                    .verticalGravity(VerticalGravity.CENTER)
                                    .isSingleLine(true)
                                    .withLayout()
                                    .backgroundColor(ContextCompat.getColor(c, R.color.black_25))
                                    .positionPx(YogaEdge.LEFT, 0)
                                    .positionPx(YogaEdge.RIGHT, 0)
                                    .positionPx(YogaEdge.TOP,
                                            c.resources.getDimensionPixelSize(R.dimen.feed_product_height) -
                                                    c.resources.getDimensionPixelSize(R.dimen.feed_title_height))
                                    .positionPx(YogaEdge.BOTTOM, 0)
                                    .paddingPx(YogaEdge.LEFT, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))
                                    .paddingPx(YogaEdge.START, c.resources.getDimensionPixelSize(R.dimen.content_padding_horizontal))
                                    .positionType(YogaPositionType.ABSOLUTE)

                    )
                    .clickHandler(BodyComponent.onClick(c))
                    .build()
        }

        @JvmStatic
        @OnEvent(ClickEvent::class)
        fun onClick(
                c: ComponentContext,
                @Prop viewModel: ProductItemViewModel) {

            val intent = ProductDetailActivity.newIntent(c, viewModel.id)
            c.startActivity(intent)
        }
    }
}