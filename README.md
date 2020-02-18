## J2020 @Visma2020Winter

Currently supports Revolut and Deutsche Bank account listing services only.

The endpoints are called as:

#### Accounts

`GET /accounts`

Retrieves all Revolut and Deutsche Bank accounts.

<details>
    <summary>Example response</summary>
    
    `{
         "REVOLUT": [
             {
                 "name": "Main",
                 "balance": 28600.0,
                 "currency": "GBP",
                 "state": "active",
                 "public": true,
                 "id": "7096f1e1-5903-4b48-8444-0057b233e847",
                 "created_at": "2020-02-04T09:12:53.497290Z",
                 "updated_at": "2020-02-04T09:12:53.590705Z"
             },
             {
                 "name": "Business trips",
                 "balance": 14700.0,
                 "currency": "USD",
                 "state": "active",
                 "public": true,
                 "id": "d5096b25-37ed-4cd8-b734-cade26ed94a6",
                 "created_at": "2020-02-04T09:12:53.497265Z",
                 "updated_at": "2020-02-04T09:12:53.596798Z"
             },
             {
                 "name": "Payments",
                 "balance": 5300.0,
                 "currency": "AUD",
                 "state": "active",
                 "public": false,
                 "id": "5a62fb8f-e5a1-46a3-9cef-a7a63a417bbf",
                 "created_at": "2020-02-04T09:12:53.588341Z",
                 "updated_at": "2020-02-04T09:12:53.588341Z"
             },
             {
                 "name": "European suppliers",
                 "balance": 3429.0,
                 "currency": "EUR",
                 "state": "active",
                 "public": true,
                 "id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                 "created_at": "2020-02-04T09:12:53.497286Z",
                 "updated_at": "2020-02-18T10:14:16.824708Z"
             }
         ],
         "DEUTSCHE": [
             {
                 "currencyCode": "EUR",
                 "bic": "DEUTDEFFXXX",
                 "accountType": "CURRENT_ACCOUNT",
                 "currentBalance": 33654.95,
                 "productDescription": "Persönliches Konto",
                 "iban": "DE10010000000000005771"
             },
             {
                 "currencyCode": "EUR",
                 "bic": null,
                 "accountType": "DEPOSIT_ACCOUNT",
                 "currentBalance": 20.75,
                 "productDescription": "Sparcard",
                 "iban": "DE10010000000000005772"
             }
         ]
     }`
    
</details>

#### Transactions

`GET /transactions`

Retrieves all Revolut and Deutsche Bank transactions.

