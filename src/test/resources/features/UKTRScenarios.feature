@apiTests @idGroup @ignore
Feature: UKTR Scenarios

#  Scenario Outline: Verify that the Group has not enrolled
#    And I make API call to PLR UKTR
#    Then I verify the response code is <StatusCode>
#    Examples:
#     | UserType     | Enrolment         | StatusCode |
#     | Organisation | with enrolment    | 201        |
#     | Organisation | without enrolment | 401        |

  Scenario Outline: Verify the error code and error message for the invalid UKTR requests
    Given I have generated a bearer token for an <UserType> and <Enrolment> and <StatusCode>
    And I make API call to PLR UKTR with <ErrorCode>
    Then I verify the response code is <StatusCode> and <ErrorCode> and <ErrorMessage>
    Examples:
      | UserType     | Enrolment         | StatusCode | ErrorCode | ErrorMessage          |
      | Organisation | with enrolment    | 400        | 001       | Invalid JSON Payload  |
      | Organisation | with enrolment    | 400        | 002       | Empty body in request |
      | Organisation | without enrolment | 401        | 003       | Not authorized        |

  ##TODO: Prashant to confirm weather to remove 400,500 status codes
  Scenario Outline: Verify the response for the external stub requests
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl    | Endpoint    |
      | Organisation | XEPLR5555555555 | 201        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR0000000400 | 400        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR5000000000 | 500        | External stub | UKTaxReturn |
  #    | Organisation | XEPLR0000000422 | 422        | External stub | UKTaxReturn |

  Scenario Outline: Verify the response for the stub requests
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint    |
      | Organisation | XEPLR5555555555 | 201        | Stub       | UKTaxReturn |
      | Organisation | XEPLR0123456400 | 400        | Stub       | UKTaxReturn |

  Scenario Outline: Verify the response for the backend requests
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint             |
      | Organisation | XMPLR0000000012 | 201        | Backend    | submit-uk-tax-return |
 #     | Organisation | XEPLR1066196422 | 422        | Backend    | submit-uk-tax-return |