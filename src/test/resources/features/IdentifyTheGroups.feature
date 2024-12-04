@apiTests @idGroup
Feature: Identify the Group

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR for <StatusCode>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | Enrolment         | StatusCode |
      | Organisation | with enrolment    | 201        |
#      | Organisation | without enrolment | 401        |
 