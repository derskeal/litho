/*
 * Copyright 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE-examples file in the root directory of this source tree.
 */

package com.facebook.samples.litho.lithography;

import android.support.v7.widget.OrientationHelper;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.RecyclerBinder;

public class Artist implements Datum {

  public final String name;
  public final String biography;
  public final String[] images;
  public final int year;

  public Artist(String name, String biography, int year, String... images) {
    this.name = name;
    this.biography = biography;
    this.year = year;
    this.images = images;
  }

  @Override
  public Component createComponent(ComponentContext c) {
    final RecyclerBinder imageRecyclerBinder = new RecyclerBinder.Builder()
        .layoutInfo(new LinearLayoutInfo(c, OrientationHelper.HORIZONTAL, false))
        .build(c);

    for (String image : images) {
      ComponentRenderInfo.Builder imageRenderInfoBuilder = ComponentRenderInfo.create();
      imageRenderInfoBuilder.component(
          SingleImageComponent.create(c)
              .image(image)
              .aspectRatio(2f)
              .build());
      imageRecyclerBinder.insertItemAt(
          imageRecyclerBinder.getItemCount(),
          imageRenderInfoBuilder.build());
    }
    return FeedItemCard.create(c)
        .artist(this)
        .binder(imageRecyclerBinder)
        .build();
  }
}
