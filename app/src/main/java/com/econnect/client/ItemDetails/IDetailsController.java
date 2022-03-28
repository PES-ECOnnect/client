package com.econnect.client.ItemDetails;

public interface IDetailsController {

    void updateUIElements();

    void reviewProduct();

    void setStars(int i);

    void answerQuestion(String questionId, String answer);
}
