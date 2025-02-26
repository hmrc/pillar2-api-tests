@apiTests @idGroup @ignore
Feature: Validate UKTR Responses with External Stubs

  ##TODO: Prashant to confirm weather to remove 400,500 status codes
  Scenario Outline: Verify the response for the External Stub requests
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl    | Endpoint    |
      | Organisation | XEPLR5555555555 | 201        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR0000000400 | 400        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR5000000000 | 500        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR0000000422 | 422        | External stub | UKTaxReturn |
