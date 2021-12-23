package coming.web3.enity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public final String cid;
    public final String avator;
    public final String address;

    public  UserInfo(String cid, String avator, String address) {
        this.cid = cid;
        this.avator = avator;
        this.address = address;
    }

    protected UserInfo(Parcel in) {
        cid = in.readString();
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

        dest.writeString((cid == null ? "" : cid).toString());
        dest.writeString((avator == null ? "" : avator).toString());
        dest.writeString((address == null ? "" : address).toString());

    }

}
