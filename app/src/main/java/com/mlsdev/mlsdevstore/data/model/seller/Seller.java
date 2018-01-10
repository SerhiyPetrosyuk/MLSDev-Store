package com.mlsdev.mlsdevstore.data.model.seller;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Seller implements Parcelable {

    @Expose
    private double feedbackScore;

    public Seller() {
    }

    public double getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(double feedbackScore) {
        this.feedbackScore = feedbackScore;
    }

    //regionParcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.feedbackScore);
    }

    protected Seller(Parcel in) {
        this.feedbackScore = in.readDouble();
    }

    public static final Parcelable.Creator<Seller> CREATOR = new Parcelable.Creator<Seller>() {
        @Override
        public Seller createFromParcel(Parcel source) {
            return new Seller(source);
        }

        @Override
        public Seller[] newArray(int size) {
            return new Seller[size];
        }
    };
    //endregion
}
