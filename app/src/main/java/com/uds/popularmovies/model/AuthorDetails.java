package com.uds.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorDetails implements Parcelable {
    @SerializedName("avatar_path")
    @Expose
    private final String avatarPath;
    @SerializedName("rating")
    @Expose
    private final int rating;

    protected AuthorDetails(Parcel in) {
        avatarPath = in.readString();
        rating = in.readInt();
    }

    public static final Creator<AuthorDetails> CREATOR = new Creator<AuthorDetails>() {
        @Override
        public AuthorDetails createFromParcel(Parcel in) {
            return new AuthorDetails(in);
        }

        @Override
        public AuthorDetails[] newArray(int size) {
            return new AuthorDetails[size];
        }
    };

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarPath);
        dest.writeInt(rating);
    }
}
