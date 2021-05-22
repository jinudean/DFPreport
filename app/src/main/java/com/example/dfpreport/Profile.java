package com.example.dfpreport;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class Profile implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profileId")
    private Integer profileId;

    public void setProfileId(Integer profileId){
        this.profileId = profileId;
    }

    protected Profile(Parcel in) {
        if (in.readByte() == 0){
            profileId = null;
        } else {
            profileId = in.readInt();
        }
        firstName = in.readString();
        lastName = in.readString();
        employeeNumber = in.readString();
        email = in.readString();
        phone = in.readString();
        station = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Integer getProfileId() {return profileId; }

    @NonNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    @ColumnInfo(name = "lastName")
    private String lastName;

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    @ColumnInfo(name = "employeeNumber")
    private String employeeNumber;

    @NonNull
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(@NonNull String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    @ColumnInfo(name = "phone")
    private  String phone;

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    @ColumnInfo(name = "station")
    private String station;

    @NonNull
    public String getStation() {
        return station;
    }

    public void setStation(@NonNull String station) {
        this.station = station;
    }

    public String geFullName(){
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName);
        fullName.append(" ");
        fullName.append(lastName);
        return fullName.toString();

    }

    public Profile(@NonNull String firstName, @NonNull String lastName, @NonNull String employeeNumber, @NonNull String email, @NonNull String phone, String station){
        this.profileId = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.email = email;
        this.phone = phone;
        this.station = station;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (profileId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(profileId);
        }
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(employeeNumber);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(station);

    }
}
