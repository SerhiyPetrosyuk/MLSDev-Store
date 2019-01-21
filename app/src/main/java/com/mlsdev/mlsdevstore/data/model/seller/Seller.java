package com.mlsdev.mlsdevstore.data.model.seller;

import android.os.Parcel;
import android.os.Parcelable;

public class Seller implements Parcelable {
    private double feedbackScore;
    private String username;
    private String feedbackPercentage;

    public Seller() {
    }

    public double getFeedbackScore() {
        return feedbackScore;
    }

    public String getUsername() {
        return username;
    }

    public String getFeedbackPercentage() {
        return feedbackPercentage != null ? feedbackPercentage : "0";
    }

    //regionParcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.feedbackScore);
        dest.writeString(this.username);
        dest.writeString(this.feedbackPercentage);
    }

    protected Seller(Parcel in) {
        this.feedbackScore = in.readDouble();
        this.username = in.readString();
        this.feedbackPercentage = in.readString();
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
