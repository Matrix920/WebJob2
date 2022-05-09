package com.wms.mwt.e_wejob.utility.connection;

public class HttpApis {

    public static final String SUCCESS="Success";

    public static final String URL="http://mwts20.somee.com/Android/";

//    post
    public static final String POST_COMPANY_JOBS =URL+"GetCompanyJobs" ;
//    post
    public static final String POST_SUITABLE_JOBS =URL+"GetSuitableJobs" ;

    public static final String POST_DIPLOMAS = URL+"GetDiplomas";

    public static final String GET_COMPANIES = URL+"GetCompanies";

    public static final  String POST_LOGIN =URL+"Login";

    public static  final String POST_ADD_CANDIDATE =URL+"AddCandidate";

    public static  final String POST_ADD_DIPLOMA =URL+"AddDiploma";

    public static final  String POST_ADD_COMPANY =URL+"AddCompany";

    public static final  String POST_ADD_JOB =URL+"AddJob";

    public static final  String POST_GET_OFFERED_JOBS =URL+"GetOfferedJobs";

    public static  final String POST_GET_SUITABLE_CANDIDATES =URL+"GetSuitableCandidates";
}
