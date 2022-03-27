package com.econnect.client.ItemDetails;

public interface IDetailsController {

    void updateUIElements();

    void reviewProduct(int numStars);

    void answerQuestion(int questionId, String answer);
}
