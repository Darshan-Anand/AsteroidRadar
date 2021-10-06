package com.ba.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import timber.log.Timber

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("picOfDay")
fun setPictureOfDay(imgView: ImageView, pictureOfDay: PictureOfDay?) {
    val context = imgView.context
    if (null!= pictureOfDay && pictureOfDay.url.isNotBlank()) {
        Timber.d("url:- ${pictureOfDay.url}")
        Picasso.with(context)
            .load(pictureOfDay.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .fit()
            .centerCrop()
            .into(imgView)
        imgView.contentDescription =
            String.format(
                context.getString(R.string.nasa_picture_of_day_content_description_format),
                pictureOfDay.title
            )
    }
    else{
        Timber.d("image setPictureFailed")
        imgView.setImageResource(R.drawable.placeholder_picture_of_day)
        imgView.contentDescription = context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }
}