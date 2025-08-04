@apiTests
Feature: Validate BTN Json schema and Responses

  Scenario Outline: Verify Submit BTN responses and validate schema
    Given I have generated a bearer token for an <UserType> and <PLRID> and "201"

    When I make API call to delete organisation using "setup/organisation" with "<PLRID>"

    And I make API call to create "<DomesticFlag>" "<TestOrganisation>" using "setup/organisation" with "<PLRID>"
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    And I verify the response code is 201
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue         |
      | pillar2Id                                | <PLRID>               |
      | organisation.orgDetails.organisationName | Test Organisation Ltd |
      | organisation.orgDetails.registrationDate | 2024-01-01            |

    And I validate "TestOrganisation" "Response" json schema for "OrganisationSuccess"

    When I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    And I validate "BTN" "Requests" json schema for "SubmissionApiBTN"
    Then I verify the response code is <StatusCode>
    And I validate "BTN" "Response" json schema for "<JsonSchema>"

    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema     | RequestUrl         | Endpoint                     | TestOrganisation      |
      | Organisation | 201        | XMPLR0000000012 | BTN_201        | Submission Api BTN | below-threshold-notification | Test Organisation Ltd |
      | Organisation | 500        | XEPLR0123456500 | Error_Response | Submission Api BTN | below-threshold-notification | Test Organisation Ltd |
      | Organisation | 400        | XEPLR0000000400 | Error_Response | Submission Api BTN | below-threshold-notification | Test Organisation Ltd |
      | Organisation | 401        | XEPLR0000000400 | Error_Response | Submission Api BTN | below-threshold-notification | Test Organisation Ltd |