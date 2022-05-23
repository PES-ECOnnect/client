package com.econnect.API.Translate;

import com.econnect.API.Service;

public class TranslateService extends Service {
    public void translate(String sourceText, String targetLanguageCode) {
        // targetLanguageCode must be a BCP-47 language code (en, es, ca)
        final String translateUrl = "https://translation.googleapis.com/v3/projects/econnect-1648742123087:translateText";
        final String bodyFormat = "{\"contents\": [\"%s\"], \"targetLanguageCode\": \"%s\"}";

        String body = String.format(bodyFormat, sourceText, targetLanguageCode);
        String response = postRaw(translateUrl, null, body);
        System.out.println(response);
    }
}
