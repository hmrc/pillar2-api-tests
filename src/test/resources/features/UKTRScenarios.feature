@apiTests @idGroup
Feature: UKTR Scenarios

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | Enrolment         | StatusCode |
      | Organisation | with enrolment    | 201        |
      | Organisation | without enrolment | 401        |

  Scenario Outline: Verify that user has sent a authenticated UKTR reuqest to verify UKTR submission
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to PLR UKTR
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode |
      | Organisation | XEPLR5555555555 | 201        |
      | Organisation | XEPLR0123456400 | 500        |
      | Organisation | XEPLR0123456404 | 500        |
      | Organisation | XEPLR0123456422 | 500        |
      | Organisation | XEPLR0123456500 | 500        |
      | Organisation | XEPLR0123456503 | 500        |
