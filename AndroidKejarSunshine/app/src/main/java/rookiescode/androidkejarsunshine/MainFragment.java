package rookiescode.androidkejarsunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by muhammadyogiindragiri on 4/23/17.
 */

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String Uri = buildUrl();

        Log.d("Url", Uri);

        new DataProcess().execute(Uri);


    }
    public  String buildUrl(){
        final String FORECAST_BASE_URL ="http://api.openweathermap.org/data/2.5/forecast/daily?";
        final String QUERY_PARAM ="q";
        final String FORMAT_PARAM ="mode";
        final String UNITS_PARAM ="units";
        final String DAYS_PARAM ="cnt";
        final String APPID_PARAM ="APPID";

        String format= "json";
        String units = "metric";
        int numDays = 14;
        String locationQuery = "94043";

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .appendQueryParameter(APPID_PARAM, BuildConfig.API_KEY)
                .build();

        return builtUri.toString();

    }

    public class DataProcess extends AsyncTask<String, String, String>{

        private HttpURLConnection urlConection;
        private BufferedReader reader;
        private String json;

        @Override
        protected String doInBackground(String... url) {

            try{
                URL urls = new URL(url[0]);
                urlConection = (HttpURLConnection) urls.openConnection();
                urlConection.setRequestMethod("GET");
                urlConection.connect();

                InputStream inputStream = urlConection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line +"\n");
                }
                json = buffer.toString();

            } catch (MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e){
                e.printStackTrace();
            }



            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }


    }


}
