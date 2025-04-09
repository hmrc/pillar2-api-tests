@apiTests
Feature: Submit ORN and validate json schema

  @OrnSubmit
  Scenario Outline: Submit ORN in submission api
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    When I make API call to create "<DomesticFlag>" "Test Organisation Ltd" using "setup/organisation" with "<PLRID>"
    Then I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>

    And I validate "ORN" "Requests" json schema for "SubmitORN"
    Then I verify the response code is <StatusCode>
    And I validate "ORN" "Response" json schema for "ResponseORN"

    Then I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    And I verify the response code is 422
    Then I verify the response contains the following values:
      | Key     | ExpectedValue                    |
      | code    | 044                              |
      | message | Tax obligation already fulfilled |

    Then I make API call to delete organisation using "setup/organisation" with "<PLRID>"
    Then I verify the response code is 204

    Examples:
      | UserType     | StatusCode | PLRID           | DomesticFlag | RequestUrl         | Endpoint                     |  |
      | Organisation | 201        | XEPLR5555551126 | Non-Domestic | Submission Api ORN | overseas-return-notification |  |