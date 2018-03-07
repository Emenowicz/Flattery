package zpi.flattery.webscrapper.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocodeResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("results")
    @Expose
    public results[] results;

    public class results {

        @SerializedName("formatted_address")
        @Expose
        public String formatted_address;

        @SerializedName("geometry")
        @Expose
        public geometry geometry;

        @SerializedName("types")
        @Expose
        public String[] types;

        @SerializedName("address_component")
        @Expose
        public address_component[] address_components;
    }

    public class geometry {
        @SerializedName("bounds")
        @Expose
        public bounds bounds;
        @SerializedName("location_type")
        @Expose
        public String location_type;
        @SerializedName("location")
        @Expose
        public location location;
        @SerializedName("viewport")
        @Expose
        public bounds viewport;
    }

    public class bounds {
        @SerializedName("northeast")
        @Expose
        public location northeast;
        @SerializedName("southwest")
        @Expose
        public location southwest;
    }

    public class location {
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("lng")
        @Expose
        public String lng;
    }

    public class address_component {
        @SerializedName("long_name")
        @Expose
        public String long_name;
        @SerializedName("short_name")
        @Expose
        public String short_name;
        @SerializedName("types")
        @Expose
        public String[] types;
    }
}