<details>
    <summary>Example response</summary>
    
    `{
         "REVOLUT": [
             {
                 "id": "e2553907-7192-444f-95f0-c5709d049ca0",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "2291dda3c5ddcb55362911f0888f5ffc61d222c2",
                 "created_at": "2020-02-18T10:14:16.822921Z",
                 "updated_at": "2020-02-18T10:14:16.822921Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "abd31e7d-4e40-4769-9100-7b14be1e716c",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "eadbe990-3828-40c3-8e39-d993141c2211",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "75839e245987f4587e2311a4944a19ee55e8da95",
                 "created_at": "2020-02-18T10:14:16.255920Z",
                 "updated_at": "2020-02-18T10:14:16.255920Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "b4d0b80f-301b-4185-b68a-4d32d9044e8f",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "e8bb848c-7d3a-477f-aba6-451f473b3ecd",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "e6b274838edad6b1b335f17c8a415e75e3251963",
                 "created_at": "2020-02-18T09:46:23.822596Z",
                 "updated_at": "2020-02-18T09:46:23.822596Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "0ad211a2-c674-4ac8-a578-21b14500dd04",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "42685c66-b968-48bd-87a1-9f8723b3e4ef",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "5d277afea4a4e8f748261028a2dd74f4b99f3912",
                 "created_at": "2020-02-18T09:46:23.672875Z",
                 "updated_at": "2020-02-18T09:46:23.672875Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "fb2915ba-7cc0-4776-9246-dc9ed30af1c7",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "f756118a-4814-4fb0-be4d-e92f516e7857",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "797837b7448204ec89b0e998be209355332549cc",
                 "created_at": "2020-02-18T09:44:48.842095Z",
                 "updated_at": "2020-02-18T09:44:48.842095Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "811e265c-a4d2-43a7-875b-f36337860da9",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "2413a83c-f7e2-485a-a186-aeb3356cd188",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "fcf78635cf66f8f1fcdee6051f1782a1afbd5ac7",
                 "created_at": "2020-02-18T09:42:00.380350Z",
                 "updated_at": "2020-02-18T09:42:00.380350Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "d56a4040-e5f3-4002-a310-3342cf00e57e",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "c2e7cf58-b78d-4a97-a20e-7eb37b52dc3b",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "1256eb37f78e3769a52f8f46d57fa291bcf560fa",
                 "created_at": "2020-02-18T08:33:59.104781Z",
                 "updated_at": "2020-02-18T08:33:59.104781Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "bb8e72c3-2093-48a5-91a5-8a0ab89cbc9b",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "ddc81632-90c8-4599-90df-9b8ddda924b0",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "b0438177870bce802a4c327ddbbf8e07735e87fe",
                 "created_at": "2020-02-18T08:30:37.191016Z",
                 "updated_at": "2020-02-18T08:30:37.191016Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "d68deacc-6080-4c2a-9bd4-5038b022034c",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "2e72c9df-1156-4dc4-89e6-258f824a18d6",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "c51e983fae234e6fee98ad55a5c05c39371c23f9",
                 "created_at": "2020-02-18T08:29:31.774979Z",
                 "updated_at": "2020-02-18T08:29:31.774979Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "1cd5ec81-c39b-4930-9739-93fa046fea84",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "3b79dc8e-1831-460a-8157-d29732696c02",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "dff0b3ce6bf054ddce8aadf56de94c58e33b2cda",
                 "created_at": "2020-02-18T08:17:55.138457Z",
                 "updated_at": "2020-02-18T08:17:55.138457Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "bc9ecee4-146d-44eb-a06e-b8f946b97128",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "0881a005-b8bf-40ab-8b2c-8f9dc4b12b2b",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "b2311a2cd6f8bc0daf6224488fc500502d4a3dbf",
                 "created_at": "2020-02-18T08:10:06.642031Z",
                 "updated_at": "2020-02-18T08:10:06.642031Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "f27f33f6-7a26-4d56-a2ae-fcd8847d15ec",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "9181e0c3-a2d5-4811-b7c5-69ea0f1755ff",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "14ed79b7383eb56b6727148c90286a5ea0d62bd5",
                 "created_at": "2020-02-17T23:35:11.537893Z",
                 "updated_at": "2020-02-17T23:35:11.537893Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "dc592c1b-7485-4c41-baef-e4b81ec3b33a",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "d8800c7d-10c9-4d35-adde-c44d29c0a78c",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "716d6d7ba443f3ff9f96b0e9447b0364b8499764",
                 "created_at": "2020-02-17T23:35:11.376349Z",
                 "updated_at": "2020-02-17T23:35:11.376349Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "41b9b4b8-7fc4-43eb-adc0-6660e935c826",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "981b3a86-202b-4fc6-8134-5b0df3282393",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "bdab073d221295781df5038d8886517ec5db98b6",
                 "created_at": "2020-02-17T23:33:27.488851Z",
                 "updated_at": "2020-02-17T23:33:27.488851Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "8b9a7205-395b-427e-a0ef-368032ca127a",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "0adfa3cd-f3f2-4374-ae8f-c39c67944206",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "7564a79fe502ee34d0ddf0841e2184b3fc8c8e36",
                 "created_at": "2020-02-17T23:33:27.313431Z",
                 "updated_at": "2020-02-17T23:33:27.313431Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "f89f0f82-fd8c-4771-8126-0cbcf831c11f",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "ddbebd01-db13-498b-b45d-ac8e8291ea75",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "5048a81e87f26fa5fee70062c32ae519e7f1bcda",
                 "created_at": "2020-02-17T15:33:38.593061Z",
                 "updated_at": "2020-02-17T15:33:38.593061Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "2814a4b1-3692-4bb6-898b-73ae9a364f1e",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "e5ab0032-609c-4ffd-b3c3-e181678d8c37",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "6b4eb863df83a761ff941d64ddcd7e89600c5271",
                 "created_at": "2020-02-17T15:33:38.449808Z",
                 "updated_at": "2020-02-17T15:33:38.449808Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "e092132c-7748-4a80-b09e-2656bf78bbc0",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "be0405ac-76fd-41a3-bdd3-ad4303ac12d2",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "c05015f4c56165f86c45dc4e5d785675b26af85f",
                 "created_at": "2020-02-17T15:32:44.652708Z",
                 "updated_at": "2020-02-17T15:32:44.652708Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "4c3bb287-5cf3-4559-bc95-9ce2b5bf507d",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "2fd748b1-49b4-4990-afe5-3a8e2c952944",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "29321e2ad14ba32b6f5a6b71bf79396cc5fe5161",
                 "created_at": "2020-02-17T15:32:44.511552Z",
                 "updated_at": "2020-02-17T15:32:44.511552Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "50ecc784-f41d-476a-9bc9-d2ca3c235e1b",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "fd2b8ed9-66cc-4d43-9d02-9e5e3a6cb640",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "9f7f954acfd9bf8d303921f820d90bfc76c68e7f",
                 "created_at": "2020-02-17T15:29:43.513279Z",
                 "updated_at": "2020-02-17T15:29:43.513279Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "94077578-f1ad-4b4a-aa01-22de114fe417",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "a880f21e-b803-4c0f-aeb8-7c74fb17bff0",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "9b12cec79d952991a92d18f4f962be256691ad4b",
                 "created_at": "2020-02-17T15:29:43.361323Z",
                 "updated_at": "2020-02-17T15:29:43.361323Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "4bb25d59-70d4-4a34-9960-b72906981e06",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "b361e5f9-2a80-48a9-ba73-d3bb330a67f5",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "7040492330883d7090df0fd7713e69512693daa6",
                 "created_at": "2020-02-17T15:29:14.246791Z",
                 "updated_at": "2020-02-17T15:29:14.246791Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "8864dcf0-642a-4749-8046-2cc022772067",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "eda01f49-ef3b-4639-b751-f0c99a0f794d",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "6967229028e219c359eb5dc94646d58b460178a3",
                 "created_at": "2020-02-17T15:29:14.113073Z",
                 "updated_at": "2020-02-17T15:29:14.113073Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "855d8840-decc-433f-8235-2a7612311151",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "bb3b0317-d19f-408c-9ffe-4b3170e69ce9",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "357ce0b3f087e6ee934ada29cea7ec5bf8e1108c",
                 "created_at": "2020-02-17T15:28:13.891284Z",
                 "updated_at": "2020-02-17T15:28:13.891284Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "d9fe4048-7b03-4992-a516-b6ac3cb7c1fd",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "9cda1867-c3c0-41fd-a4e7-a542a5520de5",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "27cd85e3a8e8e7f66a5828da5c117fa85342db5e",
                 "created_at": "2020-02-17T15:28:13.746191Z",
                 "updated_at": "2020-02-17T15:28:13.746191Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "a74afa71-44fb-44be-9758-4b6d77dc3133",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "734c4ff2-fddd-4fe9-94f2-f9690c85a045",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "791c12f7ab0a038992779d29c73c07ce34bb7fbf",
                 "created_at": "2020-02-17T15:16:41.291238Z",
                 "updated_at": "2020-02-17T15:16:41.291238Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "38ad31b1-205e-4efc-95f0-edff1ad4efcc",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "cdc4c652-c0d6-4a80-a353-a89725de2f29",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "b5125ac268f1da2ef2c3f1f97a075221103675c5",
                 "created_at": "2020-02-17T15:16:41.145510Z",
                 "updated_at": "2020-02-17T15:16:41.145510Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "d1e3adb7-4cf8-4d3e-aa48-09fde6b48ac6",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "368c194e-49a9-40ea-ad80-cfc203cd812f",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "b1cae0f2ca18d49644f2397918d551aa49c663ec",
                 "created_at": "2020-02-17T15:08:20.033191Z",
                 "updated_at": "2020-02-17T15:08:20.033191Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "ee424c7b-a9ac-467b-9dbd-571126ca2eb2",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "0e22c0e0-8470-44e3-81ac-bbb655660abe",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "48f1cd2998d5adb6dc82e8137ba34bfe9a71bb88",
                 "created_at": "2020-02-17T15:08:19.861464Z",
                 "updated_at": "2020-02-17T15:08:19.861464Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "f21dd100-8424-4667-8d8a-a1e0c82b2cfd",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "f324f2b1-4f35-4fe3-b4ce-7a003670d8ee",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "c8ad9d13830ab7de2966407ef06ef7b93a67eafd",
                 "created_at": "2020-02-17T14:40:20.158389Z",
                 "updated_at": "2020-02-17T14:40:20.158389Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "ea92d5d3-8f42-4efa-9c7c-dfc083622a56",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "b5954aa0-463c-42be-84a2-f2f6b22a35d8",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "Some reference",
                 "merchant": null,
                 "card": null,
                 "request_id": "a0a0c3c3e962a80744719cd7ac8e78de86c87d76",
                 "created_at": "2020-02-17T14:40:19.880215Z",
                 "updated_at": "2020-02-17T14:40:19.880215Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "9a61cedc-8ac6-4f7b-b396-5f660cefda70",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "93247ccf-5787-47f2-a816-925b93e48b63",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "de4382a94e55bbd258547860d3d4fa03cbbcc45b",
                 "created_at": "2020-02-17T10:35:33.452540Z",
                 "updated_at": "2020-02-17T10:35:33.452540Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "9ab424b1-7c19-4369-a1fc-3620d45b5d50",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "af236117-3717-47da-a480-c6237d7388e6",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "d936e0dc8eff7e5e3b9b6eebbd262f895fd0d101",
                 "created_at": "2020-02-17T10:35:33.338353Z",
                 "updated_at": "2020-02-17T10:35:33.338353Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "da679652-a0df-4b7c-88da-e0ee45adcf27",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "21e275f8-7856-4594-8995-5404785972fc",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "b19dc84afb30c85c8e981bf7c33d70241f687655",
                 "created_at": "2020-02-17T09:00:37.684409Z",
                 "updated_at": "2020-02-17T09:00:37.684409Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "94edc364-7d6d-4874-8dab-b49479ddebf6",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "27709ac7-67db-4392-b156-deb72c8f66fc",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "5763e17c433adb4b3fe13cfcd164705a2d4bbc2f",
                 "created_at": "2020-02-17T09:00:37.562405Z",
                 "updated_at": "2020-02-17T09:00:37.562405Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "97902ad0-c316-4809-ae87-2d40a52780d7",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "5acf73d9-92f9-4593-b8e5-14cd09d0466a",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "82f31009bf6c8fcbd1f17d186cd702184c372b1d",
                 "created_at": "2020-02-17T08:57:16.120493Z",
                 "updated_at": "2020-02-17T08:57:16.120493Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "3b12330f-8e4b-42f0-bab9-1cc88b5f691f",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "28bbd0be-b2d6-4100-a870-93f40de34cf8",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "6a9d5506587b80792eadf9c2af3a4e5a9913618a",
                 "created_at": "2020-02-17T08:57:16.008432Z",
                 "updated_at": "2020-02-17T08:57:16.008432Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "c1a9f5be-238c-4a3c-bafd-5d5a0b7407d0",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "075601de-15f3-4d2a-b2ff-0e9925e725db",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "64c7eadcd73e6c934d5a4b46752eb21a8828888b",
                 "created_at": "2020-02-17T07:55:48.035720Z",
                 "updated_at": "2020-02-17T07:55:48.035720Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "2342fac1-d4be-4c05-a452-a3c2d2605182",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "c6d7e3b3-7145-421d-ab9a-1c05c8c1960c",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "912ee38144bf8eb346cbfc3346f60d37ba796825",
                 "created_at": "2020-02-17T07:53:56.689211Z",
                 "updated_at": "2020-02-17T07:53:56.689211Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "b2754194-80d9-455b-9422-777e57c8e455",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "31d263e6-4511-43be-9b46-d50c717b262e",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "f44902d9380fd268d9fdcee5fbe8ef2f257376fe",
                 "created_at": "2020-02-17T07:53:56.566310Z",
                 "updated_at": "2020-02-17T07:53:56.566310Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "6b1f81b3-8d07-4124-ad04-242a73d39a90",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "cd285d17-9930-4ee7-ba23-6fec3a21514f",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "4a6cb300948d58e0f6ee621cabaefc23f0142f89",
                 "created_at": "2020-02-17T07:29:05.668476Z",
                 "updated_at": "2020-02-17T07:29:05.668476Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "d56b853a-3f12-4866-89d9-2dc8b28788d3",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "ff1b4528-42a6-4fab-95fc-4baab5e56ced",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "e0b18d1f197658d3d3bc9237c5fbeeecad52dbfd",
                 "created_at": "2020-02-17T07:29:05.551976Z",
                 "updated_at": "2020-02-17T07:29:05.551976Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "caa846ab-f660-419b-99fa-f2026c3d9ae4",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "9379f1d2-807d-455c-96d5-cb4e611dabea",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "de9ebdc0a7a287f8fffe246f226e6e68d7d4eea9",
                 "created_at": "2020-02-14T16:02:17.474549Z",
                 "updated_at": "2020-02-14T16:02:17.474549Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "a7621138-ebdd-466f-b316-1df2ff7df8d7",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "180600d5-9e42-46dc-a55c-967498994614",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "f1937fc5f650e8bc320cb7c728789043aac87250",
                 "created_at": "2020-02-14T16:02:17.359734Z",
                 "updated_at": "2020-02-14T16:02:17.359734Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "b71e53b8-4bd5-486b-9d79-6e041691c220",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "9ae5712e-9603-4e3e-a3db-974fc484856f",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "87fb8050bef8d73e94090b362ed833adc2a9fee1",
                 "created_at": "2020-02-14T15:53:23.571493Z",
                 "updated_at": "2020-02-14T15:53:23.571493Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "e4108e6c-8419-475b-837b-03a80e65d817",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "e5889103-314c-40bb-b81f-6881e5d135f9",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "682356d5dad13947cde7d65368121d07a8174451",
                 "created_at": "2020-02-14T15:53:23.466469Z",
                 "updated_at": "2020-02-14T15:53:23.466469Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "36914de9-76ae-431d-b01a-1d3b1d05ab3a",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "71fef0c7-fb51-4501-80dc-50c9791fb77c",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "15b6aa6f3d7fc5d442b1f0608217b376e819b602",
                 "created_at": "2020-02-14T15:49:39.957544Z",
                 "updated_at": "2020-02-14T15:49:39.957544Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "504ddb30-12cd-47c7-87cd-50439e016708",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "a1840ac9-da99-4918-a122-de32e2246080",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "8c8145c3d0c2ed55d692b4c7435a1e6e882dc09f",
                 "created_at": "2020-02-14T15:49:39.815621Z",
                 "updated_at": "2020-02-14T15:49:39.815621Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "7b36a5fd-2ac9-4106-a0a5-3d56d161a89e",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "7f363031-d6c3-47ed-9373-04fcc65423e4",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "ca0bfd2489941cf096ca13c49fc3d2fcbee6ea11",
                 "created_at": "2020-02-14T13:50:29.422075Z",
                 "updated_at": "2020-02-14T13:50:29.422075Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "e824df21-86a1-4ef1-80b4-989828f91b3a",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "98293aff-6a3b-4190-b35e-6f429af13ec1",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "886e69308ab56936e318585bad3b61cf14a99158",
                 "created_at": "2020-02-14T13:50:29.306129Z",
                 "updated_at": "2020-02-14T13:50:29.306129Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "7055e78c-185e-422f-97ef-427d2da061b1",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "ee4203eb-30a2-4c59-ada7-27ff3c8b457a",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "255c97aa28aec7268bfc292424e848981740fbd3",
                 "created_at": "2020-02-14T07:38:30.629048Z",
                 "updated_at": "2020-02-14T07:38:30.629048Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "655afa04-e7f5-47b4-9d51-9b0b1d2b80c9",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "251029ce-3d73-4150-a778-2dc136a86a74",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "99e7a1d06095ca934a487d9ca28e55574ae920d6",
                 "created_at": "2020-02-14T07:38:30.518650Z",
                 "updated_at": "2020-02-14T07:38:30.518650Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "1f0f6d86-1b2f-4ee0-a2e3-d22d953a4ad7",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "f71d5d0a-7b37-4657-ae1b-17c8b60faf1d",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "2e4b3142e138c75386375ac9d1cbbbbe66c482dc",
                 "created_at": "2020-02-14T07:36:04.027984Z",
                 "updated_at": "2020-02-14T07:36:04.027984Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "70937dcb-d76b-4beb-82ce-afa95b1d1ec8",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "1c6f65c0-aada-4d00-868b-b77d472ba276",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "724be2b32399b31f27f2054863d261d7395769d2",
                 "created_at": "2020-02-14T07:36:03.923491Z",
                 "updated_at": "2020-02-14T07:36:03.923491Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "87388ec1-6875-4c0a-8fee-4a239520ea69",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "4d02b8ef-146d-454b-afef-c65278fbb2a7",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "c3a912c98667d8a0d899a1b66cfa4955d1ec57eb",
                 "created_at": "2020-02-14T07:32:18.099748Z",
                 "updated_at": "2020-02-14T07:32:18.099748Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "4a6bfa9f-e117-4d21-91a1-232cdce2f357",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "38e21905-6533-45eb-9822-337d440c8a31",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "7a6cca85e7fa959b2f3008545c63dc9a61f20941",
                 "created_at": "2020-02-14T07:32:17.995499Z",
                 "updated_at": "2020-02-14T07:32:17.995499Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "e4c4c27c-8d20-4b3e-9a05-eb0088b60669",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "68c3401e-c276-4cf4-9f93-7044164b77c9",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "b01054991e8e16ee299b7c6c34353e2232b582d1",
                 "created_at": "2020-02-14T07:30:45.954608Z",
                 "updated_at": "2020-02-14T07:30:45.954608Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "3020561e-a86c-4659-9f53-8a2a6c8cde76",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "334c487d-1906-48e1-a84d-940e58535246",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "ade26bb1b21fa1e36a757119faad2257dd23af59",
                 "created_at": "2020-02-14T07:30:45.841859Z",
                 "updated_at": "2020-02-14T07:30:45.841859Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "cef6a4b6-a3c9-46fb-bd5b-46796aac7320",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "82c447a4-4ce7-4df9-8656-bf484a16ceb0",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "45f4c77e5380652364ee0233ffbfe40cb76ef2a9",
                 "created_at": "2020-02-13T15:38:47.989781Z",
                 "updated_at": "2020-02-13T15:38:47.989781Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "7d57f0fb-8a95-44b5-9127-c1f802d560c3",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "65683ef2-2925-4726-ba00-ff222bf5f106",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "658b35c452750036fea1b884eef36818f989f839",
                 "created_at": "2020-02-13T15:37:30.195767Z",
                 "updated_at": "2020-02-13T15:37:30.195767Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "40fc35a7-dc1c-4865-b224-d3ab9a79aa11",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "39169dbd-8852-4751-841d-1d80d35fb337",
                 "type": "transfer",
                 "state": "pending",
                 "reference": "test",
                 "merchant": null,
                 "card": null,
                 "request_id": "e0cbf84638264ee082a848b",
                 "created_at": "2020-02-13T13:59:31.238324Z",
                 "updated_at": "2020-02-13T13:59:31.238324Z",
                 "completed_at": null,
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "external",
                             "account_id": "62670460-561e-4955-ba19-0b4c4df46566"
                         },
                         "amount": -1.0,
                         "currency": "EUR",
                         "description": "To Acme Corp.",
                         "balance": null,
                         "leg_id": "6fb0a174-39ac-4c88-8b0b-9bf005ecb1c7",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "a14c37fe-5d66-4e95-aed7-37aeb8d87bde",
                 "type": "card_payment",
                 "state": "completed",
                 "reference": null,
                 "merchant": {
                     "name": "Tesco Stores",
                     "city": "London",
                     "country": "GBR",
                     "category_code": "5969"
                 },
                 "card": {
                     "phone": "+447215037625",
                     "card_number": "**** **********3416",
                     "first_name": "Grace",
                     "last_name": "Wright"
                 },
                 "request_id": null,
                 "created_at": "2020-02-04T09:12:53.803174Z",
                 "updated_at": "2020-02-04T09:12:53.803174Z",
                 "completed_at": "2020-02-04T09:12:53.803249Z",
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": null,
                         "amount": -11.5,
                         "currency": "GBP",
                         "description": "Tesco Stores",
                         "balance": null,
                         "leg_id": "8bcad257-c8bd-43cc-8626-3253f6702927",
                         "account_id": "7096f1e1-5903-4b48-8444-0057b233e847",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "6ba675a6-86b8-46e0-956f-10b5ef8253ad",
                 "type": "card_payment",
                 "state": "completed",
                 "reference": null,
                 "merchant": {
                     "name": "Amazon",
                     "city": "London",
                     "country": "GBR",
                     "category_code": "5969"
                 },
                 "card": {
                     "phone": "+447299054978",
                     "card_number": "**** **********7928",
                     "first_name": "Charles",
                     "last_name": "Adams"
                 },
                 "request_id": null,
                 "created_at": "2020-02-04T09:12:53.802648Z",
                 "updated_at": "2020-02-04T09:12:53.802648Z",
                 "completed_at": "2020-02-04T09:12:53.802826Z",
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": null,
                         "amount": -59.99,
                         "currency": "USD",
                         "description": "Amazon",
                         "balance": null,
                         "leg_id": "d7001e71-8016-4ede-a504-f8043fa09e80",
                         "account_id": "d5096b25-37ed-4cd8-b734-cade26ed94a6",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "8e4ff1a3-fe11-484c-a5ec-ac283a88b5f1",
                 "type": "card_payment",
                 "state": "completed",
                 "reference": null,
                 "merchant": {
                     "name": "Tesco Stores",
                     "city": "London",
                     "country": "GBR",
                     "category_code": "5969"
                 },
                 "card": {
                     "phone": "+447263870472",
                     "card_number": "**** **********5056",
                     "first_name": "Emilia",
                     "last_name": "Thompson"
                 },
                 "request_id": null,
                 "created_at": "2020-02-04T09:12:53.802136Z",
                 "updated_at": "2020-02-04T09:12:53.802136Z",
                 "completed_at": "2020-02-04T09:12:53.802388Z",
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": null,
                         "amount": -11.5,
                         "currency": "AUD",
                         "description": "Tesco Stores",
                         "balance": null,
                         "leg_id": "386b4556-45c4-4b27-9745-b678dc6b4751",
                         "account_id": "5a62fb8f-e5a1-46a3-9cef-a7a63a417bbf",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             },
             {
                 "id": "63e0c9ee-8884-47e6-82d5-a0392d47808b",
                 "type": "transfer",
                 "state": "completed",
                 "reference": "payment",
                 "merchant": null,
                 "card": null,
                 "request_id": null,
                 "created_at": "2020-02-04T09:12:53.776451Z",
                 "updated_at": "2020-02-04T09:12:53.776451Z",
                 "completed_at": "2020-02-04T09:12:53.778209Z",
                 "related_transaction_id": null,
                 "legs": [
                     {
                         "counterparty": {
                             "counterparty_id": null,
                             "account_type": "revolut",
                             "account_id": "88888888-4444-4444-4444-222222222222"
                         },
                         "amount": -210.0,
                         "currency": "EUR",
                         "description": "To Rory Pearson",
                         "balance": 3490.0,
                         "leg_id": "3f4dcdc5-0625-4e26-8077-cfe23cef52ae",
                         "account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                         "bill_amount": null,
                         "bill_currency": null
                     }
                 ]
             }
         ],
         "DEUTSCHE": [
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-07-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-07-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -29.55,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-07-12",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -13.6,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "der Inder am eck",
                 "bookingDate": "2019-07-14",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -9.55,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "Pizza Amalfi",
                 "bookingDate": "2019-07-14",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -400.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Rathausplatz",
                 "bookingDate": "2019-07-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-07-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -116.16,
                 "counterPartyName": "JET",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Die Tanke Ihrer Wahl",
                 "bookingDate": "2019-07-21",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-07-23",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-07-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -15.8,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "der Inder am eck",
                 "bookingDate": "2019-07-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-07-30",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -118.16,
                 "counterPartyName": "JET",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Die Tanke Ihrer Wahl",
                 "bookingDate": "2019-08-08",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-08-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-08-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -310.0,
                 "counterPartyName": "Lufthansa",
                 "counterPartyIban": null,
                 "paymentReference": "Flug DUS-SZG & RET.",
                 "bookingDate": "2019-08-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -39.55,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-08-11",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -6.99,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "China 2go",
                 "bookingDate": "2019-08-13",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -13.6,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "der Inder am eck",
                 "bookingDate": "2019-08-16",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -350.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Rathausplatz",
                 "bookingDate": "2019-08-18",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-08-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -40.0,
                 "counterPartyName": "Kitzbühel Taxi",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 1234346458",
                 "bookingDate": "2019-08-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -35.0,
                 "counterPartyName": "Steak Restaurant am Flughafen",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnungs Nr. 324543645",
                 "bookingDate": "2019-08-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -96.16,
                 "counterPartyName": "JET",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Die Tanke Ihrer Wahl",
                 "bookingDate": "2019-08-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -50.0,
                 "counterPartyName": "Lufthansa",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Upgrade to Businessclass",
                 "bookingDate": "2019-08-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -55.0,
                 "counterPartyName": "Sonnenalm",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 235573132",
                 "bookingDate": "2019-08-21",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -122.3,
                 "counterPartyName": "Take five",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 1234346458",
                 "bookingDate": "2019-08-21",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -120.0,
                 "counterPartyName": "Bergbahn Kitzbühel",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Wochenkarte Rechnung 43656243",
                 "bookingDate": "2019-08-21",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Thai Massage",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. We hope you had a gleat time",
                 "bookingDate": "2019-08-22",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -24.2,
                 "counterPartyName": "Kitzbüheler Apotheke",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 1234346458",
                 "bookingDate": "2019-08-22",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-08-22",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -129.95,
                 "counterPartyName": "Douglas",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 234346789",
                 "bookingDate": "2019-08-23",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -160.0,
                 "counterPartyName": "Chizzo",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Bon appetit",
                 "bookingDate": "2019-08-24",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -220.0,
                 "counterPartyName": "Kitzbühler Krankenhaus",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Behandlung verdrehtes Knie",
                 "bookingDate": "2019-08-25",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Reisch Skiverleih",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rennski + Stöcke",
                 "bookingDate": "2019-08-26",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-08-28",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -40.0,
                 "counterPartyName": "Kitzbühel Taxi",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Rechnung 657847828",
                 "bookingDate": "2019-08-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-08-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -50.0,
                 "counterPartyName": "Lufthansa",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Upgrade to Businessclass",
                 "bookingDate": "2019-08-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -150.0,
                 "counterPartyName": "Parkhaus am Flughafen",
                 "counterPartyIban": null,
                 "paymentReference": "POS mit PIN. Parken sie bald wieder!",
                 "bookingDate": "2019-08-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -830.0,
                 "counterPartyName": "Grand Tirolia Kitzbühel",
                 "counterPartyIban": null,
                 "paymentReference": "Thank you for your stay with us",
                 "bookingDate": "2019-08-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -32.15,
                 "counterPartyName": "Rossmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Mein Drogeriemarkt, Grosse Str.",
                 "bookingDate": "2019-09-02",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -19.15,
                 "counterPartyName": "Rossmann",
                 "counterPartyIban": null,
                 "paymentReference": "Mein Drogeriemarkt, Grosse Str.",
                 "bookingDate": "2019-09-02",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-09-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-09-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -116.42,
                 "counterPartyName": "ESSO",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Den Tiger im Tank",
                 "bookingDate": "2019-09-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -29.35,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-09-11",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -9.55,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "Pizza Amalfi",
                 "bookingDate": "2019-09-15",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -600.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Bahnhof",
                 "bookingDate": "2019-09-18",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-09-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -28.65,
                 "counterPartyName": "Kaisers Tengelmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. DANKE FUER IHREN EINKAUF",
                 "bookingDate": "2019-09-21",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-09-22",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -43.99,
                 "counterPartyName": "Amazon S.a.r.L.",
                 "counterPartyIban": null,
                 "paymentReference": "Rechnung",
                 "bookingDate": "2019-09-24",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-09-28",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-09-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -59.22,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-10-03",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -56.67,
                 "counterPartyName": "JET",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Die Tanke Ihrer Wahl",
                 "bookingDate": "2019-10-07",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-10-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-10-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -16.99,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "Pizza Amalfi",
                 "bookingDate": "2019-10-13",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -1200.0,
                 "counterPartyName": "Lufthansa",
                 "counterPartyIban": null,
                 "paymentReference": "Thank you for your flight FRA-HKG Ret.",
                 "bookingDate": "2019-10-13",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -368.16,
                 "counterPartyName": "Hotel Shibo",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. 3 nights bed & breakfast",
                 "bookingDate": "2019-10-13",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -12.44,
                 "counterPartyName": "Kaisers Tengelmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. DANKE FUER IHREN EINKAUF",
                 "bookingDate": "2019-10-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -7600.55,
                 "counterPartyName": "Join the Crew",
                 "counterPartyIban": null,
                 "paymentReference": "Rechnung 9876667",
                 "bookingDate": "2019-10-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -400.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Rathausplatz",
                 "bookingDate": "2019-10-22",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-10-22",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -45.78,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-10-23",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -117.56,
                 "counterPartyName": "ESSO",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Den Tiger im Tank",
                 "bookingDate": "2019-10-24",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-10-28",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -22.67,
                 "counterPartyName": "Duesseldorf Taxi Firma",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Vielen Dank, dass sie mit uns gefahren sind.",
                 "bookingDate": "2019-10-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-10-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -30.0,
                 "counterPartyName": "Steakrestaurant am Flughafen",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-10-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-10-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -578.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Withdrawl for 600HK$ at HK Airport",
                 "bookingDate": "2019-10-30",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -10.55,
                 "counterPartyName": "Duesseldorf Taxi Company",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Vielen Dank, dass sie mit uns gefahren sind.",
                 "bookingDate": "2019-11-03",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-11-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-11-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -516.45,
                 "counterPartyName": "Amazon S.a.r.L.",
                 "counterPartyIban": null,
                 "paymentReference": "Rechnung",
                 "bookingDate": "2019-11-11",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -450.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Bahnhof",
                 "bookingDate": "2019-11-18",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -83.21,
                 "counterPartyName": "Kaisers Tengelmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. DANKE FUER IHREN EINKAUF",
                 "bookingDate": "2019-11-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-11-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-11-22",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -104.16,
                 "counterPartyName": "JET",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Die Tanke Ihrer Wahl",
                 "bookingDate": "2019-11-26",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-11-28",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-11-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-12-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -100000.95,
                 "counterPartyName": "Good Yachts Inc.",
                 "counterPartyIban": null,
                 "paymentReference": "Rechnung",
                 "bookingDate": "2019-12-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2019-12-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -256.78,
                 "counterPartyName": "Amazon S.a.r.L.",
                 "counterPartyIban": null,
                 "paymentReference": "Rechnung",
                 "bookingDate": "2019-12-12",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2019-12-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -33.15,
                 "counterPartyName": "Kaisers Tengelmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. DANKE FUER IHREN EINKAUF",
                 "bookingDate": "2019-12-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2019-12-23",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -400.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Lindenstrassse",
                 "bookingDate": "2019-12-24",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -113.88,
                 "counterPartyName": "ESSO",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Den Tiger im Tank",
                 "bookingDate": "2019-12-25",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -36.57,
                 "counterPartyName": "REWE Lieferservice",
                 "counterPartyIban": null,
                 "paymentReference": "Wir bringen s",
                 "bookingDate": "2019-12-26",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2019-12-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2019-12-30",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -9.15,
                 "counterPartyName": "Rossmann",
                 "counterPartyIban": null,
                 "paymentReference": "Mein Drogeriemarkt, Grosse Str.",
                 "bookingDate": "2020-01-03",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -987.65,
                 "counterPartyName": "Petra Meyer-Meisner",
                 "counterPartyIban": "DE33500700100055667788",
                 "paymentReference": "Unterhalt",
                 "bookingDate": "2020-01-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": "DE41300700100009876542",
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2020-01-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -96.16,
                 "counterPartyName": "ESSO",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Den Tiger im Tank",
                 "bookingDate": "2020-01-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -30.16,
                 "counterPartyName": "Ddorf Airport Parking",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Rechnung 355464234379",
                 "bookingDate": "2020-01-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -104.16,
                 "counterPartyName": "Hilton Central London",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. We hope you had a pleasent stay",
                 "bookingDate": "2020-01-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -200.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Rathausplatz",
                 "bookingDate": "2020-01-18",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -80.0,
                 "counterPartyName": "Stadtwerke D",
                 "counterPartyIban": null,
                 "paymentReference": "Strom Abschlag",
                 "bookingDate": "2020-01-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -43.45,
                 "counterPartyName": "Kaisers Tengelmann",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. DANKE FUER IHREN EINKAUF",
                 "bookingDate": "2020-01-19",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -116.16,
                 "counterPartyName": "ESSO",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Den Tiger im Tank",
                 "bookingDate": "2020-01-20",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": 9000.95,
                 "counterPartyName": "Meisner Enterprise",
                 "counterPartyIban": null,
                 "paymentReference": "Gehalt",
                 "bookingDate": "2020-01-22",
                 "currencyCode": "EUR",
                 "transactionCode": "153",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "SALA",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -65.0,
                 "counterPartyName": "Douglas",
                 "counterPartyIban": null,
                 "paymentReference": "POS MIT PIN. Ihre Parfuemerie Am Platz",
                 "bookingDate": "2020-01-22",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -17.6,
                 "counterPartyName": "Lieferando.de",
                 "counterPartyIban": null,
                 "paymentReference": "der Inder am eck",
                 "bookingDate": "2020-01-26",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -110.0,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Leben RefNr. 765488780",
                 "bookingDate": "2020-01-28",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005771",
                 "amount": -269.55,
                 "counterPartyName": "Allianz AG",
                 "counterPartyIban": null,
                 "paymentReference": "Rente50 RefNr. 765488779",
                 "bookingDate": "2020-01-29",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-07-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Innenstadt",
                 "bookingDate": "2019-07-17",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-08-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Innenstadt",
                 "bookingDate": "2019-08-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-09-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Alte Schule",
                 "bookingDate": "2019-09-11",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-10-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Rheinpromenade",
                 "bookingDate": "2019-10-16",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-11-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Innenstadt",
                 "bookingDate": "2019-11-13",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2019-12-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MA005734466",
                 "creditorId": "DE17ZZZ00046235285",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -50.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Kneipenviertel",
                 "bookingDate": "2019-12-17",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "MX0355443",
                 "creditorId": "DE0222200004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "212+ZKLE 911/696682-X-ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 65.0,
                 "counterPartyName": "Andreas Meisner",
                 "counterPartyIban": null,
                 "paymentReference": "Taschengeld",
                 "bookingDate": "2020-01-09",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "ABMX0355443",
                 "creditorId": "DE0111100004544221",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/XC:121600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": -100.0,
                 "counterPartyName": "Deutsche Bank",
                 "counterPartyIban": null,
                 "paymentReference": "Barauszahlung, Innenstadt",
                 "bookingDate": "2020-01-10",
                 "currencyCode": "EUR",
                 "transactionCode": "123",
                 "externalBankTransactionDomainCode": "D001",
                 "externalBankTransactionFamilyCode": "CCRD",
                 "externalBankTransactionSubFamilyCode": "CWDL",
                 "mandateReference": "GIRO007535511",
                 "creditorId": "DE19ZZZ00000899991",
                 "e2eReference": "E2E - Reference",
                 "paymentIdentification": "ZKLE 911/TN:171600 ABC"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 1.0,
                 "counterPartyName": "Test Name",
                 "counterPartyIban": "DE10010000000000005771",
                 "paymentReference": null,
                 "bookingDate": "2020-02-13",
                 "currencyCode": "EUR",
                 "transactionCode": null,
                 "externalBankTransactionDomainCode": null,
                 "externalBankTransactionFamilyCode": null,
                 "externalBankTransactionSubFamilyCode": null,
                 "mandateReference": null,
                 "creditorId": null,
                 "e2eReference": null,
                 "paymentIdentification": "RTE819a36b8-97b8-448e-b399-791a9ed9efce"
             },
             {
                 "originIban": "DE10010000000000005772",
                 "amount": 1.0,
                 "counterPartyName": "DE10010000000000005771",
                 "counterPartyIban": "DE10010000000000005771",
                 "paymentReference": null,
                 "bookingDate": "2020-02-17",
                 "currencyCode": "EUR",
                 "transactionCode": null,
                 "externalBankTransactionDomainCode": null,
                 "externalBankTransactionFamilyCode": null,
                 "externalBankTransactionSubFamilyCode": null,
                 "mandateReference": null,
                 "creditorId": null,
                 "e2eReference": null,
                 "paymentIdentification": "RTE50d6d80e-29e9-4384-897c-af213c0b7139"
             }
         ]
     }`
    
