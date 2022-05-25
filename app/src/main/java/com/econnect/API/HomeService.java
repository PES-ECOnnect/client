package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.API.Exceptions.ProfileIsPrivateException;
import com.econnect.Utilities.Translate;
import com.econnect.client.R;

import java.util.TreeMap;

public class HomeService extends Service{
    HomeService(){}

    public static class City {
        public final String name;
        public final String[] street_names;
        public City (String name, String[] street_names) {
            this.name = name;
            this.street_names = street_names;
        }
    }

    public static class Homes {
        // Important: The name of these attributes must match the ones in the returned JSON
        public String numero;
        public String escala;
        public String pis;
        public String porta;

        public Homes(String numero, String escala, String pis, String porta) {
            this.numero = numero;
            this.escala = escala;
            this.pis = pis;
            this.porta = porta;
        }

        public void removeNulls(){
            if(this.numero == null) this.numero = "";
            if(this.escala == null) this.escala = "";
            if(this.pis == null) this.pis = "";
            if(this.porta == null) this.porta = "";
        }
    }

   /* public static class HomeCoords {
        public final double latitude;
        public final double longitude;
        public HomeCoords(double lat, double lon) {
            latitude = lat;
            longitude = lon;
        }
    }

     public HomeCoords getHomeLocation() {
        // Call API
        super.needsToken = true;
        JsonResult result = get(ApiConstants.HOME_PATH, null);

        // Parse result
        Double lat = result.getObject("lat", Double.class);
        Double lon = result.getObject("lon", Double.class);

        if (lat == null || lon == null) return null;
        return new HomeCoords(lat, lon);
    }*/


    public Homes[] getHomesBuilding(String zipcode, String street_name, String num) {
        JsonResult result;
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.ZIPCODE, zipcode);
        params.put(ApiConstants.STREET_NAME, street_name);
        params.put(ApiConstants.STREET_NUM, num);
        // Call API
        try{
            super.needsToken = true;
            result = get(ApiConstants.BUILDING_PATH, params);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_BUILDING_NOT_EXISTS:
                    throw new RuntimeException(Translate.id(R.string.building_not_exists));
                default:
                    throw e;
            }
        }

        // Parse result
        Homes[] homes = result.getArray(ApiConstants.RET_RESULT, Homes[].class);
        assertResultNotNull(homes, result);
        for(int i = 0; i < homes.length; ++i) homes[i].removeNulls();
        return homes;
    }

    public String[] getCity(String zipcode) {
        JsonResult result;
        // Call API
        try{
            super.needsToken = true;
            result = get(ApiConstants.CITY_PATH + "/" + zipcode, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_CITY_NOT_EXISTS:
                    throw new RuntimeException(Translate.id(R.string.city_not_exists));
                default:
                    throw e;
            }
        }

        // Parse result
        String[] s = result.getArray(ApiConstants.RET_RESULT, String[].class);
        assertResultNotNull(s, result);
        return s;
    }

    public boolean setHome(String zipcode, String street_name, String num, String escala, String pis, String porta) {
        JsonResult result;
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.ZIPCODE, zipcode);
        params.put(ApiConstants.STREET_NAME, street_name);
        params.put(ApiConstants.STREET_NUM, num);
        if(!escala.equals("")) params.put(ApiConstants.ESCALA, escala);
        if(!pis.equals("")) params.put(ApiConstants.FLOOR, pis);
        if(!porta.equals("")) params.put(ApiConstants.DOOR, porta);
        // Call API
        try{
            super.needsToken = true;
            result = put(ApiConstants.HOME_PATH, params,null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_BUILDING_NOT_EXISTS:
                    throw new RuntimeException(Translate.id(R.string.building_not_exists));
                default:
                    throw e;
            }
        }

        // Parse result
        try {
            super.expectOkStatus(result);
            return true;
        } catch (Exception e){
            // This should never happen, the API should always return an ok status or an error building
            return false;
        }
    }



}
