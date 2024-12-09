@apiTests1 @idGroup
Feature: Identify the Group

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR
    Then I verify the response code is <StatusCode> and <ErrorCode>
    Examples:
      | UserType     |  | Enrolment      | StatusCode | ErrorCode |
     #| Organisation |  | with enrolment    | 201        | 000       |
     #| Organisation |  | without enrolment | 401        | 003       |
      | Organisation |  | with enrolment | 400        | 001       |

 