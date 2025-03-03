@apiTests
Feature: Validate Obligations & Submission History Json schema and Responses

  @obligationSubmissionApiBackend
  Scenario Outline: Verify Obligations & Submission History responses and validate schema for backend
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify the response code is <StatusCode>
    Then I validate "Obligation" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                              | RequestUrl                             | Parameters            |
      | Organisation | 200        | XMPLR0000000012 | Obligations_And_Submission_200          | Obligations and Submission Api backend | 2024-01-30/2024-12-31 |
      | Organisation | 500        | XMPLR0000000012 | ObligationsAndSubmission_Error_Response | Obligations and Submission Api backend | 2024-01-29/2024-12-35 |

  @obligationSubmissionApi
  Scenario Outline: Verify Obligations & Submission History responses and validate schema submission api
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify the response code is <StatusCode>
    Then I validate "Obligation" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                              | RequestUrl                     | Parameters                            |
      | Organisation | 200        | XMPLR0000000012 | Obligations_And_Submission_200          | Obligations and Submission Api | fromDate=2024-01-01&toDate=2024-12-31 |
      | Organisation | 400        | XMPLR0000000012 | ObligationsAndSubmission_Error_Response | Obligations and Submission Api | fromDate=2024-01-01&toDate=2024-12-32 |