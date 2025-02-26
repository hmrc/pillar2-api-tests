@apiTests
Feature: Validate Obligations & Submission History Json schema and Responses

  Scenario Outline: Verify Obligations & Submission History responses and validate schema
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify the response code is <StatusCode>
    Then I validate "Obligation" response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                              | RequestUrl                     | Parameters            |
      | Organisation | 200        | XMPLR0000000012 | Obligations_And_Submission_201          | Obligations and Submission Api | 2024-01-30/2024-12-31 |
      | Organisation | 400        | XMPLR0000000012 | ObligationsAndSubmission_Error_Response | Obligations and Submission Api | 2024-01-29/2024-12-35 |