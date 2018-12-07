package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translate {
    public String qwe(String str) throws IOException, ParseException {
        char ch = str.trim().charAt(0);
        String string = null;
        if((ch >= 0x0041 && ch <= 0x005A) || (ch >= 0x0061 && ch <= 0x007A)) {
            string = getJsonStringYandex("en-ru", str);
        }
        if((ch >= 0x0410 && ch <= 0x044F)) {
            string = getJsonStringYandex("ru-en", str);
        }
        return string;
    }

    public static String getJsonStringYandex(String trans, String text) throws IOException, ParseException {
        String apiKey = "trnsl.1.1.20181118T191123Z.85d19ad6e11c3a62.704389163aa3b5e399947618a2278da86487b4f8";
        String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + apiKey + "&lang=" + trans + "&text=" + URLEncoder.encode(text, "UTF-8");
        URL url = new URL(requestUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        int rc = httpConnection.getResponseCode();
        if (rc == 200) {
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            return getTranslateFromJSON(strBuilder.toString());
        }
        return "Ошибка";
    }

    public static String getTranslateFromJSON(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(str);
        StringBuilder sb = new StringBuilder();
        JSONArray array = (JSONArray) object.get("text");
        for (Object s : array) {
            sb.append(s.toString() + "\n");
        }
        return sb.toString();
    }

}
