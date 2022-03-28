package com.econnect.client.ItemDetails;

public class CompanyDetailsController implements IDetailsController {

    private final ProductDetailsFragment _fragment;
    private final int _companyId;


    public CompanyDetailsController(ProductDetailsFragment fragment, int companyId) {
        this._fragment = fragment;
        this._companyId = companyId;
    }

    @Override
    public void updateUIElements() {

    }

    @Override
    public void reviewProduct(int numStars) {

    }

    @Override
    public void answerQuestion(int questionId, String answer) {

    }
}
