// Generated by view binder compiler. Do not edit!
package com.trytest.foodorder.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.trytest.foodorder.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PlaceyourorderListRowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView menuName;

  @NonNull
  public final TextView menuPrice;

  @NonNull
  public final TextView menuQty;

  @NonNull
  public final ImageView thumbImage;

  private PlaceyourorderListRowBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView menuName, @NonNull TextView menuPrice, @NonNull TextView menuQty,
      @NonNull ImageView thumbImage) {
    this.rootView = rootView;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.menuQty = menuQty;
    this.thumbImage = thumbImage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PlaceyourorderListRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PlaceyourorderListRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.placeyourorder_list_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PlaceyourorderListRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.menuName;
      TextView menuName = ViewBindings.findChildViewById(rootView, id);
      if (menuName == null) {
        break missingId;
      }

      id = R.id.menuPrice;
      TextView menuPrice = ViewBindings.findChildViewById(rootView, id);
      if (menuPrice == null) {
        break missingId;
      }

      id = R.id.menuQty;
      TextView menuQty = ViewBindings.findChildViewById(rootView, id);
      if (menuQty == null) {
        break missingId;
      }

      id = R.id.thumbImage;
      ImageView thumbImage = ViewBindings.findChildViewById(rootView, id);
      if (thumbImage == null) {
        break missingId;
      }

      return new PlaceyourorderListRowBinding((ConstraintLayout) rootView, menuName, menuPrice,
          menuQty, thumbImage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
