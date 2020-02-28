/**
 * @author Paulius Staisiunas
 */

package com.j2020;

public class Constants {
    public static final String DISPLAY_FAILED_PAYMENT_ID = "[Not processed]";
    public static final String DISPLAY_FAILED_PAYMENT_STATUS = "[FAILED]";

    public static final String JMS_TRANSACTION_QUEUE = "MainTransactionQueue";

    public static final String DEUTSCHE_SEPA_PAYMENT_REQUEST_METHOD = "PHOTOTAN";
    public static final String DEUTSCHE_SEPA_PAYMENT_REQUEST_TYPE = "INSTANT_SEPA_CREDIT_TRANSFERS";
    public static final String DEUTSCHE_SEPA_PAYMENT_REQUEST_LANGUAGE = "de";

    public static final String DEUTSCHE_SEPA_PAYMENT_REQUEST_DATA_TYPE = "challengeRequestDataInstantSepaCreditTransfers";

    public static final String REVOLUT_OAUTHJWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyZXZvbHV0LWp3dC1zY" +
            "W5kYm94LmdsaXRjaC5tZSIsInN1YiI6InZ1SXZPWnVDLXFRcUppT0VOVjFOcmI4STI2b3FRdHN6V0RMcVNkWUxTS2MiLCJhdWQiOiJod" +
            "HRwczovL3Jldm9sdXQuY29tIn0.zTjgOs0bXnaeke8hTDSwlxoXh-c1i2aZux9N0D2CCTMKptIC0SB4xExpN-wK6dJnORMvJvGLcHeac" +
            "eyaf62Kwa9SQ3tBRtruVzdyBiGOUhBo_dcGfX50L2OGn-BLSf3LmX-4zWpQ1_LvO8wHp9wawEpg59PXMdJXF7TDKVDkCx0";

    public static final String REVOLUT_ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
    public static final String REVOLUT_CLIENT_ID = "vuIvOZuC-qQqJiOENV1Nrb8I26oqQtszWDLqSdYLSKc";
    public static final String REVOLUT_REFRESH_TOKEN = "oa_sand_vx6Wl1iCfh7Ou1XEPMNCDIrWiVe0ip6YtG5Nifj-TKc";
    public static final String REVOLUT_TOKEN_RENEWAL_URL = "https://sandbox-b2b.revolut.com/api/1.0/auth/token";
    public static final String REVOLUT_PAYMENT_URL = "https://sandbox-b2b.revolut.com/api/1.0/pay";
    public static final String REVOLUT_ACCOUNT_URL = "https://sandbox-b2b.revolut.com/api/1.0/accounts";
    public static final String REVOLUT_TRANSACTION_URL = "https://sandbox-b2b.revolut.com/api/1.0/transactions";

    public static final String DEUTSCHE_OAUTHTOKEN = "eyJhbGciOiJub25lIn0.eyJleHAiOjE1ODg2ODI1MTAsImp0aSI6IjE0ZDdkOWVmLTBlNDMtNGRkYy04ZDJkLWI3MzhhMjIzZmNmNSJ9.";
    public static final String DEUTSCHE_CLIENT_SECRET = "AIBSztT5LGU-C4v-vL9mYpugeVusegbNENrVndGgqBLGj37KUIzs0wz1JJvd9oVx8DGmjMrclN1nVuNDT_Q-FlM";
    public static final String DEUTSCHE_CLIENT_ID = "dfd86ac9-8f2a-415a-8805-a90694e049ba";
    public static final String DEUTSCHE_TOKEN_RENEWAL_URL = "https://simulator-api.db.com/gw/oidc/token";
    public static final String DEUTSCHE_PAYMENT_URL = "https://simulator-api.db.com/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers";
    public static final String DEUTSCHE_ACCOUNT_URL = "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts";
    public static final String DEUTSCHE_TRANSACTION_URL = "https://simulator-api.db.com/gw/dbapi/v1/transactions";
    public static final String DEUTSCHE_TWO_FACTOR_SECRET = "PRE6QIOR5XL77XOV";
    public static final String DEUTSCHE_ONE_TIME_PASS_URL = "https://simulator-api.db.com/gw/dbapi/others/onetimepasswords/v2/single";

    public static final String TEST_CURRENCY_CODE = "EUR";
    public static final String TEST_ACCESS_TOKEN = "expiredAnyway";
    public static final String TEST_ANY_ACCOUNT = "anyValidAccount";
    public static final String TEST_DEUTSCHE_DUMMY_SOURCE_IBAN = "DE00100500";

    private Constants() {
    }
}
