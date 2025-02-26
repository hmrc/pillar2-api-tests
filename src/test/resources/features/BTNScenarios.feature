@apiTests
Feature: Validate BTN Json schema and Responses

  Scenario Outline: Verify Submit BTN responses and validate schema
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I validate "BTN" request json schema for "SubmissionApiBTN"
    When I verify the response code is <StatusCode>
    Then I validate "BTN" response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema     | RequestUrl         | Endpoint                     |
      | Organisation | 201        | XMPLR0000000012 | BTN_201        | Submission Api BTN | below-threshold-notification |
      | Organisation | 500        | XEPLR0123456500 | Error_Response | Submission Api BTN | below-threshold-notification |
      | Organisation | 400        | XEPLR0000000400 | Error_Response | Submission Api BTN | below-threshold-notification |
      | Organisation | 401        | XEPLR0000000400 | Error_Response | Submission Api BTN | below-threshold-notification |
      | Individual   | 403        | XEPLR0000000400 | Error_Response | Submission Api BTN | below-threshold-notification |
