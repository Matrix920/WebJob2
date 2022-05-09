package com.wms.mwt.e_wejob.utility.entity;

public class Diploma {
    public int DiplomaID,CandidateID;
    public String DiplomaTitle;

    public static final String DIPLOMA_ID="DiplomaID";
    public static final String CANDIDATE_ID=Candidate.CANDIDATE_ID;
    public static final String DIPLOMA_TITLE="DiplomaTitle";
    public static final String DIPLOMA="Diploma";

    public Diploma(int diplomaID, int candidateID, String diplomaTitle) {
        DiplomaID = diplomaID;
        CandidateID = candidateID;
        DiplomaTitle = diplomaTitle;
    }
}