</details>

`POST /transactions`

Initiates payments for any listed services (may only be supported Revolut or Deutsche Bank entries).

<details>
    <summary>Example request body</summary>
    
    `{
     	"REVOLUT":[
     		{
     			"sourceAccount": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                 "destinationAccount": "62670460-561e-4955-ba19-0b4c4df46566",
                 "currency": "EUR",
                 "amount": 1.0,
                 "additionalInfo": {
                 	"counterparty": "b3314028-6158-4d11-8d4a-ef5e1bc9bc73",
                 	"reference": "Some reference"
                 }
     		},
     		{
     			"sourceAccount": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
                 "destinationAccount": "62670460-561e-4955-ba19-0b4c4df46566",
                 "currency": "EUR",
                 "amount": 54.11,
                 "additionalInfo": {
                 	"counterparty": "b3314028-6158-4d11-8d4a-ef5e1bc9bc73",
                 	"reference": "Some reference"
                 }
     		}
     	],
     	"DEUTSCHE":[
     		{
                 "sourceAccount": "DE10010000000000005772",
                 "destinationAccount": "DE10010000000000005771",
                 "currency": "EUR",
                 "amount": 1.0,
                 "additionalInfo": {
                 	"creditorName": "Some creditor"
                 }
             },
             {
                 "sourceAccount": "DE10010000000000005772",
                 "destinationAccount": "DE10010000000000005771",
                 "currency": "EUR",
                 "amount": 1.7,
                 "additionalInfo": {
                 	"creditorName": "Some creditor"
                 }
             }
     	]
     }`
    
</details>

<details>
    <summary>Example response</summary>
    
    `{
         "REVOLUT": [
             {
                 "id": "2820c2c3-e975-4e87-b6dc-f3104226cbf4",
                 "state": "pending",
                 "created_at": "2020-02-18T14:37:00.103051Z",
                 "completed_at": null
             },
             {
                 "id": "d1b96e70-4f06-4c14-b339-d955142c7db4",
                 "state": "pending",
                 "created_at": "2020-02-18T14:37:00.260861Z",
                 "completed_at": null
             }
         ],
         "DEUTSCHE": [
             {
                 "paymentId": "RTE0b320536-66d4-4381-a1a3-5f5ea351e1ab",
                 "transactionStatus": "PDNG"
             },
             {
                 "paymentId": "RTE2418c9cd-fbf8-4d17-baf1-44bcb07708bf",
                 "transactionStatus": "PDNG"
             }
         ]
     }`
    
</details>