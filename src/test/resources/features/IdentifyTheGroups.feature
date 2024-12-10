@apiTests @idGroup
Feature: Identify the Group

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     |  | Enrolment         | StatusCode |
      | Organisation |  | with enrolment    | 201        |
      | Organisation |  | without enrolment | 401        |

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR with <ErrorCode>
    Then I verify the response code is <StatusCode> and <ErrorCode> and <ErrorMessage>
    Examples:
      | UserType     |  | Enrolment         | StatusCode | ErrorCode | ErrorMessage          |
      | Organisation |  | with enrolment    | 400        | 001       | Invalid JSON Payload  |
      | Organisation |  | with enrolment    | 400        | 002       | Empty body in request |
      | Organisation |  | without enrolment | 401        | 003       | Not authorized        |