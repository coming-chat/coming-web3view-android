package coming.web3.enity.webview;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public final String name;
    public final String avator;
    public final String address;

    public UserInfo(String name, String avator, String address) {
        this.name = name;
        this.avator = avator;
        this.address = address;
    }

    protected UserInfo(Parcel in) {
        name = in.readString();
        avator = in.readString();
        address = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString((name == null ? "" : name).toString());
        dest.writeString((avator == null ? "" : avator).toString());
        dest.writeString((address == null ? "" : address).toString());

    }

}
