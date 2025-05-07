@apiTests @Orn
Feature: Submit ORN and validate json schema

  @OrnNonDomestic
  Scenario Outline: Submit and Amend ORN in submission api for non domestic organization
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    When I make API call to create "<DomesticFlag>" "Test Organisation Ltd" using "setup/organisation" with "<PLRID>"
    And I verify the response code is <StatusCode>

    Then I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    And I validate "ORN" "Requests" json schema for "SubmitAmendORN"
    Then I verify the response code is <StatusCode>
    And I validate "ORN" "Response" json schema for "SubmitAmendORN"

    Then I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    And I verify the response code is 422
    And I validate "ORN" "Response" json schema for "ValidationORN"
    Then I verify the response contains the following values:
      | Key     | ExpectedValue                    |
      | code    | 044                              |
      | message | Tax obligation already fulfilled |

    When I make API call to Submission Api Amend ORN and <Endpoint> and <PLRID> and 200
    And I validate "ORN" "Requests" json schema for "SubmitAmendORN"
    Then I verify the response code is 200
    And I validate "ORN" "Response" json schema for "SubmitAmendORN"

    Then I make API call to delete organisation using "setup/organisation" with "<PLRID>"
    And I verify the response code is 204

    Examples:
      | UserType     | StatusCode | PLRID           | DomesticFlag | RequestUrl         | Endpoint                     |
      | Organisation | 201        | XEPLR5555551126 | Non-Domestic | Submission Api ORN | overseas-return-notification |

  @OrnDomestic
  Scenario Outline: Validation error for Submit ORN in submission api for domestic organization
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    When I make API call to create "<DomesticFlag>" "Test Organisation Ltd" using "setup/organisation" with "<PLRID>"
    And I verify the response code is 201
    Then I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    And I validate "ORN" "Requests" json schema for "SubmitAmendORN"

    Then I verify the response code is <StatusCode>
    And I validate "ORN" "Response" json schema for "ValidationORN"

    Then I verify the response contains the following values:
      | Key     | ExpectedValue                  |
      | code    | 003                            |
      | message | Request could not be processed |

    Then I make API call to delete organisation using "setup/organisation" with "<PLRID>"
    Then I verify the response code is 204
    Examples:
      | UserType     | StatusCode | PLRID           | DomesticFlag | RequestUrl         | Endpoint                     |
      | Organisation | 422        | XEPLR5555551126 | Domestic     | Submission Api ORN | overseas-return-notification |