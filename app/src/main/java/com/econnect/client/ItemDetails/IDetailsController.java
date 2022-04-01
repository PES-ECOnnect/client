package com.econnect.client.ItemDetails;

public interface IDetailsController {

    void updateUIElements();

    void reviewProduct();

    void setStars(int i);

    void answerQuestion(int questionId, boolean answer);
}
