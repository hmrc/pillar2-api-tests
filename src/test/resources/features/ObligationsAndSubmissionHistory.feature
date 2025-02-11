@apiTests1
Feature: Obligations And Submission History Scenarios

  Scenario Outline: verify Obligations And Submission History response
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                                                                  | RequestUrl                     | Parameters                                        |
      | Organisation | 200        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/Obligations_And_Submission_201.json          | Obligations and Submission Api | obligations-and-submissions/2024-01-30/2024-12-31 |
      | Organisation | 400        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/ObligationsAndSubmission_Error_Response.json | Obligations and Submission Api | obligations-and-submissions/2024-01-29/2024-12-35 |